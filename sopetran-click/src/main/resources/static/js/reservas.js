let calificacionActual = 0;
let resenaItemSeleccionado = '';

function cargarReservas() {
    const contenedor = document.getElementById('lista-reservas-container');
    const msjSinReservas = document.getElementById('sin-reservas-msg');
    
    if (!contenedor || !msjSinReservas) return;
    
    const historial = JSON.parse(localStorage.getItem('historial_reservas') || '[]');
    
    if (historial.length === 0) {
        contenedor.style.display = 'none';
        msjSinReservas.style.display = 'block';
        return;
    }
    
    contenedor.style.display = 'flex';
    msjSinReservas.style.display = 'none';
    
    contenedor.innerHTML = '';
    
    historial.forEach((reserva, index) => {
        const card = document.createElement('div');
        card.style.cssText = 'background: rgba(255,255,255,0.03); border: 1px solid rgba(255,255,255,0.1); border-radius: 16px; padding: 20px; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 16px;';
        
        const infoHtml = `
            <div>
                <span style="font-size: 0.75rem; color: #8b0000; text-transform: uppercase; letter-spacing: 1px; font-weight: bold;">${reserva.tipo} · ${reserva.fecha}</span>
                <h3 style="font-size: 1.2rem; font-weight: bold; margin: 4px 0;">${reserva.item}</h3>
                <p style="font-size: 0.9rem; color: rgba(255,255,255,0.6); margin: 0;">${reserva.local || ''}</p>
            </div>
            <div style="display: flex; gap: 12px; align-items: center;">
                <span style="font-weight: bold; color: #ffc107;">${reserva.precio}</span>
                <button onclick="abrirModalResena('${reserva.item} - ${reserva.local}')" style="background: rgba(255,255,255,0.1); color: white; border: none; padding: 8px 16px; border-radius: 8px; font-size: 0.85rem; font-weight: 600; cursor: pointer; transition: 0.3s;" onmouseover="this.style.background='rgba(255,255,255,0.2)'" onmouseout="this.style.background='rgba(255,255,255,0.1)'">
                    <i class="bi bi-star"></i> Calificar
                </button>
            </div>
        `;
        card.innerHTML = infoHtml;
        contenedor.appendChild(card);
    });
}

function abrirModalResena(nombreItem) {
    resenaItemSeleccionado = nombreItem;
    document.getElementById('resena-item-nombre').innerText = nombreItem;
    setRating(0);
    document.getElementById('resena-comentario').value = '';
    const modal = document.getElementById('modal-resena');
    if (modal) modal.style.display = 'flex';
}

function cerrarModalResena() {
    const modal = document.getElementById('modal-resena');
    if (modal) modal.style.display = 'none';
}

function setRating(rating) {
    calificacionActual = rating;
    const estrellas = document.getElementById('estrellas-resena').children;
    for (let i = 0; i < estrellas.length; i++) {
        if (i < rating) {
            estrellas[i].classList.replace('bi-star', 'bi-star-fill');
        } else {
            estrellas[i].classList.replace('bi-star-fill', 'bi-star');
        }
    }
}

function enviarResena() {
    if (calificacionActual === 0) {
        alert("Por favor selecciona una calificación.");
        return;
    }
    
    const comentario = document.getElementById('resena-comentario').value;
    
    const resenaSimulada = {
        item: resenaItemSeleccionado,
        rating: calificacionActual,
        comment: comentario,
        fecha: new Date().toISOString()
    };
    
    console.log("Reseña enviada: ", JSON.stringify(resenaSimulada));
    
    cerrarModalResena();
    alert("¡Gracias por tu opinión! Tu reseña ha sido enviada con éxito.");
}

// Hook para cargar reservas cuando se abre la vista
const originalAbrirModulo = window.abrirModulo;
if (originalAbrirModulo) {
    window.abrirModulo = function(modulo) {
        if (modulo === 'reservas') {
            const jwt = localStorage.getItem('auth_jwt');
            if (!jwt) {
                if (typeof abrirModalAuth === 'function') {
                    abrirModalAuth();
                } else {
                    alert('Debes iniciar sesión para ver tus reservas.');
                }
                return; // Bloquear acceso si no hay auth
            }
            cargarReservas();
        }
        originalAbrirModulo(modulo);
    };
}
