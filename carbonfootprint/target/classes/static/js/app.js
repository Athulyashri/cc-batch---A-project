const API_URL = 'http://localhost:8080/api';

// DOM Elements - Auth
const loginForm = document.getElementById('loginForm');
const signupForm = document.getElementById('signupForm');
const showSignupBtn = document.getElementById('showSignup');
const showLoginBtn = document.getElementById('showLogin');
const loginSection = document.getElementById('login-section');
const signupSection = document.getElementById('signup-section');
const authMessage = document.getElementById('authMessage');

// DOM Elements - Dashboard
const transportForm = document.getElementById('transportForm');
const energyForm = document.getElementById('energyForm');
const activityMessage = document.getElementById('activityMessage');
const logoutBtn = document.getElementById('logoutBtn');
const downloadPdfBtn = document.getElementById('downloadPdfBtn');

// Helper to check auth
function getAuthToken() {
    return localStorage.getItem('jwt');
}

// Redirect if needed
if (window.location.pathname.endsWith('dashboard.html') && !getAuthToken()) {
    window.location.href = 'index.html';
}

if (window.location.pathname.endsWith('index.html') || window.location.pathname === '/') {
    if (getAuthToken()) {
        window.location.href = 'dashboard.html';
    }

    // Toggle forms
    if(showSignupBtn) {
        showSignupBtn.addEventListener('click', (e) => {
            e.preventDefault();
            loginSection.classList.remove('active');
            signupSection.classList.add('active');
            authMessage.innerText = '';
        });
    }

    if(showLoginBtn) {
        showLoginBtn.addEventListener('click', (e) => {
            e.preventDefault();
            signupSection.classList.remove('active');
            loginSection.classList.add('active');
            authMessage.innerText = '';
        });
    }

    // Login
    if(loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const username = document.getElementById('loginUsername').value;
            const password = document.getElementById('loginPassword').value;
            
            try {
                const res = await fetch(`${API_URL}/auth/signin`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username, password })
                });
                
                if (res.ok) {
                    const data = await res.json();
                    localStorage.setItem('jwt', data.token);
                    localStorage.setItem('username', data.username);
                    window.location.href = 'dashboard.html';
                } else {
                    const err = await res.json();
                    authMessage.innerHTML = `<span class="text-danger">${err.message || 'Login failed'}</span>`;
                }
            } catch (err) {
                authMessage.innerHTML = `<span class="text-danger">Server error</span>`;
            }
        });
    }

    // Signup
    if(signupForm) {
        signupForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const username = document.getElementById('signupUsername').value;
            const email = document.getElementById('signupEmail').value;
            const password = document.getElementById('signupPassword').value;
            
            try {
                const res = await fetch(`${API_URL}/auth/signup`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username, email, password })
                });
                
                const data = await res.json();
                if (res.ok) {
                    authMessage.innerHTML = `<span class="text-success">${data.message} Please log in.</span>`;
                    signupSection.classList.remove('active');
                    loginSection.classList.add('active');
                } else {
                    authMessage.innerHTML = `<span class="text-danger">${data.message}</span>`;
                }
            } catch (err) {
                authMessage.innerHTML = `<span class="text-danger">Server error</span>`;
            }
        });
    }
}

// Dashboard Logic
if (window.location.pathname.endsWith('dashboard.html')) {
    
    document.getElementById('welcomeMessage').innerText = `Welcome, ${localStorage.getItem('username')}`;
    
    logoutBtn.addEventListener('click', () => {
        localStorage.removeItem('jwt');
        localStorage.removeItem('username');
        window.location.href = 'index.html';
    });
    
    // Fetch Summary
    async function loadSummary() {
        try {
            const res = await fetch(`${API_URL}/footprint/summary`, {
                headers: { 'Authorization': `Bearer ${getAuthToken()}` }
            });
            if(res.status === 401) {
                logoutBtn.click();
                return;
            }
            if (res.ok) {
                const data = await res.json();
                document.getElementById('totalFootprint').innerText = data.totalFootprint.toFixed(2);
                
                const recList = document.getElementById('recommendationsList');
                recList.innerHTML = '';
                if(data.recommendations.length > 0) {
                    data.recommendations.forEach(r => {
                        recList.innerHTML += `<li class="list-group-item bg-transparent text-light border-0"><i class="bi bi-star-fill text-warning me-2"></i> ${r}</li>`;
                    });
                } else {
                    recList.innerHTML = '<li class="list-group-item bg-transparent text-muted border-0">No recommendations yet. Start logging activities!</li>';
                }
            }
        } catch(err) {
            console.error('Failed to load summary');
        }
    }
    
    loadSummary();
    
    // Log Transport
    if(transportForm) {
        transportForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const type = document.getElementById('transportType').value;
            const distance = document.getElementById('transportDistance').value;
            
            try {
                const res = await fetch(`${API_URL}/footprint/transport`, {
                    method: 'POST',
                    headers: { 
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${getAuthToken()}`
                    },
                    body: JSON.stringify({ type, distance: parseFloat(distance) })
                });
                if(res.ok) {
                    activityMessage.innerHTML = `<span class="text-success">Transport logged successfully!</span>`;
                    document.getElementById('transportDistance').value = '';
                    loadSummary();
                }
            } catch (err) {
                activityMessage.innerHTML = `<span class="text-danger">Failed to log activity.</span>`;
            }
            setTimeout(() => activityMessage.innerText = '', 3000);
        });
    }

    // Log Energy
    if(energyForm) {
        energyForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const kwhUsed = document.getElementById('electricityKwh').value;
            
            try {
                const res = await fetch(`${API_URL}/footprint/electricity`, {
                    method: 'POST',
                    headers: { 
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${getAuthToken()}`
                    },
                    body: JSON.stringify({ kwhUsed: parseFloat(kwhUsed) })
                });
                if(res.ok) {
                    activityMessage.innerHTML = `<span class="text-success">Energy logged successfully!</span>`;
                    document.getElementById('electricityKwh').value = '';
                    loadSummary();
                }
            } catch (err) {
                activityMessage.innerHTML = `<span class="text-danger">Failed to log activity.</span>`;
            }
            setTimeout(() => activityMessage.innerText = '', 3000);
        });
    }

    // Download PDF
    if(downloadPdfBtn) {
        downloadPdfBtn.addEventListener('click', async () => {
            try {
                const res = await fetch(`${API_URL}/footprint/report/pdf`, {
                    headers: { 'Authorization': `Bearer ${getAuthToken()}` }
                });
                if (res.ok) {
                    const blob = await res.blob();
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'footprint_report.pdf';
                    document.body.appendChild(a);
                    a.click();
                    a.remove();
                }
            } catch(err) {
                console.error('Failed to download PDF', err);
            }
        });
    }
}

function renderCharts(data) {
    const pieCtx = document.getElementById('emissionsPieChart');
    if (pieCtx) {
        new Chart(pieCtx, {
            type: 'pie',
            data: {
                labels: ['Transport', 'Electricity', 'Food', 'Waste'],
                datasets: [{
                    data: [20, 30, 40, 10], // Dummy data
                    backgroundColor: ['#ff6384', '#36a2eb', '#ffce56', '#4bc0c0']
                }]
            }
        });
    }
    const barCtx = document.getElementById('emissionsBarChart');
    if (barCtx) {
        new Chart(barCtx, {
            type: 'bar',
            data: {
                labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4'],
                datasets: [{
                    label: 'Emissions',
                    data: [100, 150, 120, 90], // Dummy data
                    backgroundColor: '#36a2eb'
                }]
            }
        });
    }
}
setTimeout(() => renderCharts(), 1500);

const pdfBtn = document.getElementById('downloadPdfBtn');
if (pdfBtn) {
    pdfBtn.addEventListener('click', () => {
        window.open('/api/report/download', '_blank');
    });
}

