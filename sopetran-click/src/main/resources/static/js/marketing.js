async function enviarLeadMarketing(e) {
  e.preventDefault();
  const fb = document.getElementById('mkt-feedback');
  const negocio = document.getElementById('mkt-negocio').value.trim();
  const payload = {
    tipo:     'PETICION',
    nombre:   document.getElementById('mkt-nombre').value.trim(),
    email:    document.getElementById('mkt-email').value.trim(),
    telefono: document.getElementById('mkt-tel').value.trim(),
    asunto:   'Asesoría comercial - ' + negocio,
    mensaje:  document.getElementById('mkt-mensaje').value.trim()
  };
  try {
    const res = await fetch('/api/pqrs', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!res.ok) throw new Error('HTTP ' + res.status);
    fb.textContent = '✅ ¡Recibimos tu solicitud! Nuestro equipo comercial te contactará pronto.';
    fb.className = 'mkt-feedback ok';
    document.getElementById('marketing-form').reset();
  } catch (err) {
    fb.textContent = '❌ No se pudo enviar. Intenta de nuevo o escríbenos por WhatsApp.';
    fb.className = 'mkt-feedback error';
  }
  return false;
}
