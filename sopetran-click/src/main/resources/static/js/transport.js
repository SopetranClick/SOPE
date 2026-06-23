
    const rutasPorEmpresa = {
    sotrauraba: [
{ id: 1, origen: 'Sopetrán', destino: 'San Jerónimo', horarios: ['07:00', '11:00', '14:30'], precio: '$5,000', duracion: '25 min', asientos: 16 },
{ id: 2, origen: 'Sopetrán', destino: 'Vereda El Guayabo', horarios: ['08:00', '13:00', '17:00'], precio: '$3,500', duracion: '20 min', asientos: 14 },
    ],
    rapidoochoa: [
{ id: 3, origen: 'Sopetrán', destino: 'Medellín', horarios: ['05:00', '07:30', '10:00', '13:00', '16:00'], precio: '$18,000', duracion: '1h 45min', asientos: 32 },
{ id: 4, origen: 'Sopetrán', destino: 'Santa Fe de Antioquia', horarios: ['06:00', '09:00', '12:00', '15:00'], precio: '$8,500', duracion: '40 min', asientos: 20 },
    ]
};

    const empresaInfo = {
    sotrauraba:   { nombre: 'Sotrauraba',    tag: 'Transporte · Sotrauraba' },
    rapidoochoa:  { nombre: 'Rápido Ochoa',  tag: 'Transporte · Rápido Ochoa' }
};

    const driversPorTipo = {
    moto: [
{ nombre: 'Carlos Muñoz', placa: 'ABC-123', marca: 'Honda CB190', año: 2022, tel: '3001234567', disponible: true },
{ nombre: 'Andrés López', placa: 'XYZ-456', marca: 'Yamaha FZ2.0', año: 2021, tel: '3109876543', disponible: true },
{ nombre: 'Diego Ríos', placa: 'KLM-789', marca: 'Suzuki GS150', año: 2023, tel: '3204561234', disponible: false },
    ],
    carro: [
{ nombre: 'Jhon Taborda', placa: 'PAL-345', marca: 'Renault Logan', año: 2020, tel: '3151112233', disponible: true },
{ nombre: 'Martha Giraldo', placa: 'SOB-112', marca: 'Chevrolet Sail', año: 2021, tel: '3006667788', disponible: true },
{ nombre: 'Luis Bermúdez', placa: 'ANT-990', marca: 'Toyota Hilux', año: 2019, tel: '3145556677', disponible: false },
    ]
};

    const tipoDomicilioInfo = {
    moto:  { nombre: 'Moto Taxi', tag: 'Domicilio · Moto Taxi', icon: 'fa-motorcycle' },
    carro: { nombre: 'Carro',     tag: 'Domicilio · Carro',     icon: 'fa-car' }
};

    let asientosSeleccionados = {};
    let historialPasos = [{ key: 'inicio', label: 'Inicio' }];

    const stepsMap = {
    'inicio':           'step-1',
    'transporte':       'step-2-transporte',
    'domicilio':        'step-2-domicilio',
    'bus-sotrauraba':   'step-bus',
    'bus-rapidoochoa':  'step-bus',
    'moto':             'step-driver',
    'carro':            'step-driver',
};

    function irA(destino) {
    if (destino === 'transporte') {
    historialPasos = [{key:'inicio',label:'Inicio'}, {key:'transporte',label:'Transporte'}];
} else if (destino === 'domicilio') {
    historialPasos = [{key:'inicio',label:'Inicio'}, {key:'domicilio',label:'Domicilio'}];
} else if (destino === 'bus-sotrauraba' || destino === 'bus-rapidoochoa') {
    const empresaKey = destino === 'bus-sotrauraba' ? 'sotrauraba' : 'rapidoochoa';
    historialPasos = [
{key:'inicio',label:'Inicio'},
{key:'transporte',label:'Transporte'},
{key:destino,label:empresaInfo[empresaKey].nombre}
    ];
    renderBuses(empresaKey);
} else if (destino === 'moto' || destino === 'carro') {
    historialPasos = [
{key:'inicio',label:'Inicio'},
{key:'domicilio',label:'Domicilio'},
{key:destino,label:tipoDomicilioInfo[destino].nombre}
    ];
    renderDrivers(destino);
}

    mostrarStep(stepsMap[destino]);
    renderBreadcrumb();
    window.scrollTo({top:0, behavior:'smooth'});
}

    function mostrarStep(stepId) {
    document.querySelectorAll('.step').forEach(s => s.classList.remove('activo'));
    document.getElementById(stepId).classList.add('activo');
}

    function renderBreadcrumb() {
    const bc = document.getElementById('breadcrumb');
    if (historialPasos.length <= 1) { bc.innerHTML = ''; return; }
    bc.innerHTML = historialPasos.map((p, i) => {
    const esUltimo = i === historialPasos.length - 1;
    return `${i > 0 ? '<i class="fas fa-chevron-right"></i>' : ''}
      <div class="crumb ${esUltimo ? 'actual':''}" onclick="${esUltimo ? '' : `volverA(${i})`}">${p.label}</div>`;
}).join('');
}

    function volverA(index) {
    const paso = historialPasos[index];
    historialPasos = historialPasos.slice(0, index + 1);
    mostrarStep(stepsMap[paso.key] || 'step-1');
    renderBreadcrumb();
    window.scrollTo({top:0, behavior:'smooth'});
}

    function renderBuses(empresaKey) {
    document.getElementById('bus-empresa-tag').textContent = empresaInfo[empresaKey].tag;
    document.getElementById('bus-empresa-titulo').innerHTML = `Rutas de <span style="color:var(--rojo-vivo);font-style:italic;">${empresaInfo[empresaKey].nombre}</span>`;

    const rutas = rutasPorEmpresa[empresaKey];
    const grid = document.getElementById('bus-grid');

    grid.innerHTML = rutas.map(r => {
    const ocupados = Array.from({length: r.asientos}, () => Math.random() < .35);
    return `
    <div class="bus-card">
      <div class="bus-route-top">
        <div class="bus-icon"><i class="fas fa-bus"></i></div>
        <span class="bus-badge">${empresaInfo[empresaKey].nombre}</span>
      </div>
      <div class="bus-ruta">${r.origen} → ${r.destino}</div>
      <div class="bus-desc">Servicio directo • Salidas diarias</div>
      <div class="bus-info-row">
        <div class="bus-chip"><i class="fas fa-clock"></i> ${r.duracion}</div>
        <div class="bus-chip"><i class="fas fa-tag"></i> ${r.precio}</div>
        <div class="bus-chip"><i class="fas fa-chair"></i> ${r.asientos} puestos</div>
      </div>
      <div style="font-size:.75rem;color:var(--gris);margin-bottom:8px;letter-spacing:.05em;">Próximos horarios:</div>
      <div style="display:flex;gap:7px;flex-wrap:wrap;margin-bottom:18px;">
        ${r.horarios.map(h => `<span style="background:var(--glass);border:1px solid rgba(255,255,255,.08);padding:4px 10px;border-radius:6px;font-size:.75rem;color:#ccc;">${h}</span>`).join('')}
      </div>
      <div style="font-size:.75rem;color:var(--gris);margin-bottom:8px;letter-spacing:.05em;">Selecciona tu puesto:</div>
      <div class="bus-seats" id="seats-${r.id}">
        ${ocupados.map((oc,i) => `<div class="seat ${oc ? 'ocupado' : 'libre'}" data-ruta="${r.id}" data-num="${i+1}" onclick="toggleSeat(this,${r.id},${i+1})"></div>`).join('')}
      </div>
      <div class="seat-legend">
        <div><span class="legend-dot" style="background:rgba(40,140,40,.45);border:1px solid rgba(40,180,40,.5);"></span>Libre</div>
        <div><span class="legend-dot" style="background:rgba(139,0,0,.45);border:1px solid #8b0000;"></span>Ocupado</div>
        <div><span class="legend-dot" style="background:var(--rojo-vivo);border:1px solid #ff4444;"></span>Tuyo</div>
      </div>
      <button class="btn-comprar" onclick="abrirModal(${r.id},'${r.origen} → ${r.destino}','${r.precio}')">
        <i class="fas fa-ticket-alt"></i>&nbsp; Comprar tiquete
      </button>
    </div>`;
}).join('');

    asientosSeleccionados = {};
    rutas.forEach(r => asientosSeleccionados[r.id] = new Set());
}

    function toggleSeat(el, rutaId, num) {
    if (el.classList.contains('ocupado')) return;
    if (el.classList.contains('seleccionado')) {
    el.classList.remove('seleccionado'); el.classList.add('libre');
    asientosSeleccionados[rutaId].delete(num);
} else {
    el.classList.remove('libre'); el.classList.add('seleccionado');
    asientosSeleccionados[rutaId].add(num);
}
}

    let modalRutaId = null;
    function abrirModal(id, nombre, precio) {
    modalRutaId = id;
    document.getElementById('modal-titulo').textContent = nombre;
    document.getElementById('modal-sub').textContent = `Precio por puesto: ${precio}`;
    const sel = asientosSeleccionados[id];
    document.getElementById('modal-asientos').textContent = sel && sel.size > 0 ? [...sel].join(', ') : 'Ninguno (se asignará automáticamente)';
    document.getElementById('modal-tiquete').classList.add('open');
}
    function cerrarModal() { document.getElementById('modal-tiquete').classList.remove('open'); }
    function confirmarCompra() {
    const n = document.getElementById('input-nombre').value.trim();
    if (!n) { alert('Por favor ingresa tu nombre.'); return; }
    cerrarModal();
    setTimeout(() => alert(`✅ ¡Reserva confirmada para ${n}! Pronto recibirás los detalles.`), 200);
}

    function renderDrivers(tipo) {
    document.getElementById('driver-tag').textContent = tipoDomicilioInfo[tipo].tag;
    document.getElementById('driver-titulo').innerHTML = `Disponibles de <span style="color:var(--rojo-vivo);font-style:italic;">${tipoDomicilioInfo[tipo].nombre}</span>`;

    const drivers = driversPorTipo[tipo];
    const icon = tipoDomicilioInfo[tipo].icon;

    document.getElementById('driver-grid').innerHTML = drivers.map(d => `
    <div class="driver-card">
      <div class="driver-photo">
        <i class="fas fa-user-circle"></i>
        <div class="driver-status ${d.disponible ? '' : 'ocupado'}">
          <span class="dot"></span> ${d.disponible ? 'Disponible' : 'Ocupado'}
        </div>
      </div>
      <div class="driver-body">
        <div class="driver-name">${d.nombre}</div>
        <div class="driver-rating">
          <i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star-half-alt"></i>
          <span>4.7 (23)</span>
        </div>
        <div class="driver-vehicle">
          <div class="v-chip"><i class="fas ${icon}"></i> ${d.marca}</div>
          <div class="v-chip"><i class="fas fa-id-card"></i> ${d.placa}</div>
          <div class="v-chip"><i class="fas fa-calendar"></i> ${d.año}</div>
        </div>
        <div class="driver-actions">
          <button class="btn-llamar" onclick="window.location='tel:${d.tel}'"><i class="fas fa-phone"></i> Llamar</button>
          <button class="btn-whatsapp" onclick="window.open('https://wa.me/57${d.tel}','_blank')"><i class="fab fa-whatsapp"></i> WhatsApp</button>
        </div>
      </div>
    </div>
  `).join('');
}

    renderBreadcrumb();
