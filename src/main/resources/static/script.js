let currentSortOrder = 'none';

function toggleSort() {
    if (currentSortOrder === 'none') {
        currentSortOrder = 'asc';
    } else if (currentSortOrder === 'asc') {
        currentSortOrder = 'desc';
    } else {
        currentSortOrder = 'none';
    }
    updateSortButton();
    loadFilms(currentSortOrder);
}

function updateSortButton() {
    const sortButton = document.getElementById('sortButton');
    if (currentSortOrder === 'none') sortButton.innerHTML = 'Sortuj';
    else if (currentSortOrder === 'asc') sortButton.innerHTML = 'Sortuj: A-Z';
    else sortButton.innerHTML = 'Sortuj: Z-A';
}

function loadFilms(sortOrder) {
    fetch(`/api/data?sort=${sortOrder === 'none' ? '' : sortOrder}`)
        .then(response => response.json())
        .then(movies => {
            const container = document.getElementById('films-container');
            container.innerHTML = movies.map(movie => `
                <div class="movie-card">
                    <img src="/images/${movie.imagePath}" alt="${movie.title}" class="movie-poster">
                    <div class="movie-content">
                        <h3 class="movie-title">${movie.title}</h3>
                        <div class="movie-meta">${movie.releaseYear} • ${movie.director.name}</div>
                        <p class="movie-description">${movie.description}</p>
                        <div class="movie-actions">
                            <button onclick="deleteFilm(${movie.id})" class="btn btn-danger">Usuń</button>
                            <button onclick="openEditModal(${JSON.stringify(movie).replace(/"/g, '&quot;')})" class="btn btn-primary">Edytuj</button>
                        </div>
                    </div>
                </div>
            `).join('');
        });
    window.scrollTo({ top: 0, behavior: "smooth" });
}

function deleteFilm(id) {
    if (confirm('Czy na pewno chcesz usunąć ten film?')) {
        fetch(`/api/data/${id}`, {
            method: 'DELETE'
        }).then(() => {
            loadFilms();
        }).catch(error => {
            console.error('Błąd:', error);
            alert('Wystąpił błąd podczas usuwania filmu: ' + error.message);
        });
    }
}

function openEditModal(movie) {
    document.getElementById('editFilmId').value = movie.id;
    document.getElementById('editTitle').value = movie.title;
    document.getElementById('editYear').value = movie.releaseYear;

    loadDirectors('editDirectorSelect').then(() => {
        const directorSelect = document.getElementById('editDirectorSelect');
        directorSelect.value = movie.director.name;
    });
    document.getElementById('editDescription').value = movie.description;
    document.getElementById('currentImagePreview').src = `/images/${movie.imagePath}`;
    document.getElementById('editModal').style.display = 'flex';
}

function loadDirectors(selectId = null) {
    return fetch('/api/data/directors')
        .then(response => response.json())
        .then(directors => {
            if (selectId) {
                const select = document.getElementById(selectId);
                select.innerHTML = '<option value="">-- Wybierz reżysera --</option>';

                directors.forEach(director => {
                    const option = document.createElement('option');
                    option.value = director.name;
                    option.textContent = director.name;
                    select.appendChild(option);
                });
            }
        }).catch(message => alert('Błąd podczas ładowania reżyserów' + message));
}

function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
}

document.getElementById('editForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const directorSelect = document.getElementById('editDirectorSelect');
    const newDirectorInput = document.getElementById('editNewDirector');
    let directorName;

    if (directorSelect.value && !newDirectorInput.value.trim()) {
        directorName = directorSelect.value;
    } else if (newDirectorInput.value.trim()) {
        directorName = newDirectorInput.value.trim();
    } else {
        alert('Wybierz reżysera lub podaj nowego');
        return;
    }

    const formData = new FormData();
    const filmId = document.getElementById('editFilmId').value;
    formData.append('title', document.getElementById('editTitle').value);
    formData.append('releaseYear', document.getElementById('editYear').value);
    formData.append('directorName', directorName);
    formData.append('description', document.getElementById('editDescription').value);

    const imageInput = document.getElementById('editImage');
    if (imageInput.files[0]) {
        formData.append('imageFile', imageInput.files[0]);
    }

    fetch(`/api/data/${filmId}`, {
        method: 'PUT',
        body: formData
    })
        .then(response => response.text())
        .then(message => {
            alert(message);
            closeEditModal();
            loadFilms(currentSortOrder);
            loadDirectors();
        })
        .catch(error => alert(error.message));
});

document.getElementById('addFilmForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const directorSelect = document.getElementById('directorSelect');
    const newDirectorInput = document.getElementById('newDirector');
    let directorName;
    if (directorSelect.value && !newDirectorInput.value.trim()) {
        directorName = directorSelect.value;
    } else if (newDirectorInput.value.trim()) {
        directorName = newDirectorInput.value.trim();
    } else {
        alert('Wybierz reżysera lub podaj nowego');
        return;
    }
    const formData = new FormData();
    formData.append('title', document.getElementById('title').value);
    formData.append('releaseYear', document.getElementById('releaseYear').value);
    formData.append('directorName', directorName);
    formData.append('description', document.getElementById('description').value);
    formData.append('image', document.getElementById('image').files[0]);
    fetch('/api/data', {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(message => {
            window.scrollTo({top: 0, behavior: 'smooth'});
            alert(message);
            loadFilms(currentSortOrder);
            loadDirectors();
            this.reset();
        })
        .catch(error => alert(error.message));
});

document.addEventListener('DOMContentLoaded', () => {
    loadFilms(currentSortOrder);
    loadDirectors('directorSelect');
    window.scrollTo({ top: 0, behavior: "smooth" });
    document.getElementById('sortButton').addEventListener('click', toggleSort);
});