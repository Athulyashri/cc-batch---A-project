document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = 'index.html';
        return;
    }

    fetchProfile();

    document.getElementById('logoutBtn').addEventListener('click', (e) => {
        e.preventDefault();
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        window.location.href = 'index.html';
    });

    document.getElementById('darkModeToggle').addEventListener('change', (e) => {
        const isDark = e.target.checked;
        if (isDark) document.body.classList.add('dark-mode');
        else document.body.classList.remove('dark-mode');

        fetch('/api/profile/darkmode', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({ enabled: isDark })
        });
    });

    function fetchProfile() {
        fetch('/api/profile', {
            headers: { 'Authorization': `Bearer ${token}` }
        })
        .then(res => res.json())
        .then(data => {
            document.getElementById('usernameDisplay').textContent = data.username;
            document.getElementById('emailDisplay').textContent = data.email;
            document.getElementById('pointsDisplay').textContent = data.points;
            document.getElementById('streakDisplay').textContent = data.streakCount || 0;
            if (data.avatarUrl) {
                document.getElementById('avatarImg').src = data.avatarUrl;
            }
            if (data.darkModeEnabled) {
                document.getElementById('darkModeToggle').checked = true;
                document.body.classList.add('dark-mode');
            }
        })
        .catch(err => console.error(err));
    }
});
