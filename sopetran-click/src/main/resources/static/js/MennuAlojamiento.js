        // Simulamos una lista larga de alojamientos (puedes añadir los 30 aquí)
        const catalog = Array.from({length: 15}, (_, i) => ({
            id: i,
            name: `Alojamiento Premium ${i + 1}`,
            desc: `Descripción detallada para el alojamiento número ${i + 1}. Un lugar paradisíaco en Sopetrán diseñado para el máximo confort y descanso familiar.`,
            loc: `Vereda Sector ${i + 1}, Sopetrán`,
            phone: `+57 300 000 ${1000 + i}`,
            cover: `https://picsum.photos/id/${10 + i}/1470/800`,
            gallery: [
                `https://picsum.photos/id/${10 + i}/1470/800`,
                `https://picsum.photos/id/${20 + i}/600/400`,
                `https://picsum.photos/id/${30 + i}/600/400`,
                `https://picsum.photos/id/${40 + i}/600/400`,
                `https://picsum.photos/id/${50 + i}/600/400`
            ]
        }));

        let currentHotelIdx = 0;
        let currentPhotoIdx = 0;

        const mainBg = document.getElementById('mainBg');
        const textPanel = document.getElementById('textPanel');
        const gridGallery = document.getElementById('gridGallery');
        const bottomNav = document.getElementById('bottomNav');
        const hotelStackContainer = document.getElementById('accommodationStack');

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
            const hotel = catalog[currentHotelIdx];

            // Fondo
            mainBg.style.backgroundImage = `url(${hotel.cover})`;

            // Textos
            document.getElementById('nodeTitle').innerText = hotel.name;
            document.getElementById('nodeDesc').innerText = hotel.desc;
            document.getElementById('nodeLoc').innerText = hotel.loc;
            document.getElementById('nodePhone').innerText = hotel.phone;

            // Render Galería Grid
            const gridItems = document.getElementById('galleryGridItems');
            gridItems.innerHTML = '';
            hotel.gallery.slice(1, 5).forEach((img, i) => {
                const item = document.createElement('div');
                item.className = 'gallery-item';
                item.style.backgroundImage = `url(${img})`;
                item.onclick = () => {
                    mainBg.style.backgroundImage = `url(${img})`;
                    currentPhotoIdx = i + 1;
                };
                gridItems.appendChild(item);
            });

            // Actualizar Stack Visualmente
            updateStackVisuals();
        }

        function renderInitialStack() {
            hotelStackContainer.innerHTML = '';
            catalog.forEach((hotel, i) => {
                const card = document.createElement('div');
                card.className = `stack-card`;
                card.style.backgroundImage = `url(${hotel.cover})`;
                card.onclick = () => renderHotel(i);
                hotelStackContainer.appendChild(card);
            });
            updateStackVisuals();
        }

        function updateStackVisuals() {
            const cards = document.querySelectorAll('.stack-card');
            cards.forEach((card, i) => {
                card.classList.toggle('active', i === currentHotelIdx);
            });

            // Lógica de Desplazamiento (Carousel Infinito de 5)
            // Calculamos cuánto mover el contenedor para que el activo esté centrado
            // o se vea el grupo de 5.
            const cardWidth = 115; // 100px + 15px gap
            let offset = 0;

            if (currentHotelIdx >= 2 && currentHotelIdx < catalog.length - 2) {
                offset = (currentHotelIdx - 2) * cardWidth;
            } else if (currentHotelIdx >= catalog.length - 2) {
                offset = (catalog.length - 5) * cardWidth;
            }
            
            // Si hay menos de 5 hoteles, no se mueve
            if(catalog.length <= 5) offset = 0;

            hotelStackContainer.style.transform = `translateX(-${offset}px)`;
        }

        function moveGallery(dir) {
            const currentGallery = catalog[currentHotelIdx].gallery;
            currentPhotoIdx += dir;
            
            if (currentPhotoIdx >= currentGallery.length) currentPhotoIdx = 0;
            if (currentPhotoIdx < 0) currentPhotoIdx = currentGallery.length - 1;
            
            mainBg.style.backgroundImage = `url(${currentGallery[currentPhotoIdx]})`;
        }

        window.onload = () => {
            renderInitialStack();
            renderHotel(0);
        };