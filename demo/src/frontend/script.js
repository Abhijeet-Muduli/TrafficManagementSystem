// Backend API base URL
const API = 'http://localhost:8080/api/vehicles';

// In-memory list for filtering
let allVehicles = [];

/* ── LOAD ALL VEHICLES ON PAGE START ── */
async function loadVehicles() {
    try {
        const res  = await fetch(API);
        const data = await res.json();
        allVehicles = data;
        renderTable(data);
        updateStats(data);
    } catch (e) {
        // If backend not running, show empty table
        console.warn('Backend not connected:', e.message);
    }
}

/* ── SUBMIT VEHICLE ── */
document.getElementById('submitbtn').addEventListener('click', async function () {
    const vehicleId    = document.getElementById('vehicleId').value.trim();
    const make         = document.getElementById('make').value.trim();
    const model        = document.getElementById('model').value.trim();
    const year         = parseInt(document.getElementById('year').value.trim());
    const color        = document.getElementById('color').value.trim();
    const owner        = document.getElementById('owner').value.trim();
    const licensePlate = document.getElementById('licensePlate').value.trim();
    const mileage      = parseInt(document.getElementById('mileage').value.trim()) || 0;
    const status       = document.getElementById('status').value;

    // Validation
    if (!vehicleId || !make || !model || !year || !owner || !licensePlate) {
        showToast('error', '✗ Please fill all required fields!');
        return;
    }

    const vehicle = {
        vehicleId: parseInt(vehicleId),
        make, model, year, color, owner, licensePlate, mileage, status
    };

    try {
        const res = await fetch(API, {
            method:  'POST',
            headers: { 'Content-Type': 'application/json' },
            body:    JSON.stringify(vehicle)
        });

        if (res.ok || res.status === 201) {
            showToast('ok', '✓ Vehicle added successfully!');
            clearForm();
            loadVehicles(); // refresh table from DB
        } else {
            const err = await res.json();
            showToast('error', '⚠ ' + (err.error || 'Error adding vehicle'));
        }
    } catch (e) {
        showToast('error', '✗ Cannot connect to backend server');
    }
});

/* ── RENDER TABLE ── */
function renderTable(data) {
    const tbody = document.getElementById('vehicleTableBody');

    if (!data || data.length === 0) {
        tbody.innerHTML = `<tr class="empty-row"><td colspan="9">No vehicles found.</td></tr>`;
        document.getElementById('vehicleCount').textContent = 0;
        return;
    }

    tbody.innerHTML = data.map(v => {
        const badgeClass = v.status === 'Active'           ? 'badge-active'   :
                           v.status === 'Renewal Required' ? 'badge-renewal'  : 'badge-inactive';
        return `<tr>
            <td><strong>#${v.vehicleId}</strong></td>
            <td>${v.make}</td>
            <td>${v.model}</td>
            <td>${v.year}</td>
            <td>${v.color || '—'}</td>
            <td>${v.owner}</td>
            <td><code>${v.licensePlate}</code></td>
            <td>${Number(v.mileage).toLocaleString()}</td>
            <td><span class="badge ${badgeClass}">${v.status}</span></td>
        </tr>`;
    }).join('');

    document.getElementById('vehicleCount').textContent = data.length;
}

/* ── SEARCH & FILTER ── */
function filterTable() {
    const query  = document.getElementById('searchBox').value.toLowerCase();
    const filter = document.getElementById('statusFilter').value;

    const filtered = allVehicles.filter(v => {
        const matchSearch = !query ||
            v.make.toLowerCase().includes(query)         ||
            v.model.toLowerCase().includes(query)        ||
            v.owner.toLowerCase().includes(query)        ||
            v.licensePlate.toLowerCase().includes(query);
        const matchStatus = !filter || v.status === filter;
        return matchSearch && matchStatus;
    });

    renderTable(filtered);
}

/* ── UPDATE HEADER STATS ── */
function updateStats(data) {
    document.getElementById('statTotal').textContent    = data.length;
    document.getElementById('statActive').textContent   =
        data.filter(v => v.status === 'Active').length;
    document.getElementById('statRenewals').textContent =
        data.filter(v => v.status === 'Renewal Required').length;
}

/* ── CLEAR FORM ── */
function clearForm() {
    ['vehicleId','make','model','year','color',
     'owner','licensePlate','mileage'].forEach(id => {
        document.getElementById(id).value = '';
    });
    document.getElementById('status').value = 'Active';
}

/* ── TOAST NOTIFICATION ── */
let toastTimer;
function showToast(type, msg) {
    const toast = document.getElementById('toast');
    toast.textContent = msg;
    toast.className   = `toast ${type} show`;
    clearTimeout(toastTimer);
    toastTimer = setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

/* ── INIT ── */
loadVehicles();