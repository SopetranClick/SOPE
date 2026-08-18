function handleAuthClick() {
    const jwt = localStorage.getItem('auth_jwt');
    if (!jwt) {
        if (typeof abrirModalAuth === 'function') {
            abrirModalAuth();
        } else {
            console.error('Modal auth no disponible');
        }
    } else {
        abrirDashboardUsuario();
    }
}

function abrirDashboardUsuario() {
    const container = document.getElementById('user-dashboard-container');
    if (!container) return;

    // Fetch user info from backend (simulated for now if endpoint fails, but writing real fetch)
    const jwt = localStorage.getItem('auth_jwt');
    
    // UI del Dashboard
    container.innerHTML = `
        <div id="dashboard-overlay" style="position: fixed; inset: 0; z-index: 999998; background: rgba(0,0,0,0.8); backdrop-filter: blur(8px); display: flex; justify-content: flex-end;">
            <div id="dashboard-panel" style="width: 100%; max-width: 400px; height: 100vh; background: #0a0a0a; border-left: 1px solid rgba(255,255,255,0.1); padding: 24px; color: white; display: flex; flex-direction: column; animation: slideInRight 0.3s forwards; overflow-y: auto;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px;">
                    <h2 style="font-family: 'Playfair Display', serif; font-size: 1.5rem; margin: 0;">Mi Perfil</h2>
                    <button onclick="cerrarDashboardUsuario()" style="background: transparent; border: none; color: white; font-size: 1.5rem; cursor: pointer;">&times;</button>
                </div>
                
                <div style="background: rgba(255,255,255,0.05); padding: 16px; border-radius: 12px; margin-bottom: 24px;">
                    <div style="display: flex; align-items: center; gap: 12px; margin-bottom: 12px;">
                        <div style="width: 50px; height: 50px; border-radius: 50%; background: #8b0000; display: flex; justify-content: center; align-items: center; font-size: 1.5rem; font-weight: bold;">
                            <i class="bi bi-person"></i>
                        </div>
                        <div>
                            <h3 id="dash-user-name" style="margin: 0; font-size: 1.1rem;">Cargando...</h3>
                            <p id="dash-user-email" style="margin: 0; font-size: 0.85rem; color: rgba(255,255,255,0.5);">cargando@sopetran.com</p>
                        </div>
                    </div>
                    <button onclick="logout()" style="width: 100%; padding: 8px; background: rgba(255,0,0,0.2); border: 1px solid rgba(255,0,0,0.3); color: #ff4d4d; border-radius: 8px; cursor: pointer; transition: 0.3s;" onmouseover="this.style.background='rgba(255,0,0,0.3)'" onmouseout="this.style.background='rgba(255,0,0,0.2)'">Cerrar Sesión</button>
                </div>

                <div style="margin-bottom: 24px;">
                    <h3 style="font-size: 1rem; margin-bottom: 12px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 8px;">Mis Actividades</h3>
                    <button onclick="cerrarDashboardUsuario(); abrirModulo('reservas');" style="width: 100%; padding: 12px; background: rgba(255,255,255,0.05); border: none; color: white; text-align: left; border-radius: 8px; margin-bottom: 8px; cursor: pointer; display: flex; justify-content: space-between; align-items: center;">
                        <span><i class="bi bi-calendar-check" style="margin-right: 8px;"></i> Historial de Reservas</span>
                        <i class="bi bi-chevron-right text-muted"></i>
                    </button>
                    <button onclick="alert('Módulo de locales próximamente')" style="width: 100%; padding: 12px; background: rgba(255,255,255,0.05); border: none; color: white; text-align: left; border-radius: 8px; cursor: pointer; display: flex; justify-content: space-between; align-items: center;">
                        <span><i class="bi bi-shop" style="margin-right: 8px;"></i> Mis Locales</span>
                        <i class="bi bi-chevron-right text-muted"></i>
                    </button>
                </div>
            </div>
        </div>
        <style>
            @keyframes slideInRight {
                from { transform: translateX(100%); }
                to { transform: translateX(0); }
            }
            @keyframes slideOutRight {
                from { transform: translateX(0); }
                to { transform: translateX(100%); }
            }
        </style>
    `;

    // Try fetching user data
    fetch('/api/users/me', {
        headers: { 'Authorization': 'Bearer ' + jwt }
    })
    .then(res => {
        if (!res.ok) throw new Error('Not authenticated');
        return res.json();
    })
    .then(data => {
        document.getElementById('dash-user-name').innerText = data.name || 'Usuario Sopetrán';
        document.getElementById('dash-user-email').innerText = data.email || 'usuario@sopetran.com';
    })
    .catch(e => {
        console.log('Using local user info due to backend unavailability');
        document.getElementById('dash-user-name').innerText = localStorage.getItem('auth_user') || 'Usuario Sopetrán';
        document.getElementById('dash-user-email').innerText = 'usuario@sopetran.com';
    });
}

function cerrarDashboardUsuario() {
    const panel = document.getElementById('dashboard-panel');
    if (panel) {
        panel.style.animation = 'slideOutRight 0.3s forwards';
        setTimeout(() => {
            const container = document.getElementById('user-dashboard-container');
            if (container) container.innerHTML = '';
        }, 300);
    }
}

function logout() {
    localStorage.removeItem('auth_jwt');
    localStorage.removeItem('auth_user');
    cerrarDashboardUsuario();
    // Volver a inicio si estamos en una ruta protegida
    if (typeof vistaActual !== 'undefined' && vistaActual === 'reservas') {
        abrirModulo('inicio');
    }
    alert('Sesión cerrada con éxito.');
}

// ══════════════════════════════════════
// LÓGICA DE RESEÑAS GLOBALES
// ══════════════════════════════════════
function escribirResenaGlobal() {
    const jwt = localStorage.getItem('auth_jwt');
    if (!jwt) {
        alert('Debes iniciar sesión para escribir una reseña.');
        if (typeof abrirModalAuth === 'function') abrirModalAuth();
        return;
    }
    
    // Check if #modal-resena exists (it's in MenuReservas.html which might not be in DOM or might be)
    // Actually, MenuReservas is injected via th:insert in Index.html, so it exists!
    const modal = document.getElementById('modal-resena');
    if (modal) {
        document.getElementById('resena-item-nombre').innerText = 'SopetranClick - General';
        modal.style.display = 'flex';
    } else {
        alert('Módulo de reseñas no disponible en este momento.');
    }
}

function verMasResenas() {
    const container = document.getElementById('global-reviews-container');
    if (!container) return;
    
    container.innerHTML = `
        <div id="reviews-overlay" style="position: fixed; inset: 0; z-index: 999998; background: rgba(0,0,0,0.8); backdrop-filter: blur(8px); display: flex; justify-content: center; align-items: center; padding: 20px;">
            <div id="reviews-panel" style="width: 100%; max-width: 600px; max-height: 80vh; background: #141414; border: 1px solid rgba(255,255,255,0.1); border-radius: 16px; padding: 24px; color: white; display: flex; flex-direction: column; animation: fadeIn 0.3s forwards;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px; border-bottom: 1px solid rgba(255,255,255,0.1); padding-bottom: 16px;">
                    <h2 style="font-family: 'Playfair Display', serif; font-size: 1.5rem; margin: 0;">Todas las Reseñas</h2>
                    <button onclick="cerrarVerMasResenas()" style="background: transparent; border: none; color: white; font-size: 1.5rem; cursor: pointer;">&times;</button>
                </div>
                
                <div id="reviews-list-content" style="overflow-y: auto; display: flex; flex-direction: column; gap: 16px; padding-right: 8px;">
                    <div style="text-align: center; padding: 40px; color: rgba(255,255,255,0.5);">
                        <i class="bi bi-arrow-repeat spin" style="font-size: 2rem; display: inline-block; animation: spin 1s linear infinite;"></i>
                        <p style="margin-top: 10px;">Cargando reseñas desde el servidor...</p>
                    </div>
                </div>
            </div>
        </div>
        <style>
            @keyframes fadeIn { from { opacity: 0; transform: scale(0.95); } to { opacity: 1; transform: scale(1); } }
            @keyframes spin { 100% { transform: rotate(360deg); } }
        </style>
    `;

    // Fetch reviews from backend (simulated for now)
    fetch('/api/reviews')
        .then(res => res.json())
        .then(data => {
            renderReviewsList(data);
        })
        .catch(e => {
            console.log('Using mock reviews due to backend unavailability');
            renderReviewsList([
                { name: 'Camila Morales', origin: 'Medellín', rating: 5, comment: 'Sopetrán superó todas mis expectativas...' },
                { name: 'Andrés Felipe', origin: 'Bogotá', rating: 4, comment: 'Un clima espectacular y gente muy amable.' },
                { name: 'María C.', origin: 'Santa Fe de Antioquia', rating: 5, comment: 'La ruta ecoturística es imperdible, sobre todo la cascada.' }
            ]);
        });
}

function renderReviewsList(reviews) {
    const listContent = document.getElementById('reviews-list-content');
    if (!listContent) return;
    
    if (!reviews || reviews.length === 0) {
        listContent.innerHTML = '<p style="text-align:center; color:rgba(255,255,255,0.5);">No hay reseñas disponibles aún.</p>';
        return;
    }

    listContent.innerHTML = reviews.map(r => `
        <div style="background: rgba(255,255,255,0.05); padding: 16px; border-radius: 12px; border: 1px solid rgba(255,255,255,0.05);">
            <div style="display: flex; justify-content: space-between; margin-bottom: 8px;">
                <div style="display: flex; align-items: center; gap: 12px;">
                    <div style="width: 40px; height: 40px; border-radius: 50%; background: #8b0000; display: flex; justify-content: center; align-items: center; font-weight: bold; font-size: 1.2rem;">
                        ${r.name.charAt(0)}
                    </div>
                    <div>
                        <h4 style="margin: 0; font-size: 1rem;">${r.name}</h4>
                        <p style="margin: 0; font-size: 0.75rem; color: rgba(255,255,255,0.5);">${r.origin || 'Visitante'}</p>
                    </div>
                </div>
                <div style="color: #ffc107;">${'★'.repeat(r.rating)}${'☆'.repeat(5 - r.rating)}</div>
            </div>
            <p style="margin: 0; font-size: 0.9rem; line-height: 1.5; color: rgba(255,255,255,0.8);">${r.comment}</p>
        </div>
    `).join('');
}

function cerrarVerMasResenas() {
    const container = document.getElementById('global-reviews-container');
    if (container) container.innerHTML = '';
}
