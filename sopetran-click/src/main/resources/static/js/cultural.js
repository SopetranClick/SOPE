/* ════════════════════════════════════════
   DATOS — cargados desde la API real
════════════════════════════════════════ */
let eventos = [];
let fechasEspeciales = [];
let historia = [];

const MESES_ABBR = ['Ene','Feb','Mar','Abr','May','Jun','Jul','Ago','Sep','Oct','Nov','Dic'];

const EMOJI_POR_CATEGORIA = {
    'festival': '🎉', 'arte': '🎭', 'música': '🎵', 'musica': '🎵', 'artesanías': '🏺', 'artesanias': '🏺'
};
const COLOR_POR_CATEGORIA = {
    'festival': '#2a0a0a', 'arte': '#1a0a2a', 'música': '#0a1a0a', 'musica': '#0a1a0a', 'artesanías': '#1a1400', 'artesanias': '#1a1400'
};
function derivarEmojiCategoria(categoria) {
    return EMOJI_POR_CATEGORIA[(categoria || '').toLowerCase()] || '📅';
}
function derivarColorCategoria(categoria) {
    return COLOR_POR_CATEGORIA[(categoria || '').toLowerCase()] || '#141414';
}

const EMOJI_POR_ERA = { 'fundación': '⛪', 'fundacion': '⛪', 'geografía': '🌳', 'geografia': '🌳', 'cultura viva': '🎨' };
function derivarEmojiEra(era) {
    const clave = Object.keys(EMOJI_POR_ERA).find(k => (era || '').toLowerCase().includes(k));
    return clave ? EMOJI_POR_ERA[clave] : '🏛️';
}
const COLORES_ERA = ['#1a0808', '#0e0e1a', '#0a1a0a', '#1a1408'];
function derivarColorEra(indice) {
    return COLORES_ERA[indice % COLORES_ERA.length];
}

/** Carga eventos, fechas especiales e historia desde la API real */
async function cargarDatosCulturales() {
    const [resEventos, resFechas, resHistoria] = await Promise.all([
        fetch('/api/events'),
        fetch('/api/special-dates'),
        fetch('/api/history-entries')
    ]);

    if (resEventos.ok) {
        const data = await resEventos.json();
        eventos = data.map(e => {
            const fecha = new Date(e.fechaEvento);
            return {
                featured: !!e.featured,
                emoji: derivarEmojiCategoria(e.categoria),
                dia: String(fecha.getDate()).padStart(2, '0'),
                mes: MESES_ABBR[fecha.getMonth()],
                titulo: e.nombreEvento,
                categoria: e.categoria || 'Evento',
                color: derivarColorCategoria(e.categoria),
                desc: e.descripcionLarga ? e.descripcionLarga.slice(0, 160) : '',
                lugar: e.lugar,
                hora: fecha.toLocaleTimeString('es-CO', { hour: '2-digit', minute: '2-digit' }),
                larga: e.descripcionLarga
            };
        });
    }

    if (resFechas.ok) {
        const data = await resFechas.json();
        fechasEspeciales = data.map(f => ({
            dia: String(f.dia).padStart(2, '0'),
            mes: MESES_ABBR[f.mes - 1],
            nombre: f.nombre,
            desc: f.descripcion,
            icon: '📌'
        }));
    }

    if (resHistoria.ok) {
        const data = await resHistoria.json();
        historia = data.map((h, i) => ({
            main: !!h.main,
            emoji: derivarEmojiEra(h.era),
            color: derivarColorEra(i),
            era: h.era,
            titulo: h.titulo,
            numero: h.numero,
            texto: h.texto
        }));
    }
}

/* ════════════════════════════════════════
   BREADCRUMB — navegación entre apartados
════════════════════════════════════════ */
const nombreFiltro = {
    todos:    'Galería Cultural',
    eventos:  'Eventos',
    fechas:   'Fechas Especiales',
    historia: 'Historia'
};

let historialFiltro = [{ key: 'todos', label: 'Inicio' }];

function renderBreadcrumb() {
    const bc = document.getElementById('breadcrumb');
    if (!bc) return;

    if (historialFiltro.length <= 1) { bc.innerHTML = ''; return; }

    bc.innerHTML = historialFiltro.map((p, i) => {
        const esUltimo = i === historialFiltro.length - 1;
        return `${i > 0 ? '<i class="fas fa-chevron-right"></i>' : ''}
      <div class="crumb ${esUltimo ? 'actual' : ''}" ${esUltimo ? '' : `onclick="volverABreadcrumb(${i})"`}>${p.label}</div>`;
    }).join('');
}

function volverABreadcrumb(index) {
    const paso = historialFiltro[index];
    aplicarFiltro(paso.key);
}

/* ════════════════════════════════════════
   FILTROS — muestra/oculta secciones
════════════════════════════════════════ */
function filtrar(tipo, btnElement) {
    aplicarFiltro(tipo);
}

function aplicarFiltro(tipo) {
    // Actualizar estado visual de los botones de filtro
    document.querySelectorAll('.filtro-btn').forEach(b => {
        b.classList.toggle('activo', b.dataset.filtro === tipo);
    });

    // Actualizar breadcrumb
    if (tipo === 'todos') {
        historialFiltro = [{ key: 'todos', label: 'Inicio' }];
    } else {
        historialFiltro = [
            { key: 'todos', label: 'Inicio' },
            { key: tipo, label: nombreFiltro[tipo] }
        ];
    }
    renderBreadcrumb();

    const secEventos  = document.getElementById('sec-eventos');
    const secFechas   = document.getElementById('sec-fechas');
    const secHistoria = document.getElementById('sec-historia');
    const secCifras   = document.getElementById('sec-cifras');
    const show = (el, v) => { if (el) el.style.display = v ? '' : 'none'; };

    if (tipo === 'todos')    { show(secEventos,true); show(secFechas,true); show(secHistoria,true); show(secCifras,true); }
    if (tipo === 'eventos')  { show(secEventos,true); show(secFechas,false); show(secHistoria,false); show(secCifras,false); }
    if (tipo === 'fechas')   { show(secEventos,false); show(secFechas,true); show(secHistoria,false); show(secCifras,false); }
    if (tipo === 'historia') { show(secEventos,false); show(secFechas,false); show(secHistoria,true); show(secCifras,true); }

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

/* ════════════════════════════════════════
   RENDER EVENTOS
════════════════════════════════════════ */
function renderEventos() {
    const grid = document.getElementById('eventos-grid');
    if (!grid) return;
    grid.innerHTML = eventos.map((ev, i) => `
    <div class="evento-card ${ev.featured ? 'evento-featured' : ''}" onclick="abrirModal('ev',${i})">
      <div class="evento-img-wrap">
        <div class="ev-bg" style="background:${ev.color};">${ev.emoji}</div>
        <div class="ev-overlay"></div>
        <div class="ev-date-badge"><div class="day">${ev.dia}</div><div class="month">${ev.mes}</div></div>
        <div class="ev-category">${ev.categoria}</div>
      </div>
      <div class="evento-body">
        <div class="evento-title">${ev.titulo}</div>
        <div class="evento-desc">${ev.desc}</div>
        <div class="evento-meta">
          <div class="ev-meta-item"><i class="fas fa-map-marker-alt"></i> ${ev.lugar}</div>
          ${ev.hora ? `<div class="ev-meta-item"><i class="fas fa-clock"></i> ${ev.hora}</div>` : ''}
        </div>
      </div>
    </div>
  `).join('');
}

/* ════════════════════════════════════════
   RENDER FECHAS ESPECIALES
════════════════════════════════════════ */
function renderFechas() {
    const grid = document.getElementById('fechas-grid');
    if (!grid) return;
    grid.innerHTML = fechasEspeciales.map((f, i) => `
    <div class="timeline-card" onclick="abrirModal('fecha',${i})">
      <div class="tl-icon">${f.icon}</div>
      <div class="tl-fecha">${f.dia}</div>
      <div class="tl-mes">${f.mes}</div>
      <div class="tl-nombre">${f.nombre}</div>
      <div class="tl-desc">${f.desc}</div>
    </div>
  `).join('');
}

/* ════════════════════════════════════════
   HISTORIA — RENDER CON SCROLL SNAP & EXPANSIÓN (Paso 5)
════════════════════════════════════════ */
function toggleTarjetaExpandida(btn) {
    const card = btn.closest('.historia-card') || btn.closest('.evento-card') || btn.closest('.card-expandible');
    if (!card) return;
    const esExpandida = card.classList.toggle('expandida');
    btn.innerHTML = esExpandida 
        ? 'Leer menos <i class="fas fa-chevron-up"></i>' 
        : 'Leer más <i class="fas fa-arrow-right"></i>';
}

function renderHistoria() {
    const grid = document.getElementById('historia-grid');
    if (!grid) return;
    grid.innerHTML = historia.map((h, i) => `
    <div class="historia-card ${h.main ? 'historia-main' : ''}">
      <div class="hist-banner" style="background:${h.color};">
        <div class="card-gallery-track">
          <div class="card-gallery-item"><span>${h.emoji}</span></div>
          <div class="card-gallery-item"><span>🏛️</span></div>
          <div class="card-gallery-item"><span>📜</span></div>
        </div>
        <div class="hist-number">${h.numero}</div>
      </div>
      <div class="hist-body">
        <div class="hist-era">${h.era}</div>
        <div class="hist-title">${h.titulo}</div>
        <div class="hist-text">${h.texto || ''}</div>
        <button class="btn-ver-mas-hist" onclick="event.stopPropagation(); toggleTarjetaExpandida(this)">
          Leer más <i class="fas fa-arrow-right"></i>
        </button>
      </div>
    </div>
  `).join('');
}

/* ════════════════════════════════════════
   MODAL DE DETALLE
════════════════════════════════════════ */
function abrirModal(tipo, index) {
    let titulo, cat, emoji, desc, lugar, hora;

    if (tipo === 'ev') {
        const ev = eventos[index];
        if (!ev) return;
        titulo = ev.titulo; cat = ev.categoria; emoji = ev.emoji;
        desc = ev.larga || ev.desc; lugar = ev.lugar; hora = ev.hora || '';
    } else if (tipo === 'fecha') {
        const f = fechasEspeciales[index];
        if (!f) return;
        titulo = f.nombre; cat = 'Fecha Especial'; emoji = f.icon; desc = f.desc; lugar = ''; hora = '';
    } else if (tipo === 'hist') {
        const h = historia[index];
        if (!h) return;
        titulo = h.titulo; cat = h.era; emoji = h.emoji; desc = h.texto; lugar = ''; hora = '';
    } else {
        return;
    }

    document.getElementById('m-em').textContent = emoji;
    document.getElementById('m-cat').textContent = cat;
    document.getElementById('m-titulo').textContent = titulo;
    document.getElementById('m-desc').textContent = desc;
    let meta = '';
    if (lugar) meta += `<div class="modal-meta-item"><i class="fas fa-map-marker-alt"></i> ${lugar}</div>`;
    if (hora)  meta += `<div class="modal-meta-item"><i class="fas fa-clock"></i> ${hora}</div>`;
    document.getElementById('m-meta').innerHTML = meta;
    document.getElementById('modal').classList.add('open');
}
function cerrarModal() { document.getElementById('modal').classList.remove('open'); }

/* ════════════════════════════════════════
   INIT
════════════════════════════════════════ */
cargarDatosCulturales()
    .then(() => {
        renderEventos();
        renderFechas();
        renderHistoria();
    })
    .catch(e => {
        console.error('Error cargando datos culturales:', e);
        ['eventos-grid', 'fechas-grid', 'historia-grid'].forEach(id => {
            const grid = document.getElementById(id);
            if (grid) grid.innerHTML = '<p class="sin-resultados">No se pudo cargar la información.</p>';
        });
    });
renderBreadcrumb();