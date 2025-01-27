package ec.edu.uce.DemoPokedex.Services;

import ec.edu.uce.DemoPokedex.Model.Pokemon;
import ec.edu.uce.DemoPokedex.Repository.PokemonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    // Obtener todos los Pokémon
    @Transactional(readOnly = true)
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    // Obtener Pokémon por ID
    @Transactional(readOnly = true)
    public Optional<Pokemon> getPokemonById(Long id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElse(null);
        Hibernate.initialize(pokemon.getStats());
        return pokemonRepository.findById(id);
    }

    // Obtener Pokémon por nombre
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemonByName(String name) {
        Pokemon pokemon = pokemonRepository.findByNameIgnoreCase(name).get(0);
        Hibernate.initialize(pokemon.getStats());
        return pokemonRepository.findByNameIgnoreCase(name);
    }

    // Obtener Pokémon por tipo
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemonByType(String typeName) {
        return pokemonRepository.findByTypeName(typeName);
    }

    // Obtener las evoluciones de un Pokémon por su ID
    @Transactional(readOnly = true)
    public List<Long> getEvolutionsById(Long id) {
        return pokemonRepository.findEvolutionsById(id);
    }

    // Obtener el sprite de un Pokémon por su ID
    @Transactional(readOnly = true)
    public Optional<String> getSpriteById(Long id) {
        return pokemonRepository.findSpriteById(id);
    }

    // Obtener Pokémon por habilidad
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemonByAbility(String abilityName) {
        return pokemonRepository.findByAbilityName(abilityName);
    }
}