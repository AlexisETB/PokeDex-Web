package ec.edu.uce.DemoPokedex.Services;

import ec.edu.uce.DemoPokedex.Model.Pokemon;
import ec.edu.uce.DemoPokedex.Model.Type;
import ec.edu.uce.DemoPokedex.Repository.AbilityRepository;
import ec.edu.uce.DemoPokedex.Repository.PokemonRepository;
import ec.edu.uce.DemoPokedex.Repository.TypeRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    // Obtener todos los Pokémon
    @Transactional(readOnly = true)
    public Page<Pokemon> getAllPokemon(Pageable pageable) {
        return pokemonRepository.findAllWithTypes(pageable);
    }
    // Obtener Pokémon por ID
    @Transactional(readOnly = true)
    public Optional<Pokemon> getPokemonById(Long id) {
        // Primera consulta: Carga stats y types
        Optional<Pokemon> pokemonOpt = pokemonRepository.findByIdWithStatsAndTypes(id);

        // Segunda consulta: Carga abilities si el Pokémon existe
        pokemonOpt.ifPresent(pokemon ->
                pokemonRepository.findByIdWithAbilities(id).ifPresent(p ->
                        pokemon.setAbilities(p.getAbilities())
                )
        );

        return pokemonOpt;
    }

    // Obtener Pokémon por nombre
    @Transactional(readOnly = true)
    public List<Pokemon> getPokemonByName(String name) {
        List<Pokemon> resultados = pokemonRepository.findByNameWithStatsAndTypes(name);

        // Cargar abilities para cada Pokémon encontrado
        resultados.forEach(pokemon ->
                pokemonRepository.findByIdWithAbilities(pokemon.getId()).ifPresent(p ->
                        pokemon.setAbilities(p.getAbilities())
                ));

        return resultados;
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

    //Obtener todos los tipos
    @Transactional
    public List<String> getAllTypes(){
        return typeRepository.findAllDistinctNames();
    }

    //Obtener todas las Habilidades
    @Transactional
    public List<String> getAllAbilities(){
        return abilityRepository.findAllDistinctNames();
    }

}