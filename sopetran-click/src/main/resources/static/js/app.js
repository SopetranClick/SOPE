
// ============================================
// CONTROL BOTÓN VOLVER GLOBAL
// ============================================
const btnVolverGlobal = document.getElementById('btn-volver-global');

function mostrarOcultarVolver(vistaName) {
    if (!btnVolverGlobal) return;
    if (vistaName === 'inicio') {
        btnVolverGlobal.style.display = 'none';
    } else {
        btnVolverGlobal.style.display = 'block';
    }
}

// Sobrescribimos navegarA para controlar el botón volver
const _navegarAOriginal = typeof navegarA !== 'undefined' ? navegarA : null;

// Patch de navegarA (app.js se carga antes pero las funciones son globales)
document.addEventListener('DOMContentLoaded', () => {
    // Parche al router original
    const originalNavegar = window.navegarA;
    window.navegarA = function (nombre) {
        originalNavegar(nombre);
        mostrarOcultarVolver(nombre);
    };
    window.abrirModulo = function (nombre) {
        window.navegarA(nombre);
    };
});

// ============================================
// SCROLL DE RESEÑAS
// ============================================
function scrollResenas(dir) {
    const track = document.getElementById('resenas-track');
    track.scrollBy({ left: dir * 364, behavior: 'smooth' });
}

// ============================================
// FORZAR INTERVAL DEL CARRUSEL A 5s
// Bootstrap lee el atributo data-bs-interval antes de init,
// esto lo asegura por si acaso
// ============================================
document.addEventListener('DOMContentLoaded', () => {
    const carousel = document.getElementById('heroCarousel');
    if (carousel) {
        // Inicializa con interval explícito de 3000ms
        const bsCarousel = new bootstrap.Carousel(carousel, {
            interval: 3000390,
            ride: 'carousel'
        });
    }
});


function cerrarIntro() {
    const intro = document.getElementById('Welcomed');
    if (intro) intro.style.display = 'none';
    document.body.style.overflow = 'auto';
}

document.addEventListener('DOMContentLoaded', () => {
    const intro = document.getElementById('Welcomed');
    const welcomeText = document.getElementById('welcome-text');
    const slidesIntro = intro ? intro.querySelectorAll('img') : [];
    const contentWrapper = document.getElementById('content-wrapper');
    const nav = document.getElementById('main-nav');

    if (!intro || !contentWrapper || !nav) return;

    // Control de tiempo para omitir la animación (6 minutos)
    const SEIS_MINUTOS = 6 * 60 * 1000;
    const ultimoVisto = localStorage.getItem('introAnimacionVista');
    const ahora = Date.now();

    if (ultimoVisto && (ahora - parseInt(ultimoVisto, 10) < SEIS_MINUTOS)) {
        // Omitir animación
        intro.style.display = 'none';
        contentWrapper.style.opacity = '1';
        nav.classList.add('visible');
        document.body.style.overflow = 'auto';
        
        const seccionSolicitada = new URLSearchParams(window.location.search).get('seccion');
        if (seccionSolicitada && typeof window.navegarA === 'function') {
            setTimeout(() => window.navegarA(seccionSolicitada), 100);
        }
        return; // No ejecutar los timeouts
    }

    // Se muestra la animación, guardamos el momento actual
    localStorage.setItem('introAnimacionVista', ahora.toString());

    // Fallback duro: si la animación se traba por cualquier motivo,
    // libera el scroll igual (la cadena normal tarda ~7800ms).
    setTimeout(cerrarIntro, 8000);

    // --- Lógica Intro ---
    setTimeout(() => { if (welcomeText) welcomeText.classList.add('show'); }, 500);
    setTimeout(() => {
        if (slidesIntro.length > 1) {
            slidesIntro[0].classList.remove('active');
            slidesIntro[1].classList.add('active');
        }
    }, 2500);

    setTimeout(() => {
        if (welcomeText) welcomeText.style.opacity = '0';
        setTimeout(() => {
            intro.style.transition = 'all 1.5s cubic-bezier(0.645, 0.045, 0.355, 1)';
            intro.style.transform = 'translateY(-100%)';
            contentWrapper.style.opacity = '1';
            setTimeout(() => nav.classList.add('visible'), 800);
            setTimeout(cerrarIntro, 1500);

            // Deep link: /comercio, /transporte, etc. redirigen a "/?seccion=X"
            // — una vez termina el intro, navega directo a esa sección del SPA.
            const seccionSolicitada = new URLSearchParams(window.location.search).get('seccion');
            if (seccionSolicitada && typeof window.navegarA === 'function') {
                setTimeout(() => window.navegarA(seccionSolicitada), 1600);
            }
        }, 800);
    }, 4500);
});




// INDEX

/* ============================================
   SPA ROUTER — el cerebro del sistema
   Solo necesitas estas dos funciones para toda la app
============================================ */

// Mapa: nombre → id de la vista en el HTML
const VISTAS = {
    'inicio': 'vista-inicio',
    'alojamiento': 'vista-alojamiento',
    'transporte': 'vista-transporte',
    'ecoturismo': 'vista-ecoturismo',
    'comercio': 'vista-comercio',
    'entidades': 'vista-entidades',
    'exploracion': 'vista-exploracion',
    'quienes': 'vista-quienes',
    'soporte': 'vista-soporte',
    'marketing': 'vista-marketing',
};

let vistaActual = 'inicio';
let historialVistas = [];

function navegarA(nombre, isBack = false) {
    if (nombre === vistaActual) return; // ya estás ahí

    if (!isBack && vistaActual) {
        historialVistas.push(vistaActual);
    }

    // 1. Oculta la vista actual
    const viejaVista = document.getElementById(VISTAS[vistaActual]);
    if (viejaVista) viejaVista.classList.remove('active');

    // 2. Muestra la nueva vista con animación
    const nuevaVista = document.getElementById(VISTAS[nombre]);
    if (nuevaVista) {
        nuevaVista.classList.add('active', 'entrando');
        // Quita la clase de animación después de que termina
        setTimeout(() => nuevaVista.classList.remove('entrando'), 500);
    }

    // 3. Actualiza el nav — marca el botón activo
    document.querySelectorAll('.nav button').forEach(btn => btn.classList.remove('nav-activo'));
    // (opcional) si quieres resaltar el botón del nav correspondiente

    // 4. Scroll al tope suavemente
    window.scrollTo({ top: 0, behavior: 'smooth' });

    vistaActual = nombre;
}

function goBack() {
    if (historialVistas.length > 0) {
        const anterior = historialVistas.pop();
        navegarA(anterior, true);
    } else {
        navegarA('inicio', true);
    }
}

// Función para compartir nativa
function compartir(titulo, texto, url) {
    const shareUrl = url || window.location.href;
    if (navigator.share) {
        navigator.share({ title: titulo, text: texto, url: shareUrl })
            .catch(console.error);
    } else {
        if (navigator.clipboard) {
            navigator.clipboard.writeText(`${titulo} - ${texto} ${shareUrl}`);
            alert('Enlace copiado al portapapeles.');
        } else {
            alert('Tu navegador no soporta esta función.');
        }
    }
}

// Función para abrir módulos desde los botones de servicios
function abrirModulo(nombre) {
    navegarA(nombre);
}

/* ============================================
   Tu código existente (scroll, panel, intro)
   — lo dejas igual —
============================================ */
const btnSalto = document.getElementById('btn-salto-central');
const panel = document.getElementById('panel-desplegable');

window.addEventListener('scroll', () => {
    // El botón "saltarín" es del hero de inicio: en cualquier otra
    // vista (alojamiento, comercio...) no debe reaparecer, porque
    // en alojamiento queda superpuesto sobre el botón de mostrar/
    // ocultar interfaz (mismo bottom-center, z-index mayor).
    if (vistaActual !== 'inicio') { btnSalto.style.display = 'none'; return; }
    const scrollTotal = document.documentElement.scrollHeight - window.innerHeight;
    const scrollPercent = (window.scrollY / scrollTotal) * 100;
    btnSalto.style.display = scrollPercent > 45 ? 'block' : 'none';
});

function abrirPanel() { if (panel) { panel.classList.add('active'); document.body.style.overflow = 'hidden'; } }
function cerrarPanel() { if (panel) { panel.classList.remove('active'); document.body.style.overflow = 'auto'; } }

function irAMapa() {
    cerrarPanel();
    navegarA('inicio');
    setTimeout(() => {
        document.getElementById('seccion-mapa-ref')?.scrollIntoView({ behavior: 'smooth' });
    }, 600);
}

// Red de seguridad: cada navegación libera el scroll, por si algún
// panel/modal quedó con overflow:hidden pegado en el body.
// Excepción: alojamiento es una vista fija tipo app (sin scroll),
// así que ahí se bloquea el overflow del body en vez de liberarlo;
// si no, un scroll residual dispara el botón "saltarín" del hero
// sobre los controles propios de alojamiento.
window.navegarA = (function (orig) {
    return function (nombre) {
        document.body.style.overflow = (nombre === 'alojamiento') ? 'hidden' : 'auto';
        return orig.apply(this, arguments);
    };
})(window.navegarA);

/* ============================================
   MODAL DE EVENTOS — Lógica de Apertura / Cierre (Paso 4)
============================================ */
function abrirModalEvento(titulo, fecha, lugar, tipo, img, desc) {
    const modal = document.getElementById('modal-evento');
    if (!modal) return;
    if (titulo) document.getElementById('modal-evento-titulo').textContent = titulo;
    if (fecha) document.getElementById('modal-evento-fecha').textContent = fecha;
    if (lugar) document.getElementById('modal-evento-lugar').textContent = '📍 ' + lugar.replace(/^📍\s*/, '');
    if (tipo) document.getElementById('modal-evento-tipo').textContent = tipo;
    if (desc) document.getElementById('modal-evento-desc').textContent = desc;
    if (img) document.getElementById('modal-evento-img').src = img;

    modal.classList.add('open');
    document.body.style.overflow = 'hidden';
}

function cerrarModalEvento() {
    const modal = document.getElementById('modal-evento');
    if (modal) {
        modal.classList.remove('open');
        document.body.style.overflow = 'auto';
    }
}

function abrirModalEventoDesdeCard(card) {
    if (!card) return;
    const titulo = card.querySelector('.evento-titulo')?.textContent.trim();
    const dia = card.querySelector('.evento-dia')?.textContent.trim() || '';
    const mes = card.querySelector('.evento-mes')?.textContent.trim() || '';
    const ano = card.querySelector('.evento-año')?.textContent.trim() || '';
    const fecha = `${dia} ${mes} ${ano}`.trim();
    const lugar = card.querySelector('.evento-lugar')?.textContent.trim();
    const tipo = card.querySelector('.evento-tipo')?.textContent.trim();
    abrirModalEvento(titulo, fecha, lugar, tipo);
}

document.addEventListener('keydown', e => {
    if (e.key === 'Escape') cerrarModalEvento();
});

