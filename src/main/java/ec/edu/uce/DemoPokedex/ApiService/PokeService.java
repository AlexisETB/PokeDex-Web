package ec.edu.uce.DemoPokedex.ApiService;

import ec.edu.uce.DemoPokedex.Model.*;
import ec.edu.uce.DemoPokedex.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class PokeService {
    @Autowired
    private PokeApiClient pokeApiClient;

    @Autowired
    private PokemonRepository pokemonRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(15);

    // Metodo para guardar todos los Pokémon desde la API
    public void saveAllPokemon() {
        try {
            System.out.println("Iniciando Guardado de Pokémon...");
            List<Pokemon> pokemons = pokeApiClient.getAllPokemon().join(); // Obtiene todos los Pokémon desde la API

            // Guardar cada Pokémon en paralelo utilizando CompletableFuture
            List<CompletableFuture<Void>> futures = pokemons.stream()
                    .map(pokemon -> CompletableFuture.runAsync(() -> pokemonRepository.save(pokemon), executor))
                    .toList();

            // Esperar a que todos los procesos terminen
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            System.out.println("Guardado de Pokémon completado. Total guardados: " + pokemons.size());
        } catch (Exception e) {
            System.err.println("Error durante el guardado de Pokémon: " + e.getMessage());
            e.printStackTrace();

        }
    }

    // Metodo para guardar todas las evoluciones desde la API
    public void saveAllEvolutions() {
        try {
            System.out.println("Iniciando sincronización de evoluciones...");
            List<Pokemon> pokemons = pokemonRepository.findAll(); // Obtener todos los Pokémon existentes
            List<CompletableFuture<Void>> futures = pokemons.stream()
                    .map(pokemon -> CompletableFuture.runAsync(() -> {
                        try {
                            String evolutionChainUrl = pokeApiClient.getEvolutionChainUrl(pokemon.getId());
                            List<Long> evolutionIds = pokeApiClient.getEvolutionIdsByUrl(evolutionChainUrl);

                            // Relacionar las evoluciones encontradas
                            pokemon.setEvolutions(evolutionIds);
                            pokemonRepository.save(pokemon);
                        } catch (Exception e) {
                            System.err.println("Error al procesar evoluciones para Pokémon: " + pokemon.getName());
                            e.printStackTrace();
                        }
                    }, executor))
                    .toList();

            // Esperar a que todos los procesos terminen
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            System.out.println("Sincronización de evoluciones completada.");
        } catch (Exception e) {
            System.err.println("Error durante la sincronización de evoluciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Metodo para guardar todos los datos
    public void saveAllData() {
        long startTime = System.currentTimeMillis();
        System.out.println("Iniciando sincronización completa de datos...");
        saveAllPokemon();
        saveAllEvolutions();
        shutdownExecutor();
        long endTime = System.currentTimeMillis();
        System.out.println("Sincronización completa finalizada.");
        System.out.println("Tiempo total: " + (endTime - startTime)/1000 + " s");
    }
    //
    public void shutdownExecutor() {
        executor.shutdown();
    }
}