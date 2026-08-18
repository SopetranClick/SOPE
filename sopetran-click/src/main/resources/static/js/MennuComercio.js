

    /* ============================================================
    CATEGORÍAS QUE USAN LA PRESENTACIÓN FOOD (orbital)
    Todo lo demás → pantalla-servicio
    ============================================================ */
    const CATEGORIAS_FOOD = ['restaurante'];  // agrega 'tienda' si quieres que la panadería use orbital tb

    /* ============================================================
    OVERRIDE de abrirLocal — decide qué pantalla mostrar
    ============================================================ */
    const _abrirLocalOriginal = window.abrirLocal;
    window.abrirLocal = function(id) {
    const local = LOCALES.find(l => l.id === id);
    if (!local) return;

    // ¿Es categoría food?
    if (CATEGORIAS_FOOD.includes(local.categoria)) {
    // Comportamiento original (portada + orbital)
    _abrirLocalOriginal(id);
} else {
    // Presentación alternativa para servicios y tiendas no-food
    abrirLocalServicio(local);
}
};

    /* ============================================================
    ABRIR LOCAL SERVICIO (pantalla C)
    ============================================================ */
    function abrirLocalServicio(local) {
    // Hero
    document.getElementById('srv-hero-img').src = local.fotos[0] || '';
    document.getElementById('srv-nombre').textContent = local.nombre;
    document.getElementById('srv-desc-txt').textContent = local.descripcion;

    const catLabel = local.categoria === 'tienda' ? '🛍 Tienda' :
    local.categoria === 'servicio' ? '⚙️ Servicio' : local.categoria;
    document.getElementById('srv-cat-badge').textContent = catLabel;
    document.getElementById('srv-tipo-label').textContent =
    (local.categoria.charAt(0).toUpperCase() + local.categoria.slice(1)) + ' · Sopetrán';

    const dotEl = document.getElementById('srv-dot');
    dotEl.className = 'srv-dot' + (local.abierto ? '' : ' cerrado');
    document.getElementById('srv-status-txt').textContent =
    (local.abierto ? 'Abierto' : 'Cerrado') + ' · ' + local.horario;
    document.getElementById('srv-rating').textContent = '★ ' + local.rating;

    // Título catálogo
    document.getElementById('srv-catalogo-titulo').textContent =
    local.categoria === 'servicio' ? 'Servicios disponibles' : 'Productos disponibles';

    // Fotos grid (las fotos 1-n, no la principal que ya está en hero)
    const fotosGrid = document.getElementById('srv-fotos-grid');
    fotosGrid.innerHTML = '';
    local.fotos.slice(1).forEach(url => {
    const div = document.createElement('div');
    div.className = 'srv-foto-item';
    div.innerHTML = `<img src="${url}" alt="Foto de ${local.nombre}" loading="lazy">`;
    fotosGrid.appendChild(div);
});

    // Lista de items (platos → productos/servicios)
    const listaEl = document.getElementById('srv-items-lista');
    listaEl.innerHTML = '';
    local.platos.forEach(item => {
    const div = document.createElement('div');
    div.className = 'srv-item';

    // Ícono: imagen.txt si existe, si no primer emoji del primer ingrediente
    const iconoHtml = item.img
    ? `<div class="srv-item-icon"><img src="${item.img}" alt="${item.nombre}"></div>`
    : `<div class="srv-item-icon">${item.ingredientes[0]?.split(' ')[0] || '📦'}</div>`;

    const precioClass = item.precio === 'Gratis' ? 'srv-item-precio gratis' : 'srv-item-precio';

    div.innerHTML = `
            ${iconoHtml}
            <div class="srv-item-info">
                <div class="srv-item-nombre">${item.nombre}</div>
                <div class="srv-item-desc">${item.desc}</div>
            </div>
            <div class="${precioClass}">${item.precio}</div>
        `;
    listaEl.appendChild(div);
});

    // Ocultar/mostrar pantallas
    document.getElementById('pantalla-directorio').style.display = 'none';
    document.getElementById('pantalla-local').style.display = 'none';
    document.getElementById('pantalla-servicio').style.display = 'block';
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

    /* ============================================================
    OVERRIDE volverDirectorio para incluir pantalla-servicio
    ============================================================ */
    const _volverDirOriginal = window.volverDirectorio;
    window.volverDirectorio = function() {
    document.getElementById('pantalla-servicio').style.display = 'none';
    _volverDirOriginal();
};



    // Notify parent to hide its global "volver" button — comercio manages its own navigation
    (function() {
    try {
    // Hide parent's floating volver button while inside comercio iframe
    if (window.parent && window.parent !== window) {
    const parentBtn = window.parent.document.getElementById('btn-volver-global');
    if (parentBtn) parentBtn.style.display = 'none';
}
} catch(e) {}
})();


/* ====================================================
   DATOS DE LOCALES — cargados desde la API real
   Cada local tiene:
     - id, nombre, categoria, descripcion, rating,
       abierto, horario, fotos[], platos[]
   Los platos tienen: nombre, desc, precio, img, ingredientes[]
==================================================== */
let LOCALES = [];

function formatCOP(precio) {
    if (precio === null || precio === undefined) return '—';
    return new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', maximumFractionDigits: 0 }).format(precio);
}

/** Trae los platos/productos de un local o restaurante y los normaliza */
async function cargarPlatosDe(item, esRestaurante) {
    const url = esRestaurante
        ? `/api/dishes/restaurant/${item.idRestaurant}`
        : `/api/product/local/${item.idLocal}`;
    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error('HTTP ' + res.status);
        const items = await res.json();
        return items.map(p => ({
            nombre: p.name,
            desc: p.description,
            precio: formatCOP(p.price),
            img: p.imageUrl || '/img/logo-servicio/asado.png',
            ingredientes: []
        }));
    } catch (e) {
        console.error('Error cargando platos/productos de', url, e);
        return [];
    }
}

/** Carga restaurantes + locales desde la API y arma el array LOCALES */
async function cargarLocales() {
    const [resRestaurantes, resLocales] = await Promise.all([
        fetch('/api/restaurant'),
        fetch('/api/local')
    ]);
    if (!resRestaurantes.ok || !resLocales.ok) throw new Error('No se pudo cargar el directorio de comercio.');

    const restaurantes = await resRestaurantes.json();
    const locales = await resLocales.json();

    const restaurantesNormalizados = await Promise.all(restaurantes.map(async r => ({
        id: r.idRestaurant,
        nombre: r.nombre,
        categoria: (r.categoria || 'restaurante').toLowerCase(),
        descripcion: r.description,
        rating: r.rating != null ? r.rating : '—',
        abierto: r.abierto !== false,
        horario: r.horario || 'Horario no disponible',
        fotos: [r.coverUrl, ...(r.gallery || [])].filter(Boolean),
        platos: await cargarPlatosDe(r, true)
    })));

    const localesNormalizados = await Promise.all(locales.map(async l => ({
        id: l.idLocal,
        nombre: l.nombre,
        categoria: (l.categoria || l.tipoLocal || 'servicio').toLowerCase(),
        descripcion: l.description,
        rating: l.rating != null ? l.rating : '—',
        abierto: l.abierto !== false,
        horario: l.horario || 'Horario no disponible',
        fotos: [l.coverUrl, ...(l.gallery || [])].filter(Boolean),
        platos: await cargarPlatosDe(l, false)
    })));

    LOCALES = [...restaurantesNormalizados, ...localesNormalizados];
}

/* ====================================================
   ESTADO DE LA APP
==================================================== */
let filtroActual   = 'todos';   // categoría activa
let localActual    = null;      // objeto del local abierto
let platoActual    = 0;         // índice del plato en el menú

/* ====================================================
   RENDERIZADO DEL DIRECTORIO
==================================================== */

/** Construye y muestra las tarjetas según búsqueda y filtro */
function renderizarDirectorio() {
    const query  = document.getElementById('input-buscar').value.toLowerCase().trim();
    const grid   = document.getElementById('grid-locales');
    const sinRes = document.getElementById('sin-resultados');

    // Filtra el array
    const visibles = LOCALES.filter(local => {
        const matchFiltro =
            filtroActual === 'todos'   ? true :
            filtroActual === 'abierto' ? local.abierto :
            local.categoria === filtroActual;

        const matchBusqueda =
            !query ||
            local.nombre.toLowerCase().includes(query) ||
            local.descripcion.toLowerCase().includes(query) ||
            local.categoria.toLowerCase().includes(query);

        return matchFiltro && matchBusqueda;
    });

    // Actualiza contador
    document.getElementById('num-resultados').textContent = visibles.length;

    // Limpia el grid (excepto el mensaje de sin resultados)
    grid.querySelectorAll('.local-card').forEach(c => c.remove());

    // Sin resultados
    sinRes.style.display = visibles.length === 0 ? 'block' : 'none';

    // Genera las tarjetas con delay escalonado para animación
    visibles.forEach((local, i) => {
        const card = document.createElement('div');
        card.className = 'local-card';
        card.style.animationDelay = `${i * 60}ms`;
        card.onclick = () => abrirLocal(local.id);

        const badgeClass =
            local.categoria === 'restaurante' ? 'badge-restaurante' :
            local.categoria === 'tienda'      ? 'badge-tienda' :
            'badge-servicio';

        const badgeLabel =
            local.categoria === 'restaurante' ? '🍽 Restaurante' :
            local.categoria === 'tienda'      ? '🛍 Tienda' :
            '⚙️ Servicio';

        card.innerHTML = `
            <div class="local-card-img">
                <img src="${local.fotos[0]}" alt="${local.nombre}" loading="lazy">
                <span class="badge-categoria ${badgeClass}">${badgeLabel}</span>
                <span class="rating-badge"><span class="estrella">★</span>${local.rating}</span>
            </div>
            <div class="local-card-body">
                <h3>${local.nombre}</h3>
                <p class="descripcion">${local.descripcion}</p>
                <div class="local-card-footer">
                    <span class="horario">
                        <span class="dot ${local.abierto ? '' : 'cerrado'}"></span>
                        ${local.abierto ? 'Abierto' : 'Cerrado'} · ${local.horario}
                    </span>
                    <button class="btn-entrar">Ver local →</button>
                </div>
            </div>
        `;

        grid.appendChild(card);
    });
}

/** Cambia el filtro de categoría activo */
function setFiltro(categoria, btn) {
    filtroActual = categoria;
    document.querySelectorAll('.filtro-btn').forEach(b => b.classList.remove('activo'));
    btn.classList.add('activo');
    renderizarDirectorio();
}

/** Refiltra al escribir en el buscador */
function filtrarLocales() {
    renderizarDirectorio();
}

/* ====================================================
   NAVEGACIÓN ENTRE PANTALLAS
==================================================== */

/** Abre un local por su ID */
function abrirLocal(id) {
    localActual = LOCALES.find(l => l.id === id);
    if (!localActual) return;

    // Rellena la portada del local
    document.getElementById('portada-etiqueta').textContent =
        (localActual.categoria.charAt(0).toUpperCase() + localActual.categoria.slice(1)) + ' · Sopetrán';
    document.getElementById('portada-nombre').textContent  = localActual.nombre;
    document.getElementById('portada-horario').textContent = localActual.horario;

    // Construye la cuadrícula de fotos
    const grid = document.getElementById('portada-fotos');
    grid.innerHTML = '';
    // Reinicia animación
    grid.style.opacity = '0'; grid.style.transform = 'scale(0.96)';
    requestAnimationFrame(() => {
        grid.style.animation = 'none';
        requestAnimationFrame(() => {
            grid.style.animation = 'aparecer 0.7s ease forwards 0.2s';
        });
    });

    localActual.fotos.forEach((url, i) => {
        const div = document.createElement('div');
        div.className = 'foto-item' + (i === 0 ? ' foto-grande' : '');
        div.innerHTML = `<img src="${url}" alt="Foto ${i+1} de ${localActual.nombre}" loading="lazy">`;
        grid.appendChild(div);
    });

    // Cambia de pantalla
    document.getElementById('pantalla-directorio').style.display = 'none';
    document.getElementById('pantalla-local').style.display      = 'block';
    document.getElementById('local-portada').style.display       = 'flex';
    document.getElementById('local-menu').style.display          = 'none';
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

/** Vuelve al directorio */
function volverDirectorio() {
    document.getElementById('pantalla-local').style.display      = 'none';
    document.getElementById('pantalla-directorio').style.display = 'block';
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

/** Pasa de portada a menú orbital */
function mostrarMenuLocal() {
    if (!localActual || !localActual.platos.length) return;

    document.getElementById('local-portada').style.display = 'none';
    document.getElementById('local-menu').style.display    = 'block';

    // Cabecera del menú
    document.getElementById('menu-sub').textContent    = localActual.nombre + ' · Menú';
    document.getElementById('menu-titulo').textContent = 'Orbital Bento';

    // Genera el catálogo lateral
    const lista = document.getElementById('lista-catalogo');
    lista.innerHTML = '';
    localActual.platos.forEach((plato, i) => {
        const card = document.createElement('div');
        card.className = 'catalogo-card' + (i === 0 ? ' activo' : '');
        card.onclick   = () => seleccionarPlato(i);
        card.innerHTML = `
            <img src="${plato.img}" alt="${plato.nombre}">
            <div>
                <h4>${plato.nombre}</h4>
                <p>${plato.desc.substring(0, 55)}…</p>
            </div>
            <span class="catalogo-precio">${plato.precio.replace('.000','k')}</span>
        `;
        lista.appendChild(card);
    });

    // Carga el primer plato
    seleccionarPlato(0);
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

/** Vuelve a la portada del local */
function mostrarPortadaLocal() {
    document.getElementById('local-menu').style.display    = 'none';
    document.getElementById('local-portada').style.display = 'flex';
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

/* ====================================================
   SELECCIÓN DE PLATO EN EL MENÚ ORBITAL
==================================================== */
function seleccionarPlato(indice) {
    if (!localActual) return;
    const plato = localActual.platos[indice];
    if (!plato) return;

    platoActual = indice;

    // Foto con transición suave
    const imgPlato = document.getElementById('img-plato');
    imgPlato.style.opacity   = '0';
    imgPlato.style.transform = 'scale(0.88)';
    setTimeout(() => {
        imgPlato.src = plato.img;
        imgPlato.style.transition = 'opacity 0.4s, transform 0.4s';
        imgPlato.style.opacity    = '1';
        imgPlato.style.transform  = 'scale(1)';
    }, 200);

    // Nombre y precio en la escena orbital
    document.getElementById('titulo-plato').textContent  = plato.nombre;
    document.getElementById('precio-orbital').textContent = plato.precio + ' COP';

    // Burbujas de ingredientes (solo si el plato trae datos reales)
    const tieneIngredientes = plato.ingredientes && plato.ingredientes.length > 0;
    document.querySelectorAll('.burbuja').forEach(b => {
        b.style.display = tieneIngredientes ? '' : 'none';
    });
    if (tieneIngredientes) {
        plato.ingredientes.forEach((ing, i) => {
            const lbl = document.getElementById(`burbuja-lbl-${i+1}`);
            const img = document.getElementById(`burbuja-img-${i+1}`);
            if (lbl) lbl.textContent = ing;
            if (img) img.textContent = ing.split(' ')[0]; // el emoji
        });
    }

    // Panel de detalle inferior
    document.getElementById('detalle-nombre').textContent = plato.nombre;
    document.getElementById('detalle-desc').textContent   = plato.desc;
    document.getElementById('detalle-precio').textContent = plato.precio;

    // Marca la tarjeta activa
    document.querySelectorAll('#lista-catalogo .catalogo-card').forEach((card, i) => {
        card.classList.toggle('activo', i === indice);
    });
}

/* ====================================================
   ARRANQUE
==================================================== */
document.addEventListener('DOMContentLoaded', () => {
    cargarLocales()
        .then(renderizarDirectorio)
        .catch(e => {
            console.error('Error cargando el directorio de comercio:', e);
            const grid = document.getElementById('grid-locales');
            if (grid) {
                const msg = document.createElement('p');
                msg.className = 'sin-resultados';
                msg.textContent = 'No se pudo cargar el directorio de comercio.';
                grid.appendChild(msg);
            }
        });
});

/* ====================================================
   ÓRDENES Y RESERVAS (Integración Auth)
==================================================== */
function intentarOrdenComercio() {
    const jwt = localStorage.getItem('auth_jwt');
    if (!jwt) {
        if (typeof abrirModalAuth === 'function') abrirModalAuth();
        else alert('Por favor, inicia sesión para ordenar.');
        return;
    }
    
    if (!localActual || !localActual.platos || localActual.platos.length === 0) return;
    const plato = localActual.platos[platoActual];
    if (!plato) return;
    
    const orden = {
        id: Date.now(),
        fecha: new Date().toLocaleDateString(),
        local: localActual.nombre,
        item: plato.nombre,
        precio: plato.precio,
        tipo: 'Comercio'
    };
    
    let historial = JSON.parse(localStorage.getItem('historial_reservas') || '[]');
    historial.push(orden);
    localStorage.setItem('historial_reservas', JSON.stringify(historial));
    
    alert(`¡Orden de ${plato.nombre} en ${localActual.nombre} procesada con éxito!`);
}
