// Variables globales para la paginación y estado de carga
let currentPage = 0;
let isLoading = false;
let initialLoadDone = false; // Indica si ya se cargó la primera página (y filtros) sin activar scroll

/**
 * Carga los datos en la base de datos desde la API externa.
 * Se dispara al hacer clic en el botón "Cargar Data".
 */
async function loadData() {
    try {
        const response = await fetch('/api/load', { method: 'POST' });
        if (!response.ok) {
            throw new Error(`Error al cargar datos: ${response.statusText}`);
        }
        alert("Datos cargados exitosamente");
        // Después de cargar, pobla los filtros y carga la primera página de Pokémon
        await loadFilters();
        currentPage = 0;
        await loadPokemon(currentPage, false);
        initialLoadDone = true;
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
        const typesData = await typesRes.json();
        const abilitiesData = await abilitiesRes.json();

        // Debug: verificar lo que se recibe en consola
        console.log("Tipos recibidos:", typesData);
        console.log("Habilidades recibidas:", abilitiesData);

        // Asegurarse de que los datos sean arrays (si no, intentar acceder a la propiedad "content")
        const types = Array.isArray(typesData) ? typesData : (typesData.content || []);
        const abilities = Array.isArray(abilitiesData) ? abilitiesData : (abilitiesData.content || []);

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
    console.log("Cargando página:", page);

    // Construir la URL con parámetros de paginación
    let url = `/api/Pokemon?page=${page}&size=30`;
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
    document.getElementById('pokemonName').textContent = pokemon.name || "Desconocido";
    document.getElementById('pokemonId').textContent = pokemon.id ? `#${pokemon.id}` : "";
    document.getElementById('pokemonDescription').textContent = pokemon.description || "No hay descripción disponible.";
    document.getElementById('pokemonImage').src = pokemon.sprites?.frontDefault || '/placeholder.svg';
    document.getElementById('pokemonHeight').textContent = `Altura: ${pokemon.height}`;
    document.getElementById('pokemonWeight').textContent = `Peso: ${pokemon.weight}`;

    // Mostrar habilidades
    document.getElementById('pokemonAbility').textContent = pokemon.abilities?.length
        ? `Habilidades: ${pokemon.abilities.map(a => a.name).join(', ')}`
        : "Habilidades: No disponible";

    // Mostrar tipos
    const typesDiv = document.getElementById('pokemonTypes');
    if (pokemon.types && pokemon.types.length > 0) {
        typesDiv.textContent = "Tipos: " + pokemon.types.map(t => t.name).join(', ');
    } else {
        typesDiv.textContent = "";
    }

    // Actualizar estadísticas usando <progress>
    const statMapping = {
        'hpStat': 'hp',
        'attackStat': 'attack',
        'defenseStat': 'defense',
        'spAttackStat': 'special-attack',
        'spDefenseStat': 'special-defense',
        'speedStat': 'speed'
    };

    if (pokemon.stats && Array.isArray(pokemon.stats)) {
        Object.keys(statMapping).forEach(statId => {
            const statElement = document.getElementById(statId);
            if (statElement) {
                const stat = pokemon.stats.find(s => s.name.toLowerCase().trim() === statMapping[statId]);
                if (stat) {
                    statElement.value = stat.baseStat;
                    statElement.max = 200; // Valor máximo estándar
                } else {
                    statElement.value = 0;
                }
            }
        });
    } else {
        console.warn("No se encontraron estadísticas.");
        Object.keys(statMapping).forEach(statId => {
            const statElement = document.getElementById(statId);
            if (statElement) {
                statElement.value = 0;
            }
        });
    }

    // Obtener y mostrar las evoluciones desde el endpoint correspondiente
    fetch(`/api/Pokemon/evolutions/${pokemon.id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("No se pudo obtener la cadena de evolución.");
            }
            return response.json();
        })
        .then(evolutions => {
            const evolutionDiv = document.getElementById('evolutionChain');
            if (evolutions.length > 0) {
                evolutionDiv.innerHTML = `<strong>Evoluciones:</strong> ${evolutions.join(' ➝ ')}`;
            } else {
                evolutionDiv.innerHTML = `<strong>Evoluciones:</strong> No disponibles`;
            }
        })
        .catch(error => {
            console.error("Error obteniendo evoluciones:", error);
            document.getElementById('evolutionChain').innerHTML = `<strong>Evoluciones:</strong> No disponibles`;
        });
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
        const response = await fetch(`/api/Pokemon/name/${encodeURIComponent(name)}`);
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
        const response = await fetch(`/api/Pokemon/${number}`);
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
    const scrollPosition = window.innerHeight + window.scrollY;
    const threshold = document.documentElement.scrollHeight - 150; // Umbral de 150px
    if (initialLoadDone && !isLoading && scrollPosition >= threshold) {
        currentPage++;
        console.log("Scroll infinito: cargando página", currentPage);
        loadPokemon(currentPage, true);
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

// Al cambiar el filtro de tipo o habilidad, reiniciamos la paginación y desactivamos temporalmente el scroll infinito
document.getElementById('typeFilter').addEventListener('change', () => {
    currentPage = 0;
    window.scrollTo(0, 0);
    initialLoadDone = false;
    loadPokemon(currentPage, false).then(() => { initialLoadDone = true; });
});
document.getElementById('abilityFilter').addEventListener('change', () => {
    currentPage = 0;
    window.scrollTo(0, 0);
    initialLoadDone = false;
    loadPokemon(currentPage, false).then(() => { initialLoadDone = true; });
});

// También se pueden cargar los filtros al inicio si se desea que siempre estén disponibles
document.addEventListener("DOMContentLoaded", () => {
    loadFilters();
    // Si deseas cargar la lista de Pokémon al iniciar (solo si ya hay datos en el backend)
    // currentPage = 0;
    // loadPokemon(currentPage, false).then(() => { initialLoadDone = true; });
});
