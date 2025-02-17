// Variables globales para la paginación y estado de carga
let currentPage = 0;
let isLoading = false;

/**
 * Carga los datos en la base de datos desde la API externa.
 * Se dispara al hacer clic en el botón "Cargar Data".
 */
async function loadData() {
    try {
        const response = await fetch('/api/pokemon/load', { method: 'POST' });
        if (!response.ok) {
            throw new Error(`Error al cargar datos: ${response.statusText}`);
        }
        alert("Datos cargados exitosamente");
        // Después de cargar, pobla los filtros y carga la primera página de Pokémon
        await loadFilters();
        currentPage = 0;
        loadPokemon(currentPage, false);
    } catch (error) {
        console.error(error);
        alert(error.message);
    }
}

/**
 * Obtiene y carga en los ComboBox los tipos y habilidades.
 */
async function loadFilters() {
    try {
        const [typesRes, abilitiesRes] = await Promise.all([
            fetch('/api/types'),
            fetch('/api/abilities')
        ]);
        const types = await typesRes.json();
        const abilities = await abilitiesRes.json();

        // Rellenar el select de Tipos
        const typeSelect = document.getElementById('typeFilter');
        typeSelect.innerHTML = '<option value="">Filtrar por Tipo</option>';
        types.forEach(type => {
            const option = document.createElement('option');
            option.value = type;
            option.textContent = type;
            typeSelect.appendChild(option);
        });

        // Rellenar el select de Habilidades
        const abilitySelect = document.getElementById('abilityFilter');
        abilitySelect.innerHTML = '<option value="">Filtrar por Habilidad</option>';
        abilities.forEach(ability => {
            const option = document.createElement('option');
            option.value = ability;
            option.textContent = ability;
            abilitySelect.appendChild(option);
        });
    } catch (error) {
        console.error("Error al cargar filtros:", error);
    }
}

/**
 * Carga una página de Pokémon, aplicando filtros si los hay.
 * @param {number} page Número de página a cargar.
 * @param {boolean} append Si es true, añade al grid; si es false, lo reinicia.
 */
async function loadPokemon(page = 0, append = false) {
    if (isLoading) return;
    isLoading = true;

    // Construir la URL con parámetros de paginación
    let url = `/api/pokemon?page=${page}&size=30`;
    const typeFilter = document.getElementById('typeFilter').value;
    const abilityFilter = document.getElementById('abilityFilter').value;
    if (typeFilter) {
        url += `&type=${encodeURIComponent(typeFilter)}`;
    }
    if (abilityFilter) {
        url += `&ability=${encodeURIComponent(abilityFilter)}`;
    }

    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Error al cargar los Pokémon: ${response.statusText}`);
        }
        const data = await response.json();
        updatePokemonGrid(data.content, append);
    } catch (error) {
        console.error(error);
        alert("Error al cargar los Pokémon.");
    }
    isLoading = false;
}

/**
 * Actualiza la grilla (grid) con las tarjetas de Pokémon.
 * Si append es false, se reinicia la lista; si es true, se añaden nuevas tarjetas.
 * @param {Array} pokemonList Lista de Pokémon a mostrar.
 * @param {boolean} append Indica si se debe añadir a la lista existente.
 */
function updatePokemonGrid(pokemonList, append = false) {
    const container = document.getElementById('pokemonList');
    if (!append) container.innerHTML = ""; // Reiniciar si no se está agregando
    pokemonList.forEach(pokemon => {
        const card = document.createElement("div");
        card.classList.add("pokemon-card");
        card.innerHTML = `
      <h3>${pokemon.name}</h3>
      <img src="${pokemon.sprites?.frontDefault || '/placeholder.svg'}" alt="${pokemon.name}" />
      <p>#${pokemon.id}</p>
    `;
        // Al hacer clic en la tarjeta, se muestran los detalles en la sección izquierda
        card.addEventListener('click', () => {
            displayPokemonDetails(pokemon);
        });
        container.appendChild(card);
    });
}

/**
 * Actualiza la sección de detalles del Pokémon (lado izquierdo).
 * Muestra nombre, descripción, imagen, información, tipos y estadísticas.
 * @param {Object} pokemon Objeto Pokémon con sus datos.
 */
function displayPokemonDetails(pokemon) {
    document.getElementById('pokemonTitle').textContent = pokemon.name;
    document.getElementById('pokemonDescription').textContent = pokemon.description || "";
    document.getElementById('pokemonImage').src = pokemon.sprites?.frontDefault || '/placeholder.svg';
    document.getElementById('pokemonHeight').textContent = `Altura: ${pokemon.height}`;
    document.getElementById('pokemonWeight').textContent = `Peso: ${pokemon.weight}`;

    // Mostrar tipos
    const typesDiv = document.getElementById('pokemonTypes');
    if (pokemon.types && pokemon.types.length > 0) {
        typesDiv.textContent = "Tipos: " + pokemon.types.map(t => t.name).join(', ');
    } else {
        typesDiv.textContent = "";
    }

    // Actualizar estadísticas básicas usando elementos <progress>
    // Se asume que pokemon.stats es un arreglo de objetos con propiedades 'name' y 'baseStat'
    const statMapping = {
        'hpStat': 'PS',
        'attackStat': 'Ataque',
        'defenseStat': 'Defensa',
        'spAttackStat': 'Ataque Especial',
        'spDefenseStat': 'Defensa Especial',
        'speedStat': 'Velocidad'
    };
    for (let statId in statMapping) {
        const statElement = document.getElementById(statId);
        if (pokemon.stats) {
            // Se busca el stat cuyo nombre coincida (en minúsculas para evitar problemas)
            const stat = pokemon.stats.find(s => s.name.toLowerCase() === statMapping[statId].toLowerCase());
            statElement.value = stat ? stat.baseStat : 0;
        } else {
            statElement.value = 0;
        }
    }

    // (Opcional) Si deseas cargar la cadena de evoluciones, podrías hacer otra petición aquí.
}

/**
 * Función para buscar un Pokémon por su nombre.
 */
async function searchPokemonByName() {
    const name = document.getElementById('searchName').value.trim();
    if (!name) {
        alert("Introduce un nombre para buscar.");
        return;
    }
    try {
        const response = await fetch(`/api/pokemon/name/${encodeURIComponent(name)}`);
        if (!response.ok) throw new Error("Pokémon no encontrado.");
        const pokemon = await response.json();
        displayPokemonDetails(pokemon);
    } catch (error) {
        console.error(error);
        alert(error.message);
    }
}

/**
 * Función para buscar un Pokémon por su número (ID).
 */
async function searchPokemonByNumber() {
    const number = document.getElementById('searchNumber').value.trim();
    if (!number) {
        alert("Introduce un número para buscar.");
        return;
    }
    try {
        const response = await fetch(`/api/pokemon/${number}`);
        if (!response.ok) throw new Error("Pokémon no encontrado.");
        const pokemon = await response.json();
        displayPokemonDetails(pokemon);
    } catch (error) {
        console.error(error);
        alert(error.message);
    }
}

/**
 * Implementa el scroll infinito.
 * Cuando el usuario se acerca al final de la página, se carga la siguiente página de Pokémon.
 */
window.addEventListener('scroll', () => {
    // Se verifica si se llegó cerca del final de la página (por ejemplo, a 100px de distancia)
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 100 && !isLoading) {
        currentPage++;
        loadPokemon(currentPage, true); // Se añaden más tarjetas sin limpiar el grid
    }
});

/*
 * Event Listeners para los botones y filtros:
 */
// Botón para cargar la data inicial
document.getElementById('loadDataBtn').addEventListener('click', loadData);

// Botón para buscar por nombre
document.getElementById('searchNameBtn').addEventListener('click', searchPokemonByName);

// Botón para buscar por número
document.getElementById('searchNumberBtn').addEventListener('click', searchPokemonByNumber);

// Cuando se cambia el filtro de tipo o habilidad, se reinicia la paginación y se recarga la lista
document.getElementById('typeFilter').addEventListener('change', () => {
    currentPage = 0;
    loadPokemon(currentPage, false);
});
document.getElementById('abilityFilter').addEventListener('change', () => {
    currentPage = 0;
    loadPokemon(currentPage, false);
});

// (Opcional) Si ya se han cargado datos previamente, puedes llamar a loadPokemon(currentPage, false) al inicio.
