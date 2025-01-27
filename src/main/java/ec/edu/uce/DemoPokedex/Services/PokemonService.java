package ec.edu.uce.DemoPokedex.Services;

import ec.edu.uce.DemoPokedex.Model.Pokemon;
import ec.edu.uce.DemoPokedex.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private AbilityRepository abilityRepository;

    @Transactional(readOnly = true)
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    // Obtener Pokémon por ID
    @Transactional(readOnly = true)
    public Optional<Pokemon> getPokemonById(Long id) {
        return pokemonRepository.findById(id);
    }

    // Obtener Pokémon por nombre
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemonByName(String name) {
        return pokemonRepository.findAll().stream()
                .filter(pokemon -> pokemon.getName().equalsIgnoreCase(name))
                .toList();
    }

    // Obtener Pokémon por tipo
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemonByType(String typeName) {
        return pokemonRepository.findAll().stream()
                .filter(pokemon -> pokemon.getTypes().stream()
                        .anyMatch(type -> type.getName().equalsIgnoreCase(typeName)))
                .toList();
    }

    // Obtener Pokémon por habilidad
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemonByAbility(String abilityName) {
        return pokemonRepository.findAll().stream()
                .filter(pokemon -> pokemon.getAbilities().stream()
                        .anyMatch(ability -> ability.getName().equalsIgnoreCase(abilityName)))
                .toList();
    }
}
