/* ════════════════════════════════════════
   DATOS
════════════════════════════════════════ */
const eventos = [
    {
        featured: true, emoji: '🎉', dia: '28', mes: 'Jun',
        titulo: 'Feria de las Flores Sopetrán 2025',
        categoria: 'Festival', color: '#2a0a0a',
        desc: 'La fiesta más colorida del occidente antioqueño. Desfiles, música, gastronomía típica y la tradicional elección de la Reina de las Flores. Tres días de alegría para toda la familia.',
        lugar: 'Parque Principal', hora: '10:00 AM - 11:00 PM',
        larga: 'Una de las festividades más esperadas del año en Sopetrán. Las calles se visten de color con arreglos florales elaborados por artesanos locales. Incluye exposición de carros antiguos, conciertos de música popular y la famosa noche de la antorcha que recorre el municipio.'
    },
    {
        emoji: '🎭', dia: '15', mes: 'Jul',
        titulo: 'Festival de Teatro Popular',
        categoria: 'Arte', color: '#1a0a2a',
        desc: 'Grupos teatrales de Antioquia se reúnen para llenar las plazas de Sopetrán con obras que celebran la cultura campesina.',
        lugar: 'Parque Central', hora: '5:00 PM'
    },
    {
        emoji: '🎵', dia: '05', mes: 'Ago',
        titulo: 'Noche de Serenatas',
        categoria: 'Música', color: '#0a1a0a',
        desc: 'Una noche mágica donde los trovadores locales rinden homenaje a la música antioqueña bajo las estrellas del occidente.',
        lugar: 'Plaza de Bolívar', hora: '7:00 PM'
    },
    {
        emoji: '🏺', dia: '20', mes: 'Sep',
        titulo: 'Expo Artesanías del Occidente',
        categoria: 'Artesanías', color: '#1a1400',
        desc: 'Artesanos de toda la región exponen y venden sus creaciones. Talleres abiertos de cerámica, tejido y madera.',
        lugar: 'Casa de la Cultura', hora: '9:00 AM - 6:00 PM'
    },
];

const fechasEspeciales = [
    { dia: '25', mes: 'Dic', nombre: 'Novenas de Navidad', desc: 'Celebración tradicional con pesebres artesanales que adornan cada barrio del municipio.', icon: '⛪' },
    { dia: '02', mes: 'Feb', nombre: 'Día de la Candelaria', desc: 'Patrona del municipio. Misa solemne, procesión y celebraciones populares en el parque principal.', icon: '🕯️' },
    { dia: '19', mes: 'Mar', nombre: 'Día de San José', desc: 'Honor al santo patrono de los trabajadores. Actividades familiares y culturales durante todo el día.', icon: '🌿' },
    { dia: '01', mes: 'Nov', nombre: 'Día de los Difuntos', desc: 'Tradición de visita al cementerio con flores y música. Una de las fechas más sentidas del año.', icon: '🌸' },
    { dia: '16', mes: 'Jul', nombre: 'Día de la Virgen del Carmen', desc: 'Celebración del transporte. Bendición de vehículos y procesión por las calles principales.', icon: '🚗' },
    { dia: '07', mes: 'Ago', nombre: 'Batalla de Boyacá', desc: 'Actos cívicos en instituciones educativas. Izadas de bandera y presentaciones artísticas.', icon: '🇨🇴' },
];

const historia = [
    {
        main: true, emoji: '⛪', color: '#1a0808', era: 'Fundación • Siglo XVI',
        titulo: 'Los Orígenes de Sopetrán',
        numero: '01',
        texto: 'Sopetrán fue fundado en el año 1541 por el conquistador español Jerónimo Luis Tejelo bajo el nombre de "Pueblo de Sopetrán". Su nombre proviene del latín "Sub Petra" que significa "bajo la piedra", en referencia a los grandes afloramientos rocosos que caracterizan su paisaje. Desde sus inicios, fue un importante cruce de caminos entre el río Cauca y el interior del departamento, convirtiéndose en punto de encuentro de culturas indígenas, españolas y africanas que dieron forma a su identidad única.',
    },
    {
        emoji: '🏛️', color: '#0e0e1a', era: 'Siglo XIX',
        titulo: 'La Basílica de Nuestra Señora',
        numero: '02',
        texto: 'La imponente Basílica Menor de Nuestra Señora de Chiquinquirá, construida entre 1870 y 1920, es el símbolo más reconocible del municipio. Declarada Patrimonio Arquitectónico, atrae peregrinos de toda Colombia.',
    },
    {
        emoji: '🌳', color: '#0a1a0a', era: 'Geografía',
        titulo: 'Verde y Biodiversidad',
        numero: '03',
        texto: 'Ubicado en el occidente antioqueño a orillas del río Cauca, Sopetrán cuenta con pisos térmicos que van del cálido húmedo hasta el frío, albergando una biodiversidad excepcional y paisajes únicos.',
    },
    {
        emoji: '🎨', color: '#1a1408', era: 'Cultura Viva',
        titulo: 'Tradición y Arte Popular',
        numero: '04',
        texto: 'La música de carrilera, las décimas campesinas y los tejidos artesanales son parte del ADN cultural sopetranero. El municipio ha dado al departamento trovadores, pintores y artesanos reconocidos.',
    },
];

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
    grid.innerHTML = eventos.map(ev => `
    <div class="evento-card ${ev.featured ? 'evento-featured' : ''}" onclick="abrirModal('ev','${ev.titulo}','${ev.categoria}','${ev.emoji}','${ev.larga || ev.desc}','${ev.lugar}','${ev.hora || ''}')">
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
    grid.innerHTML = fechasEspeciales.map(f => `
    <div class="timeline-card" onclick="abrirModal('fecha','${f.nombre}','Fecha Especial','${f.icon}','${f.desc}','','')">
      <div class="tl-icon">${f.icon}</div>
      <div class="tl-fecha">${f.dia}</div>
      <div class="tl-mes">${f.mes}</div>
      <div class="tl-nombre">${f.nombre}</div>
      <div class="tl-desc">${f.desc}</div>
    </div>
  `).join('');
}

/* ════════════════════════════════════════
   RENDER HISTORIA
════════════════════════════════════════ */
function renderHistoria() {
    const grid = document.getElementById('historia-grid');
    if (!grid) return;
    grid.innerHTML = historia.map(h => `
    <div class="historia-card ${h.main ? 'historia-main' : ''}">
      <div class="hist-banner" style="background:${h.color};">
        ${h.emoji}
        <div class="hist-number">${h.numero}</div>
      </div>
      <div class="hist-body">
        <div class="hist-era">${h.era}</div>
        <div class="hist-title">${h.titulo}</div>
        <div class="hist-text">${h.texto.substring(0,180)}${h.texto.length > 180 ? '...' : ''}</div>
        <button class="btn-ver-mas-hist" onclick="event.stopPropagation();abrirModal('hist','${h.titulo}','${h.era}','${h.emoji}','${h.texto}','','')">
          Leer más <i class="fas fa-arrow-right"></i>
        </button>
      </div>
    </div>
  `).join('');
}

/* ════════════════════════════════════════
   MODAL DE DETALLE
════════════════════════════════════════ */
function abrirModal(tipo, titulo, cat, emoji, desc, lugar, hora) {
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
renderEventos();
renderFechas();
renderHistoria();
renderBreadcrumb();