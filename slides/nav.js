// Shared slide navigation — used by all episode decks
(function() {
  const slides = document.querySelectorAll('.slide');
  const fill   = document.getElementById('progress-fill');
  const counter = document.getElementById('slide-counter');
  const hint   = document.getElementById('nav-hint');
  const total  = slides.length;
  let current  = 0;

  function show(n) {
    slides[current].classList.remove('active');
    current = Math.max(0, Math.min(total - 1, n));
    slides[current].classList.add('active');
    fill.style.width = ((current + 1) / total * 100).toFixed(1) + '%';
    counter.textContent = (current + 1) + ' / ' + total;
  }

  document.addEventListener('keydown', e => {
    if (e.key === 'ArrowRight' || e.key === ' ') { e.preventDefault(); show(current + 1); }
    if (e.key === 'ArrowLeft')                   { e.preventDefault(); show(current - 1); }
    if (e.key === 'Home')  show(0);
    if (e.key === 'End')   show(total - 1);
  });

  let tx = 0;
  document.addEventListener('touchstart', e => { tx = e.touches[0].clientX; });
  document.addEventListener('touchend',   e => {
    const dx = e.changedTouches[0].clientX - tx;
    if (Math.abs(dx) > 50) show(dx < 0 ? current + 1 : current - 1);
  });

  setTimeout(() => { hint.style.opacity = '0'; }, 4000);
  show(0);
})();
