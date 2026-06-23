

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

    // Ícono: imagen si existe, si no primer emoji del primer ingrediente
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
   BASE DE DATOS DE LOCALES
   Agrega, edita o quita objetos de este array.
   Cada local tiene:
     - id, nombre, categoria, descripcion, rating,
       abierto, horario, fotos[], platos[]
   Los platos tienen: nombre, desc, precio, img, ingredientes[]
==================================================== */
const LOCALES = [
    {
        id: 1,
        nombre: "Restaurante La Tradición",
        categoria: "restaurante",
        descripcion: "Cocina antioqueña auténtica desde 1985. Bandeja paisa y sancocho de gallina.",
        rating: "4.8",
        abierto: true,
        horario: "Lun–Dom · 7am – 9pm",
        fotos: [
            "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=600&q=80",
            "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?w=400&q=80",
            "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=400&q=80",
            "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=400&q=80",
            "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=400&q=80"
        ],
        platos: [
            { nombre: "Bandeja Paisa", desc: "Fríjoles, arroz, chicharrón, carne molida, chorizo, morcilla, huevo y aguacate.", precio: "$28.000", img: "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=500&q=80", ingredientes: ["🥚 Huevo","🍚 Arroz","🥑 Aguacate","🫘 Fríjoles","🥩 Carne","🍌 Plátano"] },
            { nombre: "Sancocho Antioqueño", desc: "Gallina criolla con papa, yuca y mazorca. El sabor del hogar.", precio: "$22.000", img: "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=500&q=80", ingredientes: ["🍗 Gallina","🥔 Papa","🌽 Mazorca","🧅 Yuca","🌿 Cilantro","🧄 Ajo"] },
            { nombre: "Cazuela de Fríjoles", desc: "Fríjoles cargamanto con tocino ahumado y plátano maduro.", precio: "$18.000", img: "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=500&q=80", ingredientes: ["🫘 Fríjoles","🥓 Tocino","🌭 Chorizo","🍌 Plátano","🧅 Cebolla","🌿 Cilantro"] }
        ]
    },
    {
        id: 2,
        nombre: "Trucha del Río",
        categoria: "restaurante",
        descripcion: "Especialistas en trucha arco iris fresca, criada en aguas limpias del Cauca.",
        rating: "4.6",
        abierto: true,
        horario: "Mar–Dom · 11am – 8pm",
        fotos: [
            "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?w=600&q=80",
            "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=400&q=80",
            "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=400&q=80",
            "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=400&q=80",
            "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=400&q=80"
        ],
        platos: [
            { nombre: "Trucha al Ajillo", desc: "Trucha fresca en salsa de ajo dorado con limón y ensalada.", precio: "$32.000", img: "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?w=500&q=80", ingredientes: ["🐟 Trucha","🧄 Ajo","🍋 Limón","🫒 Aceite","🌿 Perejil","🥗 Ensalada"] },
            { nombre: "Trucha en Salsa", desc: "Trucha en salsa criolla con arroz y patacones.", precio: "$30.000", img: "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=500&q=80", ingredientes: ["🐟 Trucha","🍅 Tomate","🧅 Cebolla","🌿 Hierbas","🍚 Arroz","🍌 Patacón"] }
        ]
    },
    {
        id: 3,
        nombre: "Tienda El Paisa",
        categoria: "tienda",
        descripcion: "Abarrotes, frutas frescas y productos regionales de la mejor calidad.",
        rating: "4.3",
        abierto: true,
        horario: "Lun–Sáb · 6am – 8pm",
        fotos: [
            "https://images.unsplash.com/photo-1542838132-92c53300491e?w=600&q=80",
            "https://images.unsplash.com/photo-1488459716781-31db52582fe9?w=400&q=80",
            "https://images.unsplash.com/photo-1464226184884-fa280b87c399?w=400&q=80",
            "https://images.unsplash.com/photo-1488459716781-31db52582fe9?w=400&q=80",
            "https://images.unsplash.com/photo-1542838132-92c53300491e?w=400&q=80"
        ],
        platos: [
            { nombre: "Canasta de Frutas", desc: "Selección de frutas frescas de temporada del occidente antioqueño.", precio: "$12.000", img: "https://images.unsplash.com/photo-1464226184884-fa280b87c399?w=500&q=80", ingredientes: ["🍌 Banano","🥭 Mango","🍍 Piña","🍊 Mandarina","🫐 Mora","🍇 Uvas"] },
            { nombre: "Arroz por Kilo", desc: "Arroz blanco de primera calidad empacado al vacío.", precio: "$4.500", img: "https://images.unsplash.com/photo-1542838132-92c53300491e?w=500&q=80", ingredientes: ["🌾 Arroz","📦 Empaque","✅ Certificado","🏔 Local","🌿 Natural","⚖️ 1 Kilo"] }
        ]
    },
    {
        id: 4,
        nombre: "Peluquería Estilo",
        categoria: "servicio",
        descripcion: "Cortes modernos y clásicos para toda la familia. Barbería incluida.",
        rating: "4.5",
        abierto: false,
        horario: "Mar–Dom · 9am – 6pm",
        fotos: [
            "https://images.unsplash.com/photo-1503951914875-452162b0f3f1?w=600&q=80",
            "https://images.unsplash.com/photo-1521590832167-7bcbfaa6381f?w=400&q=80",
            "https://images.unsplash.com/photo-1599351431202-1e0f0137899a?w=400&q=80",
            "https://images.unsplash.com/photo-1503951914875-452162b0f3f1?w=400&q=80",
            "https://images.unsplash.com/photo-1521590832167-7bcbfaa6381f?w=400&q=80"
        ],
        platos: [
            { nombre: "Corte Clásico", desc: "Corte de cabello y arreglo de barba con productos premium.", precio: "$20.000", img: "https://images.unsplash.com/photo-1503951914875-452162b0f3f1?w=500&q=80", ingredientes: ["✂️ Corte","🪒 Barba","💆 Masaje","💧 Lavado","🧴 Productos","💈 Acabado"] },
            { nombre: "Tinte y Corte", desc: "Servicio completo de coloración y corte personalizado.", precio: "$45.000", img: "https://images.unsplash.com/photo-1521590832167-7bcbfaa6381f?w=500&q=80", ingredientes: ["🎨 Tinte","✂️ Corte","💧 Lavado","🧴 Mascarilla","💆 Tratamiento","✨ Secado"] }
        ]
    },
    {
        id: 5,
        nombre: "Panadería La Abuela",
        categoria: "tienda",
        descripcion: "Pan artesanal horneado cada mañana. Pandeyuca, almojábanas y más.",
        rating: "4.9",
        abierto: true,
        horario: "Todos los días · 5am – 12pm",
        fotos: [
            "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=600&q=80",
            "https://images.unsplash.com/photo-1586444248902-2f64eddc13df?w=400&q=80",
            "https://images.unsplash.com/photo-1549931319-a545dcf3bc73?w=400&q=80",
            "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&q=80",
            "https://images.unsplash.com/photo-1586444248902-2f64eddc13df?w=400&q=80"
        ],
        platos: [
            { nombre: "Pandeyuca", desc: "Pandeyuca recién horneado, suave por dentro y dorado por fuera.", precio: "$1.500", img: "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=500&q=80", ingredientes: ["🧀 Queso","🌾 Almidón","🥚 Huevo","🧈 Mantequilla","🧂 Sal","🔥 Horneado"] },
            { nombre: "Almojábana", desc: "Clásica almojábana con queso costeño, suave y esponjosa.", precio: "$2.000", img: "https://images.unsplash.com/photo-1586444248902-2f64eddc13df?w=500&q=80", ingredientes: ["🧀 Queso","🌾 Maíz","🥚 Huevo","🧈 Manteca","🧂 Sal","✨ Artesanal"] }
        ]
    },
    {
        id: 6,
        nombre: "Farmacia San Rafael",
        categoria: "servicio",
        descripcion: "Medicamentos, vitaminas y atención farmacéutica personalizada.",
        rating: "4.4",
        abierto: true,
        horario: "Lun–Sáb · 8am – 7pm",
        fotos: [
            "https://images.unsplash.com/photo-1585435557343-3b092031a831?w=600&q=80",
            "https://images.unsplash.com/photo-1576602976047-174e57a47881?w=400&q=80",
            "https://images.unsplash.com/photo-1587370560942-ad2a04eabb6d?w=400&q=80",
            "https://images.unsplash.com/photo-1585435557343-3b092031a831?w=400&q=80",
            "https://images.unsplash.com/photo-1576602976047-174e57a47881?w=400&q=80"
        ],
        platos: [
            { nombre: "Consulta Farmacéutica", desc: "Orientación profesional sobre medicamentos y tratamientos.", precio: "Gratis", img: "https://images.unsplash.com/photo-1585435557343-3b092031a831?w=500&q=80", ingredientes: ["💊 Medicamentos","🩺 Asesoría","📋 Receta","🧪 Análisis","💉 Vacunas","❤️ Cuidado"] }
        ]
    },
    {
        id: 7,
        nombre: "Asadero El Rincón",
        categoria: "restaurante",
        descripcion: "Pollos y carnes a la brasa con sazón casera. El favorito del barrio.",
        rating: "4.7",
        abierto: true,
        horario: "Miér–Dom · 12pm – 9pm",
        fotos: [
            "https://images.unsplash.com/photo-1544025162-d76694265947?w=600&q=80",
            "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&q=80",
            "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=400&q=80",
            "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=400&q=80",
            "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=400&q=80"
        ],
        platos: [
            { nombre: "Pollo a la Brasa", desc: "Pollo entero marinado 12 horas y asado a la leña. Incluye papa y ensalada.", precio: "$38.000", img: "https://images.unsplash.com/photo-1544025162-d76694265947?w=500&q=80", ingredientes: ["🍗 Pollo","🥔 Papa","🥗 Ensalada","🧄 Ajo","🌿 Hierbas","🔥 Brasa"] },
            { nombre: "Costilla BBQ", desc: "Costilla de cerdo en salsa barbacoa ahumada con yuca frita.", precio: "$35.000", img: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=500&q=80", ingredientes: ["🥩 Costilla","🍖 Cerdo","🫙 BBQ","🌽 Yuca","🥗 Ensalada","🔥 Ahumado"] }
        ]
    },
    {
        id: 8,
        nombre: "Ferretería Central",
        categoria: "tienda",
        descripcion: "Materiales de construcción, herramientas y artículos para el hogar.",
        rating: "4.1",
        abierto: false,
        horario: "Lun–Sáb · 7am – 5pm",
        fotos: [
            "https://images.unsplash.com/photo-1504148455328-c376907d081c?w=600&q=80",
            "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&q=80",
            "https://images.unsplash.com/photo-1504148455328-c376907d081c?w=400&q=80",
            "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400&q=80",
            "https://images.unsplash.com/photo-1504148455328-c376907d081c?w=400&q=80"
        ],
        platos: [
            { nombre: "Kit Básico Herramientas", desc: "Martillo, destornilladores, alicates y cinta métrica.", precio: "$85.000", img: "https://images.unsplash.com/photo-1504148455328-c376907d081c?w=500&q=80", ingredientes: ["🔨 Martillo","🪛 Destornillador","🔧 Alicates","📏 Cinta","⚙️ Tornillos","🧰 Estuche"] }
        ]
    }
    /*
        ← AGREGA MÁS LOCALES AQUÍ
        Copia un bloque completo y edita los campos.
        La búsqueda y filtros funcionan automáticamente.
    */
];

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

    // Burbujas de ingredientes
    plato.ingredientes.forEach((ing, i) => {
        const lbl = document.getElementById(`burbuja-lbl-${i+1}`);
        const img = document.getElementById(`burbuja-img-${i+1}`);
        if (lbl) lbl.textContent = ing;
        if (img) img.textContent = ing.split(' ')[0]; // el emoji
    });

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
    renderizarDirectorio();
});
