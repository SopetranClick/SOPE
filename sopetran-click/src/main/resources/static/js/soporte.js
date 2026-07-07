async function enviarPqrs(e) {
  e.preventDefault();
  const fb = document.getElementById('pqrs-feedback');
  const payload = {
    tipo:     document.getElementById('pqrs-tipo').value,
    nombre:   document.getElementById('pqrs-nombre').value.trim(),
    email:    document.getElementById('pqrs-email').value.trim(),
    telefono: document.getElementById('pqrs-tel').value.trim(),
    asunto:   document.getElementById('pqrs-asunto').value.trim(),
    mensaje:  document.getElementById('pqrs-mensaje').value.trim()
  };
  try {
    const res = await fetch('/api/pqrs', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!res.ok) throw new Error('HTTP ' + res.status);
    fb.textContent = '✅ ¡Recibimos tu PQRS! Te contactaremos pronto.';
    fb.className = 'pqrs-feedback ok';
    document.getElementById('pqrs-form').reset();
  } catch (err) {
    fb.textContent = '❌ No se pudo enviar. Intenta de nuevo o escríbenos por WhatsApp.';
    fb.className = 'pqrs-feedback error';
  }
  return false;
}
