package ec.edu.uce.DemoPokedex.ApiService;

import ec.edu.uce.DemoPokedex.Model.*;
import ec.edu.uce.DemoPokedex.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


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


    // Metodo para guardar todos los Pokémon desde la API
    public void saveAllPokemon() {
        try {
            System.out.println("Iniciando Guardado de Pokémon...");
            List<Pokemon> pokemons = pokeApiClient.getAllPokemon(); // Obtiene todos los Pokémon desde la API

            for (Pokemon pokemon : pokemons) {
                // Las habilidades y tipos ya vienen como listas de cadenas, no requieren procesamiento adicional
                pokemonRepository.save(pokemon);
            }

            System.out.println("Guardado de Pokémon completado. Total guardados: " + pokemons.size());
        } catch (Exception e) {
            System.err.println("Error durante el guardado de Pokémon: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    // Metodo para guardar todas las evoluciones desde la API
//    public void saveAllEvolutions() {
//        try {
//            System.out.println("Iniciando sincronización de evoluciones...");
//            List<Pokemon> pokemons = pokemonRepository.findAll(); // Obtener todos los Pokémon existentes
//
//            for (Pokemon pokemon : pokemons) {
//                try {
//                    List<String> evolutionIds = pokeApiClient.getEvolutionIdsByPokemonId(pokemon.getId());
//                    pokemon.setEvolutionIds(evolutionIds);
//                    pokemonRepository.save(pokemon);
//                } catch (Exception e) {
//                    System.err.println("Error al procesar evoluciones para Pokémon: " + pokemon.getName());
//                    e.printStackTrace();
//                }
//            }
//
//            System.out.println("Sincronización de evoluciones completada.");
//        } catch (Exception e) {
//            System.err.println("Error durante la sincronización de evoluciones: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    // Metodo para guardar todo (Pokemon + Evoluciones)
//    public void saveAllData() {
//        System.out.println("Iniciando sincronización completa de datos...");
//        saveAllPokemon();
//        saveAllEvolutions();
//        System.out.println("Sincronización completa finalizada.");
//    }
}