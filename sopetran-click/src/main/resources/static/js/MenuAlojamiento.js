// ============================================
// ALOJAMIENTO — catálogo de hoteles y fincas
// Datos vía API (/api/hotels, /api/estates), igual que
// el resto de módulos (transport.js, MennuComercio.js…).
// ============================================

let catalog = [];
let catalogFiltrado = [];
let currentHotelIdx = 0;
let currentPhotoIdx = 0;

const mainBg              = document.getElementById('mainBg');
const textPanel           = document.getElementById('textPanel');
const gridGallery         = document.getElementById('gridGallery');
const bottomNav           = document.getElementById('bottomNav');
const hotelStackContainer = document.getElementById('accommodationStack');
const pasoCategorias      = document.getElementById('aloj-categorias');
const pasoGaleria         = document.getElementById('aloj-galeria');

/** Carga hoteles y fincas desde la API y arma el catálogo unificado */
async function cargarCatalogoAlojamiento() {
    try {
        const [resHoteles, resFincas] = await Promise.all([
            fetch('/api/hotels'),
            fetch('/api/estates')
        ]);
        const hoteles = resHoteles.ok ? await resHoteles.json() : [];
        const fincas  = resFincas.ok  ? await resFincas.json()  : [];

        catalog = [
            ...hoteles.map(h => ({
                id:      h.idHotel,
                origen:  'hotel',
                name:    h.name,
                desc:    h.description || 'Alojamiento en Sopetrán, Antioquia.',
                loc:     h.address   || 'Sopetrán, Antioquia',
                phone:   h.contact,
                tipo:    h.accommodationType || 'Hotel',
                precio:  h.pricePerNight ? '$' + Number(h.pricePerNight).toLocaleString('es-CO') + ' / noche' : '',
                cover:   h.coverUrl  || '/img/PRINCI/carrusel1.jpg',
                gallery: h.gallery && h.gallery.length > 0
                    ? [h.coverUrl, ...h.gallery]
                    : [h.coverUrl || '/img/PRINCI/carrusel1.jpg']
            })),
            ...fincas.map(f => ({
                id:      f.idEstate,
                origen:  'finca',
                name:    f.name,
                desc:    f.description || 'Finca campestre en Sopetrán.',
                loc:     f.location   || 'Sopetrán, Antioquia',
                phone:   f.contact,
                tipo:    f.farmType   || 'Finca',
                precio:  f.pricePerNight ? '$' + Number(f.pricePerNight).toLocaleString('es-CO') + ' / noche' : '',
                cover:   f.coverUrl  || '/img/PRINCI/IMG-20250830-WA0069.jpg',
                gallery: f.gallery && f.gallery.length > 0
                    ? [f.coverUrl, ...f.gallery]
                    : [f.coverUrl || '/img/PRINCI/IMG-20250830-WA0069.jpg']
            }))
        ];
    } catch (err) {
        console.error('Error cargando catálogo de alojamiento:', err);
        catalog = [];
    }
}

// Se dispara en cuanto carga el script; el usuario tarda varios segundos
// en pasar la intro y elegir categoría, así que normalmente ya está listo.
const catalogoListo = cargarCatalogoAlojamiento();

/** ¿El alojamiento pertenece a la categoría elegida? Clasifica por texto libre
 *  (tipo/farmType no son un enum en la BD), buscando palabras clave. */
function categoriaAlojamientoMatch(item, categoria) {
    const tipo = (item.tipo || '').toLowerCase();
    if (categoria === 'hoteles') return item.origen === 'hotel';
    if (item.origen !== 'finca') return false;
    const esRecreativa = tipo.includes('recreativ');
    const esCasa       = tipo.includes('casa') || tipo.includes('campestre');
    if (categoria === 'recreativas') return esRecreativa;
    if (categoria === 'casas')       return esCasa;
    if (categoria === 'fincas')      return !esRecreativa && !esCasa;
    return true;
}

async function elegirCategoriaAlojamiento(categoria) {
    await catalogoListo;
    catalogFiltrado = catalog.filter(item => categoriaAlojamientoMatch(item, categoria));

    pasoCategorias.classList.remove('activo');
    pasoGaleria.classList.add('activo');

    if (catalogFiltrado.length === 0) {
        mainBg.style.backgroundImage = 'none';
        document.getElementById('nodeTitle').innerText = 'Sin resultados';
        document.getElementById('nodeDesc').innerText  = 'Por ahora no tenemos alojamientos en esta categoría.';
        document.getElementById('nodeLoc').innerText   = '';
        document.getElementById('nodePhone').innerText = '';
        document.getElementById('nodePrice').innerText = '';
        document.getElementById('nodeTipo').innerText  = '';
        document.getElementById('galleryGridItems').innerHTML = '';
        hotelStackContainer.innerHTML = '';
        return;
    }

    renderInitialStack();
    renderHotel(0);
}

function volverCategoriasAlojamiento() {
    pasoGaleria.classList.remove('activo');
    pasoCategorias.classList.add('activo');
    toggleInterface(true);
}

function toggleInterface(isVisible) {
    if (isVisible) {
        textPanel.classList.remove('hidden-ui');
        gridGallery.classList.remove('hidden-ui');
        bottomNav.classList.remove('is-visible');
    } else {
        textPanel.classList.add('hidden-ui');
        gridGallery.classList.add('hidden-ui');
        bottomNav.classList.add('is-visible');
    }
}

function renderHotel(index) {
    currentHotelIdx = index;
    currentPhotoIdx = 0;
    const hotel = catalogFiltrado[currentHotelIdx];
    mainBg.style.backgroundImage = `url(${hotel.cover})`;
    document.getElementById('nodeTitle').innerText = hotel.name;
    document.getElementById('nodeDesc').innerText  = hotel.desc;
    document.getElementById('nodeLoc').innerText   = hotel.loc   || '';
    document.getElementById('nodePhone').innerText = hotel.phone  || '';
    document.getElementById('nodePrice').innerText = hotel.precio || '';
    document.getElementById('nodeTipo').innerText  = hotel.tipo   || '';

    const gridItems = document.getElementById('galleryGridItems');
    gridItems.innerHTML = '';
    hotel.gallery.slice(1, 5).forEach((img, i) => {
        const item = document.createElement('div');
        item.className = 'gallery-item';
        item.style.backgroundImage = `url(${img})`;
        item.onclick = () => { mainBg.style.backgroundImage = `url(${img})`; currentPhotoIdx = i + 1; };
        gridItems.appendChild(item);
    });
    updateStackVisuals();
}

function renderInitialStack() {
    hotelStackContainer.innerHTML = '';
    catalogFiltrado.forEach((hotel, i) => {
        const card = document.createElement('div');
        card.className = 'stack-card';
        card.style.backgroundImage = `url(${hotel.cover})`;
        card.title = hotel.name;
        card.onclick = () => renderHotel(i);
        hotelStackContainer.appendChild(card);
    });
    updateStackVisuals();
}

function updateStackVisuals() {
    document.querySelectorAll('.stack-card').forEach((card, i) => {
        card.classList.toggle('active', i === currentHotelIdx);
    });
    const cardWidth = 115;
    let offset = 0;
    if (currentHotelIdx >= 2 && currentHotelIdx < catalogFiltrado.length - 2) {
        offset = (currentHotelIdx - 2) * cardWidth;
    } else if (currentHotelIdx >= catalogFiltrado.length - 2) {
        offset = Math.max(0, catalogFiltrado.length - 5) * cardWidth;
    }
    if (catalogFiltrado.length <= 5) offset = 0;
    hotelStackContainer.style.transform = `translateX(-${offset}px)`;
}

function moveGallery(dir) {
    const gallery = catalogFiltrado[currentHotelIdx].gallery;
    currentPhotoIdx += dir;
    if (currentPhotoIdx >= gallery.length) currentPhotoIdx = 0;
    if (currentPhotoIdx < 0) currentPhotoIdx = gallery.length - 1;
    mainBg.style.backgroundImage = `url(${gallery[currentPhotoIdx]})`;
}
