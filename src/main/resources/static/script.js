// script.js — usado apenas em login.html
document.getElementById('login-form')?.addEventListener('submit', function(e) {
  e.preventDefault();

  const username = document.getElementById('username').value.trim();
  
  if (username) {
    // Salva o nome no localStorage (simulação de login)
    localStorage.setItem('loggedInUser', username);
    
    // Redireciona para index.html (ou dashboard se quiser)
    alert('Login realizado com sucesso!');
    window.location.href = 'index.html';
  } else {
    alert('Por favor, preencha seu e-mail ou usuário.');
  }
});