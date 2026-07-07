-- ============================================================================
-- SOPETRANCLICK — SEED COMPLEMENTARIO (columnas y tablas nuevas)
-- Completa insert.md con los campos agregados durante la extensión del
-- backend: rating/horario/abierto, tags/tipo/acceso, origen/destino/precio/
-- duracion/asientos, zonaCobertura, categoria/featured/descripcionLarga,
-- imageUrl, y las tablas driver / special_date / history_entry / event_images
-- / iconic_place_images.
--
-- INSTRUCCIONES:
--   1. Ejecutar DESPUÉS de insert.md (asume que hoteles, restaurantes,
--      locales, veredas, lugares, buses, motos, eventos y noticias ya existen
--      con los IDs de ese script).
--   2. Ejecutar sobre el schema sopetranclick.
--   3. Las UPDATE usan el nombre como filtro (no el id) para no depender del
--      orden exacto de inserción.
-- ============================================================================

SET search_path TO sopetranclick;

-- ═══════════════════════════════════════════════════════════════════════════
-- A. COMERCIO — rating / horario / abierto en restaurant y local
-- ═══════════════════════════════════════════════════════════════════════════

UPDATE restaurant SET rating = 4.8, horario = 'Lun–Dom · 7am – 9pm',  abierto = TRUE  WHERE name = 'Restaurante La Tradición';
UPDATE restaurant SET rating = 4.6, horario = 'Mar–Dom · 11am – 8pm', abierto = TRUE  WHERE name = 'Trucha del Río';
UPDATE restaurant SET rating = 4.7, horario = 'Miér–Dom · 12pm – 9pm',abierto = TRUE  WHERE name = 'Asadero El Rincón';

UPDATE local SET rating = 4.3, horario = 'Lun–Sáb · 6am – 8pm',        abierto = TRUE  WHERE name = 'Tienda El Paisa';
UPDATE local SET rating = 4.9, horario = 'Todos los días · 5am – 12pm',abierto = TRUE  WHERE name = 'Panadería La Abuela';
UPDATE local SET rating = 4.5, horario = 'Mar–Dom · 9am – 6pm',        abierto = FALSE WHERE name = 'Peluquería Estilo';
UPDATE local SET rating = 4.4, horario = 'Lun–Sáb · 8am – 7pm',        abierto = TRUE  WHERE name = 'Farmacia San Rafael';
UPDATE local SET rating = 4.1, horario = 'Lun–Sáb · 7am – 5pm',        abierto = FALSE WHERE name = 'Ferretería Central';

-- ═══════════════════════════════════════════════════════════════════════════
-- B. ECOTURISMO — tags en site, tipo/acceso/tags en iconic_place
-- ═══════════════════════════════════════════════════════════════════════════

UPDATE site SET tags = 'Cascadas,Café,Senderismo'   WHERE name = 'Santa Rita';
UPDATE site SET tags = 'Cascadas,Piscinas'          WHERE name = 'Vereda Hidalgo';
UPDATE site SET tags = 'Cascadas,Fauna,Leyendas'    WHERE name = 'El Duende';
UPDATE site SET tags = 'Río,Pesca,Camping'          WHERE name = 'Guayabal';
UPDATE site SET tags = 'Flores,Cultivos,Miradores'  WHERE name = 'Santa Bárbara';
UPDATE site SET tags = 'Café,Artesanías,Historia'   WHERE name = 'La Amorro';

-- Santa Rita
UPDATE iconic_place SET tipo = 'Cascada',        acceso = 'Sendero fácil · 15 min caminando', tags = 'Senderismo,Foto' WHERE name = 'Cascada del Gallinaso';
UPDATE iconic_place SET tipo = 'Mirador',        acceso = 'Sendero moderado · 30 min',         tags = 'Avistamiento,Amanecer' WHERE name = 'Mirador Las Águilas';
UPDATE iconic_place SET tipo = 'Ruta Agrícola',  acceso = 'Recorrido guiado · 1h 30min',        tags = 'Café,Agroturismo' WHERE name = 'Sendero del Café';
UPDATE iconic_place SET tipo = 'Patrimonio',     acceso = 'Acceso vehicular directo',           tags = 'Historia,Foto' WHERE name = 'Capilla Santa Rita';
UPDATE iconic_place SET tipo = 'Hospedaje',      acceso = 'Acceso vehicular directo',           tags = 'Hospedaje,Favorito' WHERE name = 'Posada Santa Rita';

-- Vereda Hidalgo
UPDATE iconic_place SET tipo = 'Cascada',    acceso = 'Sendero moderado · 20 min', tags = 'Cascada,Baño,Foto'   WHERE name = 'Salto de Agua Hidalgo';
UPDATE iconic_place SET tipo = 'Río',        acceso = 'Sendero fácil · 15 min',    tags = 'Senderismo,Baño'     WHERE name = 'Quebrada La Honda';
UPDATE iconic_place SET tipo = 'Naturaleza', acceso = 'Sendero moderado · 45 min', tags = 'Aves,Flora'          WHERE name = 'Reserva Forestal El Bosque';

-- El Duende
UPDATE iconic_place SET tipo = 'Cascada',      acceso = 'Sendero moderado · 25 min', tags = 'Cascada,Leyenda,Foto'  WHERE name = 'Salto El Duende';
UPDATE iconic_place SET tipo = 'Ruta',         acceso = 'Sendero fácil · 40 min',    tags = 'Aves,Senderismo'       WHERE name = 'Sendero de los Colibríes';
UPDATE iconic_place SET tipo = 'Agroturismo',  acceso = 'Acceso vehicular directo',  tags = 'Agro,Cacao'            WHERE name = 'Finca El Paraíso';
UPDATE iconic_place SET tipo = 'Mirador',      acceso = 'Sendero moderado · 35 min', tags = 'Vista,Foto'            WHERE name = 'Mirador Cerro Azul';

-- Guayabal
UPDATE iconic_place SET tipo = 'Playa Fluvial', acceso = 'Acceso vehicular directo',   tags = 'Playa,Baño,Pesca'      WHERE name = 'Playa del Río Cauca';
UPDATE iconic_place SET tipo = 'Puerto',        acceso = 'Sendero fácil · 10 min',     tags = 'Bote,Historia'         WHERE name = 'Puerto El Guayabal';
UPDATE iconic_place SET tipo = 'Camping',       acceso = 'Acceso vehicular directo',   tags = 'Camping,Estrellas'     WHERE name = 'Zona de Camping La Ceiba';

-- Santa Bárbara
UPDATE iconic_place SET tipo = 'Agroturismo', acceso = 'Recorrido guiado · 1h',      tags = 'Flores,Orquídeas' WHERE name = 'Finca Floristería Andina';
UPDATE iconic_place SET tipo = 'Mirador',     acceso = 'Sendero moderado · 30 min',  tags = 'Vista,Foto'       WHERE name = 'Mirador El Paraíso';
UPDATE iconic_place SET tipo = 'Laguna',      acceso = 'Sendero fácil · 20 min',     tags = 'Aves,Natura'      WHERE name = 'Laguna del Encanto';

-- La Amorro
UPDATE iconic_place SET tipo = 'Patrimonio',  acceso = 'Acceso vehicular directo',   tags = 'Café,Historia'    WHERE name = 'Beneficiadero Tradicional';
UPDATE iconic_place SET tipo = 'Artesanía',   acceso = 'Acceso vehicular directo',   tags = 'Guadua,Tienda'    WHERE name = 'Taller Artesanías en Guadua';
UPDATE iconic_place SET tipo = 'Senderismo',  acceso = 'Sendero fácil · 45 min',     tags = 'Senderismo,Flora' WHERE name = 'Sendero El Balcón';

-- Galería por lugar icónico (1 foto de portada cada uno, reutilizando temática)
INSERT INTO iconic_place_images (id_iconic_place, url, orden, alt_text)
SELECT id_iconic_place, '/img/PRINCI/carrosaFrutas.PNG', 0, 'Sendero del Café'
FROM iconic_place WHERE name = 'Sendero del Café';
INSERT INTO iconic_place_images (id_iconic_place, url, orden, alt_text)
SELECT id_iconic_place, 'https://images.unsplash.com/photo-1426604966848-d7adac402bff?w=800&q=80', 0, 'Cascada del Gallinaso'
FROM iconic_place WHERE name = 'Cascada del Gallinaso';
INSERT INTO iconic_place_images (id_iconic_place, url, orden, alt_text)
SELECT id_iconic_place, 'https://images.unsplash.com/photo-1511884642898-4c92249e20b6?w=800&q=80', 0, 'Salto de Agua Hidalgo'
FROM iconic_place WHERE name = 'Salto de Agua Hidalgo';
INSERT INTO iconic_place_images (id_iconic_place, url, orden, alt_text)
SELECT id_iconic_place, 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800&q=80', 0, 'Sendero de los Colibríes'
FROM iconic_place WHERE name = 'Sendero de los Colibríes';
INSERT INTO iconic_place_images (id_iconic_place, url, orden, alt_text)
SELECT id_iconic_place, 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&q=80', 0, 'Playa del Río Cauca'
FROM iconic_place WHERE name = 'Playa del Río Cauca';
INSERT INTO iconic_place_images (id_iconic_place, url, orden, alt_text)
SELECT id_iconic_place, 'https://images.unsplash.com/photo-1490750967868-88df5691cc91?w=800&q=80', 0, 'Laguna del Encanto'
FROM iconic_place WHERE name = 'Laguna del Encanto';

-- ═══════════════════════════════════════════════════════════════════════════
-- C. TRANSPORTE — origen/destino/precio/duracion/asientos en buses,
--    corrección de "name" a solo el nombre de empresa (para agrupar rutas),
--    zonaCobertura en motorbike, y la tabla nueva "driver"
-- ═══════════════════════════════════════════════════════════════════════════

-- Corrige el name para que /transporte agrupe por empresa real (2 empresas, no 4 rutas)
UPDATE buses SET
    name = 'Sotrauraba', origen = 'Sopetrán', destino = 'San Jerónimo',
    precio = 5000.00, duracion = '25 min', asientos = 16
WHERE name = 'Sotrauraba — Sopetrán a San Jerónimo';

UPDATE buses SET
    name = 'Sotrauraba', origen = 'Sopetrán', destino = 'Vereda El Guayabo',
    precio = 3500.00, duracion = '20 min', asientos = 14
WHERE name = 'Sotrauraba — Sopetrán a El Guayabo';

UPDATE buses SET
    name = 'Rápido Ochoa', origen = 'Sopetrán', destino = 'Medellín Terminal Norte',
    precio = 18000.00, duracion = '1h 45min', asientos = 32
WHERE name = 'Rápido Ochoa — Sopetrán a Medellín';

UPDATE buses SET
    name = 'Rápido Ochoa', origen = 'Sopetrán', destino = 'Santa Fe de Antioquia',
    precio = 8500.00, duracion = '40 min', asientos = 20
WHERE name = 'Rápido Ochoa — Sopetrán a Santa Fe de Antioquia';

-- zonaCobertura para las motos existentes (quedan como "asociación", el detalle
-- de conductor individual vive ahora en la tabla driver)
UPDATE motorbike SET zona_cobertura = 'Casco urbano y veredas cercanas' WHERE id_transports = 2;

-- Conductores individuales (moto y carro) — tabla nueva "driver"
INSERT INTO driver (id_transports, nombre, placa, marca, anio, telefono, disponible, tipo_vehiculo) VALUES
    (2, 'Carlos Muñoz',   'ABC-123', 'Honda CB190',     2022, '3001234567', TRUE,  'MOTO'),
    (2, 'Andrés López',   'XYZ-456', 'Yamaha FZ2.0',    2021, '3109876543', TRUE,  'MOTO'),
    (2, 'Diego Ríos',     'KLM-789', 'Suzuki GS150',    2023, '3204561234', FALSE, 'MOTO'),
    (2, 'Jhon Taborda',   'PAL-345', 'Renault Logan',   2020, '3151112233', TRUE,  'CARRO'),
    (2, 'Martha Giraldo', 'SOB-112', 'Chevrolet Sail',  2021, '3006667788', TRUE,  'CARRO'),
    (2, 'Luis Bermúdez',  'ANT-990', 'Toyota Hilux',    2019, '3145556677', FALSE, 'CARRO');

-- ═══════════════════════════════════════════════════════════════════════════
-- D. CULTURAL — categoria/featured/descripcionLarga en event, imageUrl en
--    news, y migración de las 4 "fechas especiales" + 4 "historia" a sus
--    tablas propias (special_date / history_entry), quitándolas de
--    event/news para que no se dupliquen en /cultural
-- ═══════════════════════════════════════════════════════════════════════════

-- Eventos reales (quedan en "event")
UPDATE event SET categoria = 'Festival',    featured = TRUE,
    descripcion_larga = 'Una de las festividades más esperadas del año en Sopetrán. Las calles se visten de color con arreglos florales elaborados por artesanos locales. Incluye exposición de carros antiguos, conciertos de música popular y la famosa noche de la antorcha que recorre el municipio.'
WHERE description LIKE 'Feria de las Flores%';

UPDATE event SET categoria = 'Arte'         WHERE description LIKE 'Festival de Teatro Popular%';
UPDATE event SET categoria = 'Música'       WHERE description LIKE 'Noche de Serenatas%';
UPDATE event SET categoria = 'Artesanías'   WHERE description LIKE 'Expo Artesanías del Occidente%';

-- Fotos de portada para los 4 eventos reales
INSERT INTO event_images (id_event, url, orden, alt_text)
SELECT id_event, 'https://images.unsplash.com/photo-1533174072545-7a4b6ad7a6c3?w=800&q=80', 0, 'Feria de las Flores Sopetrán'
FROM event WHERE description LIKE 'Feria de las Flores%';
INSERT INTO event_images (id_event, url, orden, alt_text)
SELECT id_event, 'https://images.unsplash.com/photo-1503095396549-807759245b35?w=800&q=80', 0, 'Festival de Teatro Popular'
FROM event WHERE description LIKE 'Festival de Teatro Popular%';
INSERT INTO event_images (id_event, url, orden, alt_text)
SELECT id_event, 'https://images.unsplash.com/photo-1514320291840-2e0a9bf2a9ae?w=800&q=80', 0, 'Noche de Serenatas'
FROM event WHERE description LIKE 'Noche de Serenatas%';
INSERT INTO event_images (id_event, url, orden, alt_text)
SELECT id_event, 'https://images.unsplash.com/photo-1528283648649-33e32552b3d3?w=800&q=80', 0, 'Expo Artesanías del Occidente'
FROM event WHERE description LIKE 'Expo Artesanías del Occidente%';

-- Migra las 4 "fechas especiales" de event → special_date (y las borra de event)
INSERT INTO special_date (id_public_entitie, nombre, descripcion, dia, mes)
SELECT id_public_entitie, 'Novenas de Navidad',
       'Celebración tradicional con pesebres artesanales que adornan cada barrio del municipio.', 25, 12
FROM event WHERE description LIKE 'Novenas de Navidad%';

INSERT INTO special_date (id_public_entitie, nombre, descripcion, dia, mes)
SELECT id_public_entitie, 'Día de la Candelaria',
       'Patrona del municipio. Misa solemne, procesión por las calles principales y celebraciones populares.', 2, 2
FROM event WHERE description LIKE 'Día de la Candelaria%';

INSERT INTO special_date (id_public_entitie, nombre, descripcion, dia, mes)
SELECT id_public_entitie, 'Día de la Virgen del Carmen',
       'Celebración del transporte. Bendición de vehículos y procesión por las calles principales del municipio.', 16, 7
FROM event WHERE description LIKE 'Día de la Virgen del Carmen%';

INSERT INTO special_date (id_public_entitie, nombre, descripcion, dia, mes)
SELECT id_public_entitie, 'Fiestas de la Independencia',
       'Actos cívicos, izadas de bandera, presentaciones artísticas y desfile por el casco urbano.', 7, 8
FROM event WHERE description LIKE 'Fiestas de la Independencia%';

DELETE FROM event WHERE description LIKE 'Novenas de Navidad%'
    OR description LIKE 'Día de la Candelaria%'
    OR description LIKE 'Día de la Virgen del Carmen%'
    OR description LIKE 'Fiestas de la Independencia%';

-- imageUrl para las noticias (todas, antes de mover las 4 que se vuelven historia)
UPDATE news SET image_url = 'https://images.unsplash.com/photo-1531572753322-ad063cecc140?w=800&q=80' WHERE title LIKE 'Los Orígenes de Sopetrán%';
UPDATE news SET image_url = '/img/PRINCI/BasilicaCuadro.PNG'  WHERE title LIKE 'La Basílica de Nuestra Señora%';
UPDATE news SET image_url = '/img/PRINCI/carrusel1.jpg'       WHERE title LIKE 'Sopetrán: Verde y Biodiversidad%';
UPDATE news SET image_url = 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=800&q=80' WHERE title LIKE 'Tradición y Arte Popular%';
UPDATE news SET image_url = '/img/Logo/sopetranlogo_blancoy negro_Sinfondo.png' WHERE title LIKE 'SopetranClick: La plataforma%';

-- Migra las 4 entradas de historia municipal de news → history_entry (y las borra de news)
INSERT INTO history_entry (id_public_entitie, era, titulo, texto, numero, orden, main)
SELECT id_public_entitie, 'Fundación · Siglo XVI', 'Los Orígenes de Sopetrán', description, '01', 1, TRUE
FROM news WHERE title LIKE 'Los Orígenes de Sopetrán%';

INSERT INTO history_entry (id_public_entitie, era, titulo, texto, numero, orden, main)
SELECT id_public_entitie, 'Siglo XIX', 'La Basílica de Nuestra Señora', description, '02', 2, FALSE
FROM news WHERE title LIKE 'La Basílica de Nuestra Señora%';

INSERT INTO history_entry (id_public_entitie, era, titulo, texto, numero, orden, main)
SELECT id_public_entitie, 'Geografía', 'Verde y Biodiversidad', description, '03', 3, FALSE
FROM news WHERE title LIKE 'Sopetrán: Verde y Biodiversidad%';

INSERT INTO history_entry (id_public_entitie, era, titulo, texto, numero, orden, main)
SELECT id_public_entitie, 'Cultura Viva', 'Tradición y Arte Popular', description, '04', 4, FALSE
FROM news WHERE title LIKE 'Tradición y Arte Popular%';

DELETE FROM news WHERE title LIKE 'Los Orígenes de Sopetrán%'
    OR title LIKE 'La Basílica de Nuestra Señora%'
    OR title LIKE 'Sopetrán: Verde y Biodiversidad%'
    OR title LIKE 'Tradición y Arte Popular%';

-- ═══════════════════════════════════════════════════════════════════════════
-- VERIFICACIÓN
-- ═══════════════════════════════════════════════════════════════════════════
DO $$
BEGIN
    RAISE NOTICE '=== SEED COMPLEMENTARIO COMPLETADO ===';
    RAISE NOTICE 'Restaurantes con rating: %', (SELECT COUNT(*) FROM restaurant WHERE rating IS NOT NULL);
    RAISE NOTICE 'Locales con rating:      %', (SELECT COUNT(*) FROM local WHERE rating IS NOT NULL);
    RAISE NOTICE 'Veredas con tags:        %', (SELECT COUNT(*) FROM site WHERE tags IS NOT NULL);
    RAISE NOTICE 'Lugares con tipo:        %', (SELECT COUNT(*) FROM iconic_place WHERE tipo IS NOT NULL);
    RAISE NOTICE 'Fotos de lugares:        %', (SELECT COUNT(*) FROM iconic_place_images);
    RAISE NOTICE 'Buses con origen/destino:%', (SELECT COUNT(*) FROM buses WHERE origen IS NOT NULL);
    RAISE NOTICE 'Empresas de bus (distintas): %', (SELECT COUNT(DISTINCT name) FROM buses);
    RAISE NOTICE 'Conductores (driver):    %', (SELECT COUNT(*) FROM driver);
    RAISE NOTICE 'Eventos con categoria:   %', (SELECT COUNT(*) FROM event WHERE categoria IS NOT NULL);
    RAISE NOTICE 'Eventos restantes:       %', (SELECT COUNT(*) FROM event);
    RAISE NOTICE 'Fotos de eventos:        %', (SELECT COUNT(*) FROM event_images);
    RAISE NOTICE 'Fechas especiales:       %', (SELECT COUNT(*) FROM special_date);
    RAISE NOTICE 'Noticias con imageUrl:   %', (SELECT COUNT(*) FROM news WHERE image_url IS NOT NULL);
    RAISE NOTICE 'Noticias restantes:      %', (SELECT COUNT(*) FROM news);
    RAISE NOTICE 'Entradas de historia:    %', (SELECT COUNT(*) FROM history_entry);
END;
$$;
