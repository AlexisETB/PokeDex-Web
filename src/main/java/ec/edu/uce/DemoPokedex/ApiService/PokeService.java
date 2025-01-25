package ec.edu.uce.DemoPokedex.ApiService;

import ec.edu.uce.DemoPokedex.Model.*;
import ec.edu.uce.DemoPokedex.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PokeService {
    @Autowired
    private PokeApiClient pokeApiClient;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private EvolutionRepository evolutionRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private MoveRepository moveRepository;

    // Metodo para guardar todos los Pokémon desde la API
    public void saveAllPokemon() {
        try {
            System.out.println("Iniciando sincronización de Pokémon...");
            List<Pokemon> pokemons = pokeApiClient.getAllPokemon(); // Obtiene todos los Pokémon desde la API

            for (Pokemon pokemon : pokemons) {
                // Manejo de tipos
                List<Type> types = new ArrayList<>();
                for (Type type : pokemon.getTypes()) {
                    Optional<Type> existingType = typeRepository.findByName(type.getName());
                    types.add(existingType.orElseGet(() -> typeRepository.save(type)));
                }
                pokemon.setTypes(types);

                // Manejo de habilidades
                List<Ability> abilities = new ArrayList<>();
                for (Ability ability : pokemon.getAbilities()) {
                    Optional<Ability> existingAbility = abilityRepository.findByName(ability.getName());
                    abilities.add(existingAbility.orElseGet(() -> abilityRepository.save(ability)));
                }
                pokemon.setAbilities(abilities);

                // Manejo de movimientos
                List<Move> moves = new ArrayList<>();
                for (Move move : pokemon.getMoves()) {
                    Optional<Move> existingMove = moveRepository.findByName(move.getName());
                    moves.add(existingMove.orElseGet(() -> moveRepository.save(move)));
                }
                pokemon.setMoves(moves);

                // Guardar el Pokémon con referencias a las entidades relacionadas
                pokemonRepository.save(pokemon);
            }

            System.out.println("Sincronización de Pokémon completada. Total sincronizados: " + pokemons.size());
        } catch (Exception e) {
            System.err.println("Error durante la sincronización de Pokémon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo para guardar todas las evoluciones desde la API
    public void saveAllEvolutions() {
        try {
            System.out.println("Iniciando sincronización de evoluciones...");
            List<Evolution> evolutions = pokeApiClient.getAllEvolutions(); // Obtiene todas las cadenas de evolución desde la API

            // Guardar las evoluciones en la base de datos
            evolutionRepository.saveAll(evolutions);
            System.out.println("Sincronización de evoluciones completada. Total sincronizadas: " + evolutions.size());
        } catch (Exception e) {
            System.err.println("Error durante la sincronización de evoluciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo para guardar todo (Pokemon + Evoluciones)
    public void saveAllData() {
        System.out.println("Iniciando sincronización completa de datos...");
        saveAllPokemon();
        saveAllEvolutions();
        System.out.println("Sincronización completa finalizada.");
    }
}