# Guía de Ejecución — SopetranClick Frontend & Responsive

> **Rol:** Senior Developer (Spring Boot + Thymeleaf + Frontend).
> **Objetivo:** Convertir el frontend parcialmente funcional en una app **responsive, data-driven y optimizada**, eliminando hardcoding y arreglando el scroll.
>
> Esta guía está escrita **con base en tu código real** (no es genérica). Cada sección apunta a archivos y líneas concretas del repo.

---

## Mapa del proyecto (lo que ya existe)

**Vistas Thymeleaf** (`src/main/resources/templates/`):
- `Index.html` → SPA: contiene TODAS las vistas como `<section class="spa-view">` que se muestran/ocultan con JS (`navegarA()`).
- `mennu/MenuAlojamiento.html`, `mennu/MennuComercio.html`, `mennu/MennuEcoturismo.html`, `mennu/vista-transporte.html`, `mennu/vista-galeria-cultural.html`
- `Exploracion.html`, `QuienesSomos.html`

**Controlador web** (`controllerWeb/MainController.java`):
- `GET /` → `Index` con `hoteles`, `fincas`, `eventos` en el Model. ✅ Ya conecta 3 datasets.
- `GET /comercio`, `/ecoturismo`, `/transporte`, `/cultural` → **NO pasan datos al Model todavía** ❌

**REST Controllers ya implementados** (`controller/{domain}/`): `/api/hotels`, `/api/estates`, `/api/sites`, `/api/restaurants`, `/api/locals`, `/api/buses`, `/api/motorbikes`, `/api/events`, `/api/news`, etc.

**CSS** (`static/css/`): `index.css`, `alojamiento.css`, `comercio.css`, `ecoturismo.css`, `transport.css`, `cultural.css`, `exploracion.css`, `quienessomos.css`, `responsive.css`

**JS** (`static/js/`): `app.js`, `transport.js`, `cultural.js`, `MenuAlojamiento.js`, `MennuComercio.js`, `MennuEcoturismo.js`
- ⚠️ `transport.js` tiene datos **hardcodeados** (`rutasPorEmpresa`, `driversPorTipo`).

---

## Orden de ejecución

1. 🔴 **Acción 1 — Scroll** (bloqueador)
2. 🟡 **Acción 2 — Thymeleaf ↔ BD**
3. 🟡 **Acción 3 — Eliminar mock data en JS**
4. 🟢 **Acción 4A — Limpieza CSS**
5. 🟢 **Acción 4B — Responsive**

---

# 🔴 Acción 1 — Resolver el Scroll (BLOQUEADOR)

## Diagnóstico: las 3 causas más probables (según tu código)

He revisado tu CSS y JS. El scroll se bloquea por **una combinación de estas causas**, no por una sola:

### Causa A — El overlay de intro `#Welcomed` queda encima
En `index.css` (~línea 571):
```css
#Welcomed {
    position: fixed;
    inset: 0;           /* cubre TODA la pantalla */
    ...
    overflow: hidden;
}
```
`app.js` (~línea 62) maneja el intro con `const intro = document.getElementById('Welcomed')`. Si la animación **no termina de ocultar** ese `<div>` (JS falla, imagen no carga, etc.), queda una capa `fixed` cubriendo todo → no puedes ver ni hacer scroll del contenido.

### Causa B — `body.style.overflow = 'hidden'` que no se revierte
En `app.js` (líneas 162–163):
```js
function abrirPanel()  { panel.classList.add('active');    document.body.style.overflow = 'hidden'; }
function cerrarPanel() { panel.classList.remove('active'); document.body.style.overflow = 'auto'; }
```
Si un panel/modal se abre y **no se cierra correctamente**, el `overflow:hidden` queda pegado en `<body>` y **bloquea el scroll de toda la página**.

### Causa C — Secciones `spa-view` con `100vh` + `hero-bg` fijo
Varios CSS usan `min-height: 100vh` y `.hero-bg { position: fixed; inset: 0 }` (ej. `transport.css:25`). Si un contenedor padre tiene `height: 100vh` (fijo, no `min-height`) + `overflow: hidden`, el contenido que sobra queda recortado sin scroll.

## Pasos para solucionarlo

### Paso 1.1 — Reproducir y confirmar la causa (DevTools)
Abre la app (`mvn spring-boot:run`) y en el navegador (F12 → Console):
```js
// ¿El body tiene overflow bloqueado?
getComputedStyle(document.body).overflow
// ¿El intro sigue visible?
getComputedStyle(document.getElementById('Welcomed')).display
// ¿Quién es más alto: el contenido o la ventana?
document.documentElement.scrollHeight, window.innerHeight
```
- Si `overflow` = `"hidden"` → **Causa B**.
- Si `#Welcomed` sigue en `display:block`/`flex` tapando todo → **Causa A**.
- Si `scrollHeight <= innerHeight` pero ves contenido cortado → **Causa C** (un wrapper recorta).

### Paso 1.2 — Red de seguridad global en CSS
Añade al **inicio** de `responsive.css` (se carga de último, así gana):
```css
/* === FIX SCROLL GLOBAL === */
html, body {
    height: auto !important;
    min-height: 100%;
    overflow-x: hidden;
    overflow-y: auto !important;   /* nunca dejar el body sin scroll vertical */
}

/* Ninguna vista debe recortar su contenido verticalmente */
.spa-view {
    min-height: 100vh;
    height: auto;
    overflow: visible;
}
```

### Paso 1.3 — Garantizar que el intro se oculte SIEMPRE
En `app.js`, donde termina la animación del intro, fuerza el ocultado y libera el body:
```js
function cerrarIntro() {
    const intro = document.getElementById('Welcomed');
    if (intro) intro.style.display = 'none';
    document.body.style.overflow = 'auto';   // libera scroll por si acaso
}
// Llamar al terminar la animación Y como fallback de seguridad:
setTimeout(cerrarIntro, 4000);   // si algo falla, a los 4s se libera
```

### Paso 1.4 — Blindar abrir/cerrar panel
```js
function abrirPanel()  { if (panel) { panel.classList.add('active');    document.body.style.overflow = 'hidden'; } }
function cerrarPanel() { if (panel) { panel.classList.remove('active'); document.body.style.overflow = 'auto';   } }
// Fallback: al navegar entre vistas, SIEMPRE restaurar el scroll
window.navegarA = (function (orig) {
    return function (nombre) {
        document.body.style.overflow = 'auto';   // seguro anti-bloqueo
        return orig.apply(this, arguments);
    };
})(window.navegarA);
```

### Paso 1.5 — Revisar contenedores con altura fija
Busca `height: 100vh` (sin `min-`) y cámbialo a `min-height: 100vh` donde el contenido pueda crecer:
```bash
grep -rn "height: 100vh" src/main/resources/static/css
```
En `alojamiento.css:48` hay un `height: 100vh` dentro de un contenedor tipo carrusel/stack — verifica que no recorte y usa `min-height` si aplica.

## ✅ Validación Acción 1
- [ ] En desktop puedes hacer scroll con rueda y barra en Index y en cada sección.
- [ ] En móvil (DevTools → modo responsive 375px) el scroll táctil funciona.
- [ ] `getComputedStyle(document.body).overflow` → `"auto"` o `"visible"` tras cerrar cualquier panel.
- [ ] El intro `#Welcomed` desaparece y no vuelve a tapar la pantalla.

## ⚠️ Issues potenciales
- No pongas `overflow-y: auto` en un contenedor interno Y en `body` a la vez sin querer → doble scrollbar. Deja el scroll principal en `body`.
- `!important` solo en la red de seguridad global; no lo riegues por todo el CSS.

---

# 🟡 Acción 2 — Conectar Thymeleaf con la BD

## Estado actual
- ✅ `Index` ya recibe `hoteles`, `fincas`, `eventos` (ver `MainController.index()`).
- ❌ `/comercio`, `/ecoturismo`, `/transporte`, `/cultural` NO pasan datos.
- ❌ Falta verificar que **cada vista** consuma esas variables con `th:each`/`th:text` en vez de HTML fijo.

## Paso 2.1 — Inyectar los servicios que faltan en `MainController`
Los REST controllers ya usan servicios (`RestaurantService`, `SiteService`, `BusesService`, `NewsService`...). Inyéctalos en `MainController` igual que ya haces con `HotelsService`:

```java
// Añadir al constructor y a los campos:
private final RestaurantService restaurantService;
private final LocalService localService;
private final SiteService siteService;
private final BusesService busesService;
private final MotorbikeService motorbikeService;
private final NewsService newsService;

@GetMapping("/comercio")
public String comercio(Model model) {
    model.addAttribute("restaurantes", restaurantService.listarTodos());
    model.addAttribute("locales",      localService.listarTodos());
    return "mennu/MennuComercio";
}

@GetMapping("/ecoturismo")
public String ecoturismo(Model model) {
    model.addAttribute("sitios",        siteService.listarTodos());
    model.addAttribute("lugaresIconicos", siteService.listarIconicos()); // o el método que exista
    return "mennu/MennuEcoturismo";
}

@GetMapping("/transporte")
public String transporte(Model model) {
    model.addAttribute("buses", busesService.listarTodos());
    model.addAttribute("motos", motorbikeService.listarTodos());
    return "mennu/vista-transporte";
}

@GetMapping("/cultural")
public String cultural(Model model) {
    model.addAttribute("eventos",  eventsService.listarTodos());
    model.addAttribute("noticias", newsService.listarTodos());
    return "mennu/vista-galeria-cultural";
}
```
> ⚠️ Verifica el **nombre exacto** del método de listado en cada servicio (aquí es `listarTodos()` según `HotelsService`). Si difiere, ajústalo.

## Paso 2.2 — IMPORTANTE: el Index es SPA, no usa esas rutas
Tu `Index.html` embebe las secciones como `spa-view` en **un solo request** (`GET /`). Eso significa que si quieres que Comercio/Ecoturismo/Transporte/Cultural muestren datos **dentro del Index**, debes pasar TODOS esos atributos también en `index()`:

```java
@GetMapping("/")
public String index(Model model) {
    model.addAttribute("hoteles",  hotelsService.listarTodos());
    model.addAttribute("fincas",   estateService.listarTodos());
    model.addAttribute("eventos",  limitar(eventsService.listarTodos(), 4));
    // --- añadir para las demás secciones embebidas ---
    model.addAttribute("restaurantes", restaurantService.listarTodos());
    model.addAttribute("locales",      localService.listarTodos());
    model.addAttribute("sitios",       siteService.listarTodos());
    model.addAttribute("buses",        busesService.listarTodos());
    model.addAttribute("motos",        motorbikeService.listarTodos());
    model.addAttribute("noticias",     newsService.listarTodos());
    return "Index";
}
```
> **Decisión de arquitectura:** o mueves TODO a un método (`prepararModeloCompleto(model)`) reutilizado por `/` y por las rutas individuales, o migras a cargar cada sección por **fetch a la API** (ver Acción 3). Recomendado: extraer un método privado que llene el Model y llamarlo desde ambos sitios para no duplicar.

## Paso 2.3 — Reemplazar HTML fijo por Thymeleaf en cada vista
Patrón (ejemplo Comercio, aplica a todas):
```html
<!-- ANTES (hardcodeado) -->
<div class="card"><h3>Restaurante El Sabor</h3><p>Comida típica</p></div>

<!-- DESPUÉS (data-driven) -->
<div class="card" th:each="r : ${restaurantes}">
    <img th:src="${r.imagenPortada}" th:alt="${r.nombre}">
    <h3 th:text="${r.nombre}">Nombre</h3>
    <p  th:text="${r.descripcion}">Descripción</p>
    <a  th:href="@{'/comercio/' + ${r.id}}">Ver más</a>
</div>

<!-- Estado vacío (buena práctica) -->
<p th:if="${#lists.isEmpty(restaurantes)}">No hay restaurantes disponibles.</p>
```
El fragmento de eventos en `Index.html` (líneas 142–159) ya lo hace bien — úsalo de plantilla:
```html
<div th:each="evento : ${eventos}" class="evento-card">
    <div th:text="${evento.fechaEvento != null ? #temporals.format(evento.fechaEvento,'dd') : '—'}">14</div>
    <div th:text="${#strings.abbreviate(evento.nombreEvento, 60)}">Título</div>
</div>
```

## Paso 2.4 — Imágenes desde BD (patrón de portada/galería)
Como indica `CLAUDE.md`, las imágenes se ordenan por `orden` (0 = portada). Extrae portada y galería en el **ResponseDTO** desde el servicio, y en la vista:
```html
<img th:src="${item.imagenPortada} ?: @{/img/placeholder-hotel.jpg}" th:alt="${item.nombre}">
<div class="galeria">
    <img th:each="url : ${item.galeria}" th:src="${url}">
</div>
```

## ✅ Validación Acción 2
- [ ] Cada ruta (`/comercio`, etc.) muestra datos de la BD, no texto fijo.
- [ ] `Ctrl+U` (ver fuente): el HTML renderizado contiene los datos reales (Thymeleaf ya se procesó en servidor).
- [ ] `grep -rn "th:each" src/main/resources/templates` cubre todas las listas.
- [ ] Con la tabla vacía, se muestra el estado vacío (no rompe).

## ⚠️ Issues potenciales
- `LazyInitializationException` al acceder a imágenes: asegúrate de mapear a DTO **dentro** del método `@Transactional` del servicio (no expongas entidades con relaciones LAZY a la vista).
- Nombres de propiedades: Thymeleaf usa el getter (`item.nombre` → `getNombre()`). Verifica que el DTO tenga esos campos.

---

# 🟡 Acción 3 — Eliminar mock data en JS e integrar API

## Estado actual
`transport.js` (líneas 2–34) tiene arrays hardcodeados: `rutasPorEmpresa`, `empresaInfo`, `driversPorTipo`, `tipoDomicilioInfo`. Revisa también `MennuComercio.js`, `MennuEcoturismo.js`, `cultural.js`.

## Paso 3.1 — Localizar todo el mock data
```bash
grep -rnE "const .*= *\[|const .*= *\{" src/main/resources/static/js | grep -iE "hotel|evento|ruta|driver|sitio|restaurante|local|bus"
```

## Paso 3.2 — Reemplazar por `fetch()` a la API existente
```js
// ANTES
const rutasPorEmpresa = { sotrauraba: [ {...}, {...} ] };

// DESPUÉS
async function cargarBuses() {
    try {
        const res = await fetch('/api/buses');
        if (!res.ok) throw new Error('HTTP ' + res.status);
        const buses = await res.json();
        renderBuses(buses);
    } catch (e) {
        console.error('Error cargando buses:', e);
        document.getElementById('lista-buses').innerHTML =
            '<p class="error">No se pudieron cargar las rutas.</p>';
    }
}
document.addEventListener('DOMContentLoaded', cargarBuses);
```
Endpoints disponibles: `/api/hotels`, `/api/estates`, `/api/restaurants`, `/api/locals`, `/api/sites`, `/api/buses`, `/api/motorbikes`, `/api/events`, `/api/news`.

## Paso 3.3 — Regla de separación de responsabilidades
- **Datos** → Thymeleaf (render inicial en servidor) **o** `fetch` a la API (contenido dinámico/filtros).
- **JS** → solo interacción (pasos, modales, validación, breadcrumbs). No debe contener datos de negocio.
- No mezcles: si una sección ya llega por Thymeleaf, no la vuelvas a pedir por fetch (duplicación).

## ✅ Validación Acción 3
- [ ] `grep` de mock data (paso 3.1) no devuelve arrays de negocio.
- [ ] DevTools → Network: al abrir Transporte se ve la llamada `GET /api/buses` con `200`.
- [ ] Si la API cae, la UI muestra un mensaje de error, no queda en blanco.

## ⚠️ Issues potenciales
- CORS: al ser same-origin (mismo Spring Boot) no hay problema; si mueves la API de dominio, configúralo.
- `fetch` es asíncrono: renderiza tras `await`, no antes.
- Los nombres de campo del JSON (API) usan los del ResponseDTO — inspecciónalos en Network → Response.

---

# 🟢 Acción 4A — Limpieza de CSS

## Paso 4.1 — Detectar clases CSS sin uso en HTML
Para cada clase definida en CSS, comprueba si aparece en algún template:
```bash
# Extrae selectores de clase de un archivo y busca cuáles NO están en templates
grep -oE "\.[a-zA-Z0-9_-]+" src/main/resources/static/css/comercio.css | sort -u | \
while read cls; do
  name="${cls#.}"
  if ! grep -rq "$name" src/main/resources/templates; then echo "HUÉRFANA: $cls"; fi
done
```
> Revisa manualmente los falsos positivos (clases añadidas por JS con `classList.add`).

## Paso 4.2 — Buscar duplicados y redundancias
- Reglas repetidas entre `index.css` y los CSS de módulo (colores, fuentes, botones).
- Variables CSS: consolida paleta en `:root` (ya usas `--dark`, `--negro`, `--border`... **unifícalas**, hoy hay nombres distintos por archivo).
- `!important` innecesarios (aparte de la red de seguridad del scroll).

## Paso 4.3 — Estrategia segura (no romper nada)
1. Trabaja **un CSS a la vez**.
2. Antes de borrar, comenta el bloque (`/* … */`) y verifica la vista.
3. Commit por archivo: `style(css): limpieza comercio.css — N reglas huérfanas`.
4. Documenta en este mismo repo qué eliminaste.

## ✅ Validación Acción 4A
- [ ] Cada vista se ve **idéntica** antes/después (comparación visual).
- [ ] No hay reglas huérfanas confirmadas.
- [ ] Paleta unificada en `:root`.

---

# 🟢 Acción 4B — Responsive (Mobile-First)

## Breakpoints estándar del proyecto
```css
/* Base = móvil (sin media query) */
@media (min-width: 768px)  { /* tablet */ }
@media (min-width: 1025px) { /* desktop */ }
```
> Ya tienes `responsive.css`; centraliza ahí los ajustes cross-módulo. Verifica que el `<meta viewport>` esté en todas las vistas (en `Index.html` ✅ ya está).

## Paso 4.4 — Grids adaptativos (1 → 2 → 3 columnas)
Tu `index.css` ya usa un buen patrón (`.eventos-grid`, línea 79):
```css
.eventos-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
    gap: 24px;
}
```
Aplica el mismo patrón a tarjetas de Alojamiento, Comercio, Ecoturismo. Con `auto-fit + minmax` obtienes responsive **sin escribir media queries** para las columnas.

## Paso 4.5 — Vistas críticas
**Index — Carrusel:**
```css
.carousel-item img { height: 60vh; object-fit: cover; }
@media (max-width: 767px) {
    .carousel-item img { height: 40vh; }
    .carousel-caption h3 { font-size: 1.2rem; }
    .carousel-control-prev, .carousel-control-next { width: 12%; }
}
```
**Alojamiento — Galería + grid:**
```css
.galeria-thumbnails { display: grid; grid-template-columns: repeat(4, 1fr); gap: 8px; }
@media (max-width: 767px) { .galeria-thumbnails { grid-template-columns: repeat(2, 1fr); } }
```
**Tablas → cards en móvil** (Transporte/Comercio):
```css
@media (max-width: 767px) {
    table, thead, tbody, tr, td { display: block; width: 100%; }
    thead { display: none; }
    tr { margin-bottom: 12px; border: 1px solid var(--border); border-radius: 12px; }
    td { display: flex; justify-content: space-between; padding: 8px 12px; }
    td::before { content: attr(data-label); font-weight: 600; }
}
```
**Tipografía escalable** (evita saltos):
```css
:root { font-size: clamp(14px, 1vw + 12px, 18px); }
h1 { font-size: clamp(1.8rem, 4vw, 3rem); }
```
**Botones touch-friendly:**
```css
@media (max-width: 767px) {
    button, .btn, a.btn { min-height: 44px; min-width: 44px; }
}
```

## Paso 4.6 — Nav móvil
El `.nav` con botones Inicio/Exploración/Quiénes debe colapsar en móvil (menú hamburguesa o stack vertical):
```css
@media (max-width: 767px) {
    .nav { flex-wrap: wrap; padding: 8px 12px; }
    .nav article { width: 100%; justify-content: center; }
    .btn-volver-fijo { top: 70px; left: 10px; font-size: 0.75rem; }
}
```

## ✅ Validación Acción 4B
- [ ] DevTools responsive: **320px, 768px, 1025px+** sin scroll horizontal.
- [ ] Ningún texto se corta ni se sale del contenedor.
- [ ] Imágenes con `max-width: 100%; height: auto` (no desbordan).
- [ ] Botones ≥ 44px en móvil.
- [ ] Lighthouse (F12 → Lighthouse → Mobile): sin "content wider than screen" ni layout shifts (CLS bajo).

## ⚠️ Issues potenciales
- Scroll horizontal fantasma: casi siempre un elemento con `width: 100vw` o margen negativo. Detéctalo con:
  ```js
  document.querySelectorAll('*').forEach(e => { if (e.offsetWidth > document.documentElement.clientWidth) console.log(e); });
  ```
- No uses `px` fijos para anchos de contenedor; usa `max-width` + `width: 100%`.
- Layout shift por imágenes: define `width`/`height` o `aspect-ratio` en las `<img>`.

---

# 📋 Checklist final de éxito

- [ ] Todas las vistas scrollean (desktop + móvil).
- [ ] 100% de datos desde BD (Thymeleaf o API). Cero mock data.
- [ ] Sin CSS muerto; paleta unificada en `:root`.
- [ ] Responsive real en 320 / 768 / 1025px.
- [ ] Imágenes cargan desde BD con placeholder de respaldo.
- [ ] APIs consumidas correctamente (Network 200).
- [ ] Sin layout shifts (Lighthouse OK).

---

# 🔧 Comandos útiles

```bash
# Levantar la app (requiere variables de entorno de BD)
mvn spring-boot:run

# Compilar
mvn clean install

# Ver todas las variables Thymeleaf usadas
grep -rn "th:each\|th:text\|th:href\|th:src" src/main/resources/templates

# Buscar datos hardcodeados en JS
grep -rnE "const .*= *\[|const .*= *\{" src/main/resources/static/js

# Buscar posibles bloqueos de scroll
grep -rn "overflow: hidden\|height: 100vh\|position: fixed" src/main/resources/static/css
```

> **Sugerencia de flujo de trabajo:** una rama por acción (`fix/scroll`, `feat/thymeleaf-bd`, `refactor/js-api`, `style/css-cleanup`, `feat/responsive`), commits pequeños y validando la vista tras cada cambio. Empieza SIEMPRE por la Acción 1: sin scroll no puedes validar el resto.
