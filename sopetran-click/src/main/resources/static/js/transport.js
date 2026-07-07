
    // Datos de rutas/empresas y conductores — cargados desde la API real
    let rutasPorEmpresa = {};
    let empresaInfo = {};
    let driversPorTipo = { moto: [], carro: [] };

    const tipoDomicilioInfo = {
    moto:  { nombre: 'Moto Taxi', tag: 'Domicilio · Moto Taxi', icon: 'fa-motorcycle' },
    carro: { nombre: 'Carro',     tag: 'Domicilio · Carro',     icon: 'fa-car' }
};

    function slugify(texto) {
        return (texto || '')
            .toLowerCase()
            .normalize('NFD')
            .replace(/[^a-z0-9]+/g, '');
    }

    /** Carga las rutas de bus desde la API y las agrupa por empresa */
    async function cargarBuses() {
        const res = await fetch('/api/buses');
        if (!res.ok) throw new Error('HTTP ' + res.status);
        const buses = await res.json();

        rutasPorEmpresa = {};
        empresaInfo = {};

        buses.forEach(b => {
            const key = slugify(b.empresa);
            if (!rutasPorEmpresa[key]) {
                rutasPorEmpresa[key] = [];
                empresaInfo[key] = { nombre: b.empresa, tag: `Transporte · ${b.empresa}` };
            }
            rutasPorEmpresa[key].push({
                id: b.idBus,
                origen: b.origen || b.ruta,
                destino: b.destino || '',
                horarios: (b.horarios || '').split(',').map(h => h.trim()).filter(Boolean),
                precio: b.precio != null ? formatCOP(b.precio) : '—',
                duracion: b.duracion || '—',
                asientos: b.asientos || 0
            });
        });

        renderEmpresas();
    }

    /** Carga los conductores de moto y carro desde la API */
    async function cargarDrivers() {
        const [resMoto, resCarro] = await Promise.all([
            fetch('/api/drivers/tipo/MOTO'),
            fetch('/api/drivers/tipo/CARRO')
        ]);
        driversPorTipo.moto  = resMoto.ok  ? (await resMoto.json()).map(mapDriver)  : [];
        driversPorTipo.carro = resCarro.ok ? (await resCarro.json()).map(mapDriver) : [];
    }

    function mapDriver(d) {
        return {
            nombre: d.nombre,
            placa: d.placa,
            marca: d.marca,
            año: d.anio,
            tel: d.telefono,
            disponible: d.disponible !== false
        };
    }

    function formatCOP(precio) {
        return new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', maximumFractionDigits: 0 }).format(precio);
    }

    /** Genera dinámicamente las tarjetas de selección de empresa */
    function renderEmpresas() {
        const grid = document.getElementById('empresa-grid');
        if (!grid) return;
        const empresas = Object.keys(empresaInfo);
        if (empresas.length === 0) {
            grid.innerHTML = '<p class="sin-resultados">No hay empresas de transporte disponibles.</p>';
            return;
        }
        grid.innerHTML = empresas.map(key => {
            const info = empresaInfo[key];
            const numRutas = rutasPorEmpresa[key].length;
            return `
                <div class="sub-choice-card" onclick="irA('bus-${key}')">
                    <div class="sub-choice-logo"><i class="fas fa-bus"></i></div>
                    <div class="sub-choice-title">${info.nombre}</div>
                    <div class="sub-choice-desc">Consulta las rutas y horarios disponibles.</div>
                    <div class="sub-choice-meta">
                        <span class="sc-chip"><i class="fas fa-route"></i> ${numRutas} ruta${numRutas !== 1 ? 's' : ''}</span>
                    </div>
                </div>`;
        }).join('');
    }

    let asientosSeleccionados = {};
    let historialPasos = [{ key: 'inicio', label: 'Inicio' }];

    const stepsMap = {
    'inicio':           'step-1',
    'transporte':       'step-2-transporte',
    'domicilio':        'step-2-domicilio',
    'moto':             'step-driver',
    'carro':            'step-driver',
};

    function irA(destino) {
    if (destino === 'transporte') {
    historialPasos = [{key:'inicio',label:'Inicio'}, {key:'transporte',label:'Transporte'}];
} else if (destino === 'domicilio') {
    historialPasos = [{key:'inicio',label:'Inicio'}, {key:'domicilio',label:'Domicilio'}];
} else if (destino.startsWith('bus-')) {
    const empresaKey = destino.substring(4);
    historialPasos = [
{key:'inicio',label:'Inicio'},
{key:'transporte',label:'Transporte'},
{key:destino,label:empresaInfo[empresaKey] ? empresaInfo[empresaKey].nombre : ''}
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

    mostrarStep(destino.startsWith('bus-') ? 'step-bus' : stepsMap[destino]);
    renderBreadcrumb();
    window.scrollTo({top:0, behavior:'smooth'});
}

    function mostrarStep(stepId) {
    document.querySelectorAll('.step').forEach(s => s.classList.remove('activo'));
    document.getElementById(stepId).classList.add('activo');
}

    function renderBreadcrumb() {
    const bc = document.getElementById('breadcrumb-transporte');
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

    /** Retrocede UN paso dentro del módulo de transporte (sin salir al index).
     *  Lo usan los botones "Volver" de cada pantalla: desde las rutas de una
     *  empresa vuelve a la lista de empresas, y desde moto/carro vuelve a la
     *  elección moto/carro, etc. Si ya estamos en el primer paso, va a inicio. */
    function volverAtras() {
    if (historialPasos.length <= 1) {
    navegarA('inicio');
    return;
}
    volverA(historialPasos.length - 2);
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

    cargarBuses().catch(e => {
        console.error('Error cargando buses:', e);
        const grid = document.getElementById('empresa-grid');
        if (grid) grid.innerHTML = '<p class="sin-resultados">No se pudieron cargar las empresas de transporte.</p>';
    });
    cargarDrivers().catch(e => console.error('Error cargando conductores:', e));
    renderBreadcrumb();
