
    /* ================================================================
    EXTENSIÓN — PANTALLA C: DETALLE DE LUGAR
    ================================================================ */

    let veredaActualDetalle = null;
    let lugarActualDetalle  = null;

    /* Abre la pantalla de detalle de un lugar */
    function abrirLugar(veredaId, lugarIndex) {
    const vereda = VEREDAS.find(v => v.id === veredaId);
    if (!vereda) return;
    const lugar = vereda.lugares[lugarIndex];
    if (!lugar) return;

    veredaActualDetalle = vereda;
    lugarActualDetalle  = lugar;

    // Hero img
    const heroImg = document.getElementById('detalle-hero-img');
    if (lugar.img) {
    heroImg.src = lugar.img;
    heroImg.style.display = 'block';
} else {
    heroImg.src = '';
    heroImg.style.display = 'none';
    // Fondo degradado como fallback
    document.querySelector('.detalle-hero').style.background =
    'linear-gradient(135deg, #0a1a0a 0%, #1a2a1a 50%, #0a0a0a 100%)';
}

    // Breadcrumb
    document.getElementById('det-bc-vereda').textContent = vereda.nombre;
    document.getElementById('det-bc-lugar').textContent  = lugar.nombre;

    // Título y subtipo
    document.getElementById('det-nombre').textContent  = lugar.nombre;
    document.getElementById('det-subtipo').textContent = lugar.subtipo || lugar.tipo;

    // Descripción
    document.getElementById('det-desc').textContent = lugar.desc;

    // Info rápida del lugar
    const infoGrid = document.getElementById('det-info-grid');
    const infoItems = [
{ icon: '🗺️', label: 'Tipo', valor: lugar.tipo },
{ icon: '📍', label: 'Vereda', valor: vereda.nombre },
{ icon: '🏷️', label: 'Categoría', valor: lugar.subtipo || '—' },
{ icon: '🥾', label: 'Acceso', valor: lugar.acceso || 'Sendero' },
    ];
    infoGrid.innerHTML = infoItems.map(i => `
        <div class="detalle-info-item">
            <div class="det-info-icon">${i.icon}</div>
            <div class="det-info-label">${i.label}</div>
            <div class="det-info-valor">${i.valor}</div>
        </div>
    `).join('');

    // Badges en el hero
    const badgesEl = document.getElementById('det-badges');
    badgesEl.innerHTML = (lugar.tags || []).map(t =>
    `<span class="detalle-badge">${t}</span>`
    ).join('');

    // Tags abajo
    const tagsEl = document.getElementById('det-tags');
    tagsEl.innerHTML = (lugar.tags || []).map(t =>
    `<span class="det-tag">${t}</span>`
    ).join('');

    // Galería — usa lugar.galeria[] si existe, sino fallback con lugar.img
    const galeriaEl = document.getElementById('det-galeria');
    galeriaEl.innerHTML = '';
    const imgs = lugar.galeria && lugar.galeria.length ? lugar.galeria
    : lugar.img ? [lugar.img, lugar.img, lugar.img]
    : [];

    if (imgs.length === 0) {
    // Placeholders con el icono
    for (let i = 0; i < 4; i++) {
    const div = document.createElement('div');
    div.className = 'detalle-galeria-item';
    div.innerHTML = `<div class="galeria-placeholder">${lugar.icono}</div>`;
    galeriaEl.appendChild(div);
}
} else {
    imgs.forEach((url, i) => {
    const div = document.createElement('div');
    div.className = 'detalle-galeria-item';
    div.innerHTML = `<img src="${url}" alt="Foto ${i+1}">`;
    div.onclick = () => abrirLightbox(i, imgs, lugar.desc);
    galeriaEl.appendChild(div);
});
}

    // Lugares relacionados (otros de la misma vereda)
    const relEl = document.getElementById('det-relacionados');
    relEl.innerHTML = '';
    vereda.lugares
    .filter(l => l.nombre !== lugar.nombre)
    .forEach((l, i) => {
    const card = document.createElement('div');
    card.className = 'rel-card';
    card.onclick = () => abrirLugar(veredaId, vereda.lugares.indexOf(l));
    card.innerHTML = l.img
    ? `<img class="rel-card-img" src="${l.img}" alt="${l.nombre}">`
    : `<div class="rel-card-placeholder">${l.icono}</div>`;
    card.innerHTML += `
                <div class="rel-card-body">
                    <div class="rel-card-nombre">${l.nombre}</div>
                    <div class="rel-card-tipo">${l.tipo}</div>
                </div>
            `;
    relEl.appendChild(card);
});

    // Mostrar pantalla C
    document.getElementById('pantalla-lugares').style.display  = 'none';
    document.getElementById('pantalla-veredas').style.display  = 'none';
    const pantallaD = document.getElementById('pantalla-detalle');
    pantallaD.style.display = 'block';
    pantallaD.style.animation = 'none';
    requestAnimationFrame(() => {
    pantallaD.style.animation = 'slideInDetalle 0.4s ease forwards';
});
    window.scrollTo({ top: 0, behavior: 'smooth' });

    // Breadcrumb global
    document.getElementById('eco-breadcrumb').innerHTML = `
        <span style="cursor:pointer;color:var(--texto-dim)" onclick="volverAVeredas()">Veredas</span>
        <i class="bi bi-chevron-right"></i>
        <span style="cursor:pointer;color:var(--texto-dim)" onclick="volverDesdeLugar()">${vereda.nombre}</span>
        <i class="bi bi-chevron-right"></i>
        <span class="activo">${lugar.nombre}</span>
    `;
}

    /* Vuelve a la lista de lugares de la vereda */
    function volverDesdeLugar() {
    if (!veredaActualDetalle) { volverAVeredas(); return; }
    document.getElementById('pantalla-detalle').style.display = 'none';
    abrirVereda(veredaActualDetalle.id);
}

    let currentLightboxIndex = 0;
    let currentLightboxImgs = [];

    /* Lightbox (Paso 3) */
    function abrirLightbox(index, imgsArray, desc = '') {
        if (!imgsArray || imgsArray.length === 0) {
            // fallback
            if (typeof index === 'string') {
                imgsArray = [index];
                index = 0;
            } else {
                return;
            }
        }
        currentLightboxIndex = index;
        currentLightboxImgs = imgsArray;
        
        const lb = document.getElementById('lightbox');
        const img = document.getElementById('lightbox-img');
        const descEl = document.getElementById('lightbox-desc');
        
        if (img) img.src = currentLightboxImgs[currentLightboxIndex];
        if (descEl) descEl.textContent = desc;
        
        if (lb) lb.classList.add('open');
        document.body.classList.add('sin-scroll');
        document.body.style.overflow = 'hidden';
    }
    
    function prevLightboxImg() {
        if (currentLightboxImgs.length === 0) return;
        currentLightboxIndex = (currentLightboxIndex - 1 + currentLightboxImgs.length) % currentLightboxImgs.length;
        const img = document.getElementById('lightbox-img');
        if (img) img.src = currentLightboxImgs[currentLightboxIndex];
    }
    
    function nextLightboxImg() {
        if (currentLightboxImgs.length === 0) return;
        currentLightboxIndex = (currentLightboxIndex + 1) % currentLightboxImgs.length;
        const img = document.getElementById('lightbox-img');
        if (img) img.src = currentLightboxImgs[currentLightboxIndex];
    }

    function cerrarLightbox() {
        const lb = document.getElementById('lightbox');
        if (lb) lb.classList.remove('open');
        document.body.classList.remove('sin-scroll');
        document.body.style.overflow = 'auto';
    }
    
    document.addEventListener('keydown', e => { 
        if (e.key === 'Escape') cerrarLightbox(); 
        if (e.key === 'ArrowLeft' && document.getElementById('lightbox')?.classList.contains('open')) prevLightboxImg();
        if (e.key === 'ArrowRight' && document.getElementById('lightbox')?.classList.contains('open')) nextLightboxImg();
    });

    /* Compartir lugar */
    function compartirLugar() {
    const texto = lugarActualDetalle
    ? `¡Visita ${lugarActualDetalle.nombre} en Sopetrán! 🌿 via SopetranClick`
    : 'Descubre Sopetrán con SopetranClick';
    compartirContenido('SopetranClick', texto, window.location.href);
}

    /* ---- Patch de abrirVereda para añadir click en lugar-card ---- */
    const _abrirVeredaOriginal = abrirVereda;
    window.abrirVereda = function(id) {
    _abrirVeredaOriginal(id);
    // Agregar onclick a cada lugar-card después de render
    setTimeout(() => {
    const vereda = VEREDAS.find(v => v.id === id);
    if (!vereda) return;
    document.querySelectorAll('#lugares-grid .lugar-card').forEach((card, i) => {
    card.style.cursor = 'pointer';
    card.onclick = () => abrirLugar(id, i);
});
}, 50);
};



    // Notify parent to hide its global "volver" button — ecoturismo manages its own navigation
    (function() {
    try {
    if (window.parent && window.parent !== window) {
    const parentBtn = window.parent.document.getElementById('btn-volver-global');
    if (parentBtn) parentBtn.style.display = 'none';
}
} catch(e) {}
})();


/* ================================================================
   DATOS — Veredas y sus lugares icónicos, cargados desde la API real
================================================================ */
let VEREDAS = [];

function parseTags(s) {
    return (s || '').split(',').map(t => t.trim()).filter(Boolean);
}

const ICONOS_POR_TIPO = {
    'cascada': '💧', 'mirador': '🦅', 'rio': '🏞️', 'río': '🏞️', 'laguna': '🌊',
    'patrimonio': '⛪', 'agroturismo': '🌱', 'artesanía': '🎍', 'artesania': '🎍',
    'senderismo': '🥾', 'hospedaje': '🏡', 'camping': '⛺', 'puerto': '⛵', 'naturaleza': '🌳'
};
function derivarIcono(valor) {
    if (!valor) return '📍';
    return ICONOS_POR_TIPO[valor.toLowerCase().trim()] || '📍';
}

/** Carga veredas (Site) y sus lugares icónicos desde la API */
async function cargarVeredas() {
    const res = await fetch('/api/sites');
    if (!res.ok) throw new Error('HTTP ' + res.status);
    const sites = await res.json();

    VEREDAS = await Promise.all(sites.map(async site => {
        let lugares = [];
        try {
            const resLugares = await fetch(`/api/sites/${site.idVereda}/iconic-places`);
            if (resLugares.ok) lugares = await resLugares.json();
        } catch (e) {
            console.error('Error cargando lugares de la vereda', site.idVereda, e);
        }
        const tagsVereda = parseTags(site.tags);
        return {
            id: site.idVereda,
            nombre: site.nombreVereda,
            icono: derivarIcono(tagsVereda[0]),
            tags: tagsVereda,
            cantidad: lugares.length,
            img: site.coverUrl,
            lugares: lugares.map(l => ({
                nombre: l.nombreLugar,
                tipo: l.tipo || '—',
                subtipo: l.tipo || '—',
                desc: l.indicaciones,
                tags: parseTags(l.tags),
                icono: derivarIcono(l.tipo),
                img: l.coverUrl,
                galeria: l.gallery || [],
                acceso: l.acceso
            }))
        };
    }));
}

/* ================================================================
   RENDER — PANTALLA A: VEREDAS
================================================================ */
function renderVeredas() {
    const grid = document.getElementById('veredas-grid');
    grid.innerHTML = '';

    VEREDAS.forEach(v => {
        const card = document.createElement('div');
        card.className = 'vereda-card';
        card.onclick = () => abrirVereda(v.id);

        const imgHtml = v.img
            ? `<img class="vereda-img" src="${v.img}" alt="${v.nombre}">`
            : `<div class="vereda-img-placeholder">${v.icono}</div>`;

        const tagsHtml = v.tags.map(t => `<span class="vereda-tag">${t}</span>`).join('');

        card.innerHTML = `
            ${imgHtml}
            <div class="vereda-body">
                <div class="vereda-arrow"><i class="bi bi-arrow-right"></i></div>
                <h3 class="vereda-name">${v.nombre}</h3>
                <div class="vereda-cantidad">
                    <i class="bi bi-geo-alt-fill"></i>
                    ${v.cantidad} lugar${v.cantidad !== 1 ? 'es' : ''} icónico${v.cantidad !== 1 ? 's' : ''}
                </div>
                <div class="vereda-iconos">${tagsHtml}</div>
            </div>
        `;

        grid.appendChild(card);
    });
}

/* ================================================================
   RENDER — PANTALLA B: LUGARES
================================================================ */
function abrirVereda(id) {
    const vereda = VEREDAS.find(v => v.id === id);
    if (!vereda) return;

    // Actualizar portal info
    document.getElementById('portal-icono').textContent = vereda.icono;
    document.getElementById('portal-nombre').textContent = vereda.nombre;
    document.getElementById('portal-label').textContent =
        `Actual vereda seleccionada · ${vereda.lugares.length} lugares`;

    // Breadcrumb
    document.getElementById('eco-breadcrumb').innerHTML = `
        <span style="cursor:pointer;color:var(--texto-dim)" onclick="volverAVeredas()">Veredas</span>
        <i class="bi bi-chevron-right"></i>
        <span class="activo">${vereda.nombre}</span>
    `;

    // Render lugares
    const grid = document.getElementById('lugares-grid');
    grid.innerHTML = '';

    vereda.lugares.forEach(l => {
        const card = document.createElement('div');
        card.className = 'lugar-card';

        const imgHtml = l.img
            ? `<img class="lugar-img" src="${l.img}" alt="${l.nombre}">`
            : `<div class="lugar-img-placeholder">${l.icono}</div>`;

        const tagsHtml = l.tags.map(t => `<span class="lugar-tag verde">${t}</span>`).join('');

        card.innerHTML = `
            <div class="lugar-img-wrap">
                ${imgHtml}
                <span class="lugar-tipo-badge">${l.tipo}</span>
                <div class="lugar-acciones">
                    <button class="lugar-accion-btn" title="Senderismo"><i class="bi bi-person-walking"></i></button>
                    <button class="lugar-accion-btn" title="Favorito"><i class="bi bi-heart"></i></button>
                    <button class="lugar-accion-btn" title="Fotografía"><i class="bi bi-camera"></i></button>
                </div>
            </div>
            <div class="lugar-body">
                <h4 class="lugar-nombre">${l.nombre}</h4>
                <p class="lugar-subtipo">${l.subtipo}</p>
                <p class="lugar-desc">${l.desc}</p>
                <div class="lugar-footer-tags">${tagsHtml}</div>
            </div>
        `;

        grid.appendChild(card);
    });

    // Animación: salida de veredas → entrada de lugares
    const pantallaV = document.getElementById('pantalla-veredas');
    const pantallaL = document.getElementById('pantalla-lugares');

    pantallaV.classList.add('saliendo');
    setTimeout(() => {
        pantallaV.style.display = 'none';
        pantallaL.style.display = 'block';
        pantallaL.classList.add('visible', 'entrando');
        setTimeout(() => pantallaL.classList.remove('entrando'), 450);
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }, 380);
}

function volverAVeredas() {
    const pantallaV = document.getElementById('pantalla-veredas');
    const pantallaL = document.getElementById('pantalla-lugares');

    pantallaL.style.display = 'none';
    pantallaL.classList.remove('visible');

    pantallaV.style.display = 'block';
    pantallaV.classList.remove('saliendo');

    // Breadcrumb reset
    document.getElementById('eco-breadcrumb').innerHTML =
        `<span class="activo">Veredas</span>`;

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

/* ---- INIT ---- */
cargarVeredas()
    .then(renderVeredas)
    .catch(e => {
        console.error('Error cargando veredas:', e);
        const grid = document.getElementById('veredas-grid');
        if (grid) grid.innerHTML = '<p class="sin-resultados">No se pudieron cargar las veredas.</p>';
    });

/* ================================================================
   NOTA PARA INTEGRACIÓN EN Index.html
   ---------------------------------------------------------------
   1. Copia TODO el contenido del <section id="vista-ecoturismo">
      y pégalo dentro de <main id="main-content"> en tu Index.html,
      reemplazando el placeholder que ya existe.

   2. Copia el bloque <style> al <head> de Index.html
      (o a tu archivo index.css).

   3. Copia el bloque <script> al final del <body>,
      ANTES de tu script del SPA router — así las funciones
      renderVeredas() y abrirVereda() estarán disponibles.

   4. El botón eco-back-btn ya llama a navegarA('inicio')
      que es tu función existente del SPA router. ✅

   5. Para agregar imágenes reales a veredas y lugares:
      Cambia el campo img: null  →  img: '/Img/Veredas/nombre.jpg'
================================================================ */