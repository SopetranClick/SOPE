# Plan de ejecución — Soporte (PQRS), Marketing y Seguridad

> **Para el modelo (Sonnet):** este documento es una orden de trabajo. Ejecuta las
> partes A y B creando/editando los archivos indicados **respetando la arquitectura
> existente** descrita en `CLAUDE.md`. La parte C es una guía de seguridad para el
> equipo (no requiere que escribas código todavía, salvo que el usuario lo pida).
>
> **Contexto clave del proyecto (ya verificado en el repo):**
> - Es una SPA de una sola página: `templates/Index.html` inserta *fragmentos*
>   Thymeleaf con `th:insert="~{mennu/Archivo :: fragmento}"`.
> - El enrutador vive en `static/js/app.js`: el objeto `VISTAS` mapea
>   `nombre → id de sección`, y `navegarA('nombre')` alterna la clase `.active`
>   sobre cada `<section class="spa-view">`.
> - El nav (`Index.html`, ~línea 51) tiene el logo de soporte
>   `@{/img/Logo/apoyo.png}` **sin `onclick`** — ese es el disparador que hay que cablear.
> - Backend por dominios: `model/ → repository/ → service/(interface + Impl) →
>   controller/(@RestController)`, con DTOs `Request`/`Response`. CRUD REST en `/api/...`.
> - Manejo global de errores en `exception/GlobalExceptionHandler.java`.

---

## PARTE A — Vista de Soporte con formulario PQRS

**Objetivo:** al hacer clic en el logo de apoyo del nav, el usuario llega a una
vista de Soporte donde puede enviar una **PQRS** (Petición, Queja, Reclamo o
Sugerencia) rápidamente mediante un formulario, y ve las opciones de **Contáctanos**.

### A.1 — Backend: persistir la PQRS

Sigue el patrón del proyecto (mira `model/category/trade/Local.java` y su servicio
como referencia de estilo).

1. **Entidad** `src/main/java/com/sope/sopetran_click/model/user/Pqrs.java`
   ```java
   @Entity
   @Table(name = "pqrs")
   @Data @NoArgsConstructor @AllArgsConstructor
   public class Pqrs {
       @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long idPqrs;

       @Column(nullable = false, length = 30)
       private String tipo;          // PETICION | QUEJA | RECLAMO | SUGERENCIA

       @Column(nullable = false, length = 120)
       private String nombre;

       @Column(nullable = false, length = 120)
       private String email;

       @Column(length = 30)
       private String telefono;

       @Column(nullable = false, length = 150)
       private String asunto;

       @Column(nullable = false, columnDefinition = "TEXT")
       private String mensaje;

       @Column(nullable = false, length = 20)
       private String estado;        // NUEVA | EN_PROCESO | CERRADA

       private LocalDateTime creadoEn;

       @PrePersist
       void prePersist() {
           this.creadoEn = LocalDateTime.now();
           if (this.estado == null) this.estado = "NUEVA";
       }
   }
   ```

2. **DTOs** en `dto/user/`:
   - `PqrsRequestDTO.java` — con validaciones Jakarta:
     `@NotBlank` en `tipo`, `nombre`, `asunto`, `mensaje`; `@Email @NotBlank` en `email`;
     `telefono` opcional. **No** incluye `estado` ni `id` (los pone el servidor).
   - `PqrsResponseDTO.java` — `idPqrs`, `tipo`, `nombre`, `asunto`, `estado`, `creadoEn`.

3. **Repositorio** `repository/PqrsRepository.java`
   ```java
   public interface PqrsRepository extends JpaRepository<Pqrs, Long> {
       List<Pqrs> findByEstadoOrderByCreadoEnDesc(String estado);
   }
   ```

4. **Servicio** `service/user/PqrsService.java` (interface) + `PqrsServiceImpl.java`:
   - `PqrsResponseDTO crear(PqrsRequestDTO dto)` con `@Transactional`.
   - `List<PqrsResponseDTO> listar()` con `@Transactional(readOnly = true)`.
   - Método privado `convertToResponseDTO(Pqrs)`.

5. **Controlador** `controller/user/PqrsController.java`
   ```java
   @RestController
   @RequestMapping("/api/pqrs")
   public class PqrsController {
       private final PqrsService service;
       public PqrsController(PqrsService service) { this.service = service; }

       @PostMapping
       public ResponseEntity<PqrsResponseDTO> crear(@Valid @RequestBody PqrsRequestDTO dto) {
           return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
       }

       @GetMapping  // (para el admin; proteger cuando exista Spring Security)
       public ResponseEntity<List<PqrsResponseDTO>> listar() {
           return ResponseEntity.ok(service.listar());
       }
   }
   ```
   > Las validaciones fallidas ya las captura `GlobalExceptionHandler`
   > (`MethodArgumentNotValidException` → 400 con `campos_invalidos`).

### A.2 — Frontend: fragmento de la vista de Soporte

1. **Crea** `src/main/resources/templates/mennu/vista-soporte.html` como *fragmento*
   (sin `<head>`, igual que `vista-transporte.html`):
   ```html
   <!DOCTYPE html>
   <html lang="es" xmlns:th="http://www.thymeleaf.org">
   <body>
   <section th:fragment="vista-soporte" id="vista-soporte" class="spa-view">
     <div class="soporte-wrapper">
       <button class="btn-back" onclick="navegarA('inicio')">
         <i class="fas fa-arrow-left"></i> SopetranClick
       </button>

       <header class="soporte-header">
         <div class="tag">Centro de Ayuda</div>
         <h1>¿En qué podemos <span>ayudarte</span>?</h1>
         <p>Envía tu PQRS y te responderemos lo antes posible.</p>
       </header>

       <div class="soporte-grid">
         <!-- Formulario PQRS -->
         <form class="pqrs-form" id="pqrs-form" onsubmit="return enviarPqrs(event)">
           <label>Tipo de solicitud
             <select id="pqrs-tipo" required>
               <option value="">Selecciona…</option>
               <option value="PETICION">Petición</option>
               <option value="QUEJA">Queja</option>
               <option value="RECLAMO">Reclamo</option>
               <option value="SUGERENCIA">Sugerencia</option>
             </select>
           </label>
           <label>Nombre completo <input type="text" id="pqrs-nombre" required></label>
           <label>Correo electrónico <input type="email" id="pqrs-email" required></label>
           <label>Teléfono (opcional) <input type="tel" id="pqrs-tel"></label>
           <label>Asunto <input type="text" id="pqrs-asunto" required></label>
           <label>Mensaje <textarea id="pqrs-mensaje" rows="5" required></textarea></label>
           <button type="submit" class="btn-enviar">Enviar PQRS</button>
           <p class="pqrs-feedback" id="pqrs-feedback"></p>
         </form>

         <!-- Contáctanos -->
         <aside class="contacto-box">
           <h3>Contáctanos</h3>
           <a class="contacto-item" href="https://wa.me/57XXXXXXXXXX" target="_blank">
             <i class="fab fa-whatsapp"></i> WhatsApp</a>
           <a class="contacto-item" href="mailto:soporte@sopetranclick.com">
             <i class="fas fa-envelope"></i> soporte@sopetranclick.com</a>
           <a class="contacto-item" href="tel:+57XXXXXXXXXX">
             <i class="fas fa-phone"></i> +57 XXX XXX XXXX</a>
           <div class="contacto-horario">
             <i class="fas fa-clock"></i> Lun–Vie 8:00 a.m. – 6:00 p.m.
           </div>
         </aside>
       </div>
     </div>
   </section>
   </body>
   </html>
   ```

2. **JS** `src/main/resources/static/js/soporte.js` (función global, sin módulo):
   ```javascript
   async function enviarPqrs(e) {
     e.preventDefault();
     const fb = document.getElementById('pqrs-feedback');
     const payload = {
       tipo:     document.getElementById('pqrs-tipo').value,
       nombre:   document.getElementById('pqrs-nombre').value.trim(),
       email:    document.getElementById('pqrs-email').value.trim(),
       telefono: document.getElementById('pqrs-tel').value.trim(),
       asunto:   document.getElementById('pqrs-asunto').value.trim(),
       mensaje:  document.getElementById('pqrs-mensaje').value.trim()
     };
     try {
       const res = await fetch('/api/pqrs', {
         method: 'POST',
         headers: { 'Content-Type': 'application/json' },
         body: JSON.stringify(payload)
       });
       if (!res.ok) throw new Error('HTTP ' + res.status);
       fb.textContent = '✅ ¡Recibimos tu PQRS! Te contactaremos pronto.';
       fb.className = 'pqrs-feedback ok';
       document.getElementById('pqrs-form').reset();
     } catch (err) {
       fb.textContent = '❌ No se pudo enviar. Intenta de nuevo o escríbenos por WhatsApp.';
       fb.className = 'pqrs-feedback error';
     }
     return false;
   }
   ```

3. **CSS** `src/main/resources/static/css/soporte.css` — reutiliza la paleta oscura
   del proyecto (fondo negro, acento rojo `#8b0000`, tarjetas `--card-bg`). Layout de dos
   columnas (`soporte-grid { display:grid; grid-template-columns: 2fr 1fr; gap: 32px; }`)
   que colapsa a una sola columna con `@media (max-width: 768px)`.

### A.3 — Registrar la vista en la SPA (editar `Index.html` y `app.js`)

1. En `Index.html`, **dentro de `<main id="main-content">`**, agrega el insert del
   fragmento junto a los demás:
   ```html
   <div th:insert="~{mennu/vista-soporte :: vista-soporte}"></div>
   ```
2. En `Index.html`, en el `<head>`, enlaza el CSS: `<link rel="stylesheet" th:href="@{/css/soporte.css}">`
   y **después de responsive.css**. Antes de `</body>` enlaza el JS:
   `<script th:src="@{/js/soporte.js}"></script>`.
3. En `static/js/app.js`, añade la ruta al objeto `VISTAS`:
   ```javascript
   'soporte': 'vista-soporte',
   ```
4. **Cablea el logo de soporte** en `Index.html` (nav, ~línea 62):
   ```html
   <img th:src="@{/img/Logo/apoyo.png}" alt="Soporte" style="width: 35px; cursor: pointer;"
        onclick="navegarA('soporte')" title="Soporte y PQRS">
   ```

### A.4 — Verificación
- `mvn spring-boot:run`, abre la app, clic en el logo de apoyo → debe mostrarse la vista.
- Envía una PQRS de prueba → 201 y mensaje de éxito; verifica la fila en la tabla `pqrs`.
- Prueba validación: envía sin asunto → el form HTML lo bloquea; si pasa, el back responde 400.

---

## PARTE B — Vista de Marketing para clientes potenciales

**Objetivo:** una vista que explique a comerciantes/anunciantes **cómo funciona la
plataforma**, los **planes de publicidad**, y ofrezca **datos de contacto para una
asesoría personalizada**. Se accede desde un enlace en el footer y/o desde
"Contáctanos".

### B.1 — Fragmento
1. **Crea** `templates/mennu/vista-marketing.html` (mismo patrón de fragmento):
   secciones sugeridas:
   - **Hero**: "Haz crecer tu negocio en Sopetrán" + CTA "Solicita asesoría".
   - **Cómo funciona** (3–4 pasos con íconos: Regístrate → Publica tu negocio →
     Recibe reservas/clientes → Mide resultados).
   - **Planes de publicidad** (tarjetas: Básico / Destacado / Premium — con features;
     precios como *placeholder* editables).
   - **Beneficios** (visibilidad, geolocalización, integración con reservas).
   - **Asesoría personalizada**: bloque de contacto (WhatsApp, correo comercial,
     teléfono, formulario corto que reusa `/api/pqrs` con `tipo=PETICION`, o un
     endpoint nuevo `/api/leads` si se quiere separar de PQRS).
2. Reutiliza `soporte.js` (`enviarPqrs`) o crea `marketing.js` si el formulario difiere.

### B.2 — CSS
- `static/css/marketing.css` con la misma identidad visual. Tarjetas de planes en grid
  responsive; resaltar el plan recomendado con borde acento.

### B.3 — Registrar en la SPA
- `Index.html`: `<div th:insert="~{mennu/vista-marketing :: vista-marketing}"></div>`,
  link a `marketing.css` y (si aplica) `marketing.js`.
- `app.js`: `'marketing': 'vista-marketing'` en `VISTAS`.
- Enlace de acceso: en el footer del `Index.html` agrega
  `<a onclick="navegarA('marketing')">Publicita tu negocio</a>` y, opcionalmente,
  un botón en la vista de Soporte ("¿Eres comerciante? Conoce nuestros planes").

### B.4 — Verificación
- Navegar a marketing desde el footer; el formulario de asesoría crea un registro
  (PQRS tipo PETICION o lead) y muestra confirmación.

---

## PARTE C — Feedback de seguridad: reservas, pagos y sesiones

> Esta es la parte más importante. Hoy el proyecto **no tiene seguridad ni
> autenticación** (lo dice `CLAUDE.md`). Antes de conectar pagos reales, hay que
> construir estas bases. Aquí va la hoja de ruta recomendada.

### C.1 — Regla de oro de los pagos: **nunca proceses tarjetas tú mismo**
- **No** recibas ni guardes número de tarjeta/CVV en tu backend. Eso te mete en
  el alcance completo de **PCI-DSS** (auditorías costosas y responsabilidad legal).
- Usa una **pasarela de pagos** que haga el cobro por ti. En Colombia:
  **Wompi (Bancolombia), PayU LATAM, Mercado Pago, ePayco**. Internacionales: Stripe.
- Prefiere **Checkout alojado (hosted checkout) o redirección/widget**: el usuario
  ingresa la tarjeta en la página de la pasarela (no en la tuya). Si necesitas
  formulario propio, usa **tokenización** del lado del cliente (la pasarela convierte
  la tarjeta en un *token* de un solo uso; tu backend solo ve el token).

### C.2 — Flujo de pago seguro (server-authoritative)
El monto y la disponibilidad **siempre** los decide el servidor, nunca el navegador:

1. **Cliente** pide reservar (habitación / puesto de bus). Envía *qué* quiere, no *cuánto* cuesta.
2. **Backend** valida disponibilidad, calcula el **precio real** desde la BD y crea
   una **orden** en estado `PENDIENTE` con un `idOrden` propio + `referencia` única.
3. **Backend** crea la intención de pago en la pasarela (con el monto que él calculó)
   y devuelve al cliente la URL/token del checkout. **Las llaves secretas (API keys)
   de la pasarela viven solo en el servidor** (variables de entorno, como ya haces con
   la BD: `DB_URL`, etc.). Nunca en el JS del cliente.
4. **Cliente** paga en la pasarela.
5. **Confirmación por webhook**: la pasarela llama a un endpoint tuyo
   (`POST /api/pagos/webhook`) para avisar el resultado. **Verifica la firma** del
   webhook (HMAC/secret de la pasarela) — no confíes en un simple redirect del
   navegador, que se puede falsificar. Solo cuando el webhook confirma `APPROVED`,
   marca la orden como `PAGADA` y **confirma la reserva**.
6. Aplica **idempotencia**: usa la `referencia`/`idempotency-key` para que un webhook
   repetido no cree dos reservas ni cobre dos veces.

### C.3 — Reservas: evitar doble venta (condiciones de carrera)
- Dos usuarios pueden intentar el mismo puesto/habitación a la vez. Protege el
  "cupo" con **transacciones** y bloqueo optimista o pesimista:
  - **Optimista**: columna `@Version` en la entidad (Reserva/Cupo). Si dos escrituras
    chocan, una falla y se reintenta.
  - **Pesimista**: `SELECT … FOR UPDATE` (`@Lock(LockModeType.PESSIMISTIC_WRITE)` en el
    repositorio) sobre el registro de disponibilidad dentro de un `@Transactional`.
- Usa un estado intermedio **`RESERVADO_TEMPORAL`** con expiración (p. ej. 10 min):
  se libera el cupo si el pago no se confirma a tiempo (job programado o verificación
  al leer).

### C.4 — Integración con APIs de terceros (empresa de transporte)
- Todas las llamadas a la API de la empresa se hacen **desde tu backend** (proxy),
  nunca desde el navegador. Así ocultas credenciales y controlas validaciones.
- Guarda las credenciales de cada empresa en variables de entorno / un gestor de
  secretos (no en el código ni en la BD en texto plano; si van en BD, **cifradas**).
- Usa **HTTPS/TLS** en todas las llamadas. Aplica **timeouts** y **reintentos con
  backoff**; registra fallos para conciliación.
- Firma/valida los webhooks entrantes de esas empresas igual que los de la pasarela.

### C.5 — Autenticación y sesión segura de usuarios
- Añade **Spring Security** (`spring-boot-starter-security`).
- **Contraseñas**: hash con **BCrypt** (`BCryptPasswordEncoder`). Nunca en texto plano.
- Dos enfoques de sesión (elige uno):
  - **Cookie de sesión de servidor** (`JSESSIONID`): marca la cookie **`HttpOnly`,
    `Secure`, `SameSite=Lax/Strict`**. Habilita **protección CSRF** (Spring Security la
    trae por defecto para formularios).
  - **JWT** (si el frontend fuera SPA desacoplada/móvil): access token de vida corta
    (~15 min) + refresh token de vida larga guardado en **cookie HttpOnly Secure**
    (no en `localStorage`, que es vulnerable a XSS). Implementa **rotación** de refresh
    tokens y una lista de revocación.
- **Autorización por roles** (ya existen en el dominio: `Turista`, `Comerciante`,
  `Administrador`): protege endpoints con `@PreAuthorize("hasRole('ADMINISTRADOR')")`
  (p. ej. `GET /api/pqrs` de la Parte A debe ser solo-admin).
- **HTTPS obligatorio** en producción (redirige HTTP→HTTPS). Sin TLS, cualquier cookie
  o token viaja en claro.

### C.6 — Endurecimiento general (checklist)
- **Validación de entrada** en todos los DTOs (`@Valid`, ya lo usas) para frenar
  inyección y datos basura. Usa siempre **JPA/consultas parametrizadas** (nada de SQL
  concatenado) para evitar **SQL injection**.
- **Escapa la salida** en las vistas (Thymeleaf escapa por defecto con `th:text`;
  evita `th:utext` con datos del usuario) para prevenir **XSS**.
- **Rate limiting** en endpoints sensibles (login, PQRS, pagos) para frenar abuso/bots.
- **CORS** restrictivo: solo tu dominio.
- **Cabeceras de seguridad**: `Content-Security-Policy`, `X-Content-Type-Options`,
  `X-Frame-Options`/`frame-ancestors`, `Strict-Transport-Security`.
- **Registro/auditoría** de acciones de pago y cambios de estado de reservas (quién,
  cuándo, monto, referencia) — clave para conciliar y para soporte.
- **Secretos fuera del repo**: API keys, secretos de webhook y JWT en variables de
  entorno (nunca commiteados). Rota llaves periódicamente.
- **Datos mínimos**: guarda solo lo necesario del usuario; cifra en reposo lo sensible;
  cumple la ley colombiana de datos personales (**Ley 1581 de 2012 / Habeas Data**):
  política de privacidad y consentimiento explícito en los formularios (incluye un
  checkbox de aceptación en PQRS y reservas).

### C.7 — Orden sugerido de implementación
1. Spring Security + registro/login + BCrypt + roles (base de todo).
2. Sesión segura (cookies HttpOnly/Secure/SameSite o JWT con refresh en cookie).
3. Modelo de **Orden/Reserva** con estados y control de concurrencia (C.3).
4. Integración de **una** pasarela (empezar por sandbox/pruebas) con webhook firmado (C.2).
5. Proxy backend hacia la API de transporte (C.4).
6. Endurecimiento (C.6) y auditoría antes de ir a producción.

---

### Resumen de archivos que tocará la Parte A y B

| Acción | Archivo |
|---|---|
| Entidad PQRS | `model/user/Pqrs.java` |
| DTOs | `dto/user/PqrsRequestDTO.java`, `dto/user/PqrsResponseDTO.java` |
| Repositorio | `repository/PqrsRepository.java` |
| Servicio | `service/user/PqrsService.java`, `service/user/PqrsServiceImpl.java` |
| Controlador | `controller/user/PqrsController.java` |
| Vista soporte | `templates/mennu/vista-soporte.html` |
| Vista marketing | `templates/mennu/vista-marketing.html` |
| JS | `static/js/soporte.js` (+ `marketing.js` si aplica) |
| CSS | `static/css/soporte.css`, `static/css/marketing.css` |
| Registro SPA | editar `templates/Index.html` (inserts, links, onclick del logo) y `static/js/app.js` (mapa `VISTAS`) |
