const API_URL = 'http://localhost:8080/api';

function getAuthToken() {
    return localStorage.getItem('jwt');
}

if (!getAuthToken()) {
    window.location.href = 'index.html';
}

document.getElementById('adminLogoutBtn').addEventListener('click', () => {
    localStorage.removeItem('jwt');
    localStorage.removeItem('username');
    window.location.href = 'index.html';
});

async function loadAdminData() {
    try {
        const statsRes = await fetch(`${API_URL}/admin/stats`, {
            headers: { 'Authorization': `Bearer ${getAuthToken()}` }
        });
        
        if (statsRes.status === 403 || statsRes.status === 401) {
            alert("Unauthorized access.");
            window.location.href = 'dashboard.html';
            return;
        }

        if (statsRes.ok) {
            const stats = await statsRes.json();
            document.getElementById('adminTotalUsers').innerText = stats.totalUsers;
            document.getElementById('adminTotalCarbon').innerText = stats.totalCarbonEmitted.toFixed(2);
            document.getElementById('adminAvgCarbon').innerText = stats.averageCarbonPerUser.toFixed(2);
        }

        const usersRes = await fetch(`${API_URL}/admin/users`, {
            headers: { 'Authorization': `Bearer ${getAuthToken()}` }
        });

        if (usersRes.ok) {
            const users = await usersRes.json();
            const tbody = document.getElementById('usersTableBody');
            tbody.innerHTML = '';
            
            users.forEach(user => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td class="text-light">${user.id}</td>
                    <td class="text-light">${user.username}</td>
                    <td class="text-light">${user.email}</td>
                    <td><span class="badge ${user.role === 'ROLE_ADMIN' ? 'bg-danger' : 'bg-primary'}">${user.role}</span></td>
                    <td class="text-light">${user.points}</td>
                `;
                tbody.appendChild(tr);
            });
        }
    } catch (error) {
        console.error("Error fetching admin data", error);
    }
}

loadAdminData();
