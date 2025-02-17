package ec.edu.uce.DemoPokedex.ApiService;

import ec.edu.uce.DemoPokedex.Model.*;
import ec.edu.uce.DemoPokedex.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class PokeService {
    @Autowired
    private PokeApiClient pokeApiClient;

    @Autowired
    private PokemonRepository pokemonRepository;

    // Metodo para guardar todos los Pokémon desde la API
    public Mono<Void> saveAllPokemon() {
        final int[] totalSaved = {0};
        return pokeApiClient.getAllPokemon()
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(pokemon ->
                        Mono.fromRunnable(()-> {pokemonRepository.save(pokemon); totalSaved[0]++;})
                                .subscribeOn(Schedulers.boundedElastic()))
                .sequential().then().doOnSubscribe(s -> System.out.println("Iniciando Guardado de Pokémon..."))
                .doOnSuccess(v -> System.out.println("Guardado de Pokémon completado. Total guardados: " + totalSaved[0]))
                .doOnEach(e -> {
                    if (e.getThrowable() != null){
                        System.err.println("Error durante el guardado de Pokémon: " + e.getThrowable().getMessage());
                    }
                });
    }

    // Metodo para guardar todas las evoluciones desde la API
    public Mono<Void> saveAllEvolutions() {
        return Mono.fromCallable(() -> pokemonRepository.findAll())
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(pokemon ->
                        pokeApiClient.getEvolutionChainUrl(pokemon.getId())
                                .flatMap(pokeApiClient::getEvolutionIdsByUrl)
                                .flatMap(evolutionIds -> {
                                    pokemon.setEvolutions(evolutionIds);
                                    return Mono.fromRunnable(() -> pokemonRepository.save(pokemon));
                                })
                                .onErrorResume(e -> {
                                    System.err.println("Error procesando evoluciones para: " + pokemon.getName() + ": " + e.getMessage());
                                    return Mono.empty();
                                })
                )
                .sequential()
                .then()
                .doOnSubscribe(s -> System.out.println("Sincronizando evoluciones..."))
                .doOnSuccess(v -> System.out.println("Sincronización de evoluciones completada"));
    }

    // Metodo para guardar todos los datos
    public Mono<Void> saveAllData() {
        final long[] startTime = new long[1]; // Contenedor para el tiempo de inicio

        return Mono.delay(Duration.ofMillis(100)) // Pequeño delay inicial
                .doOnSubscribe(s -> {
                    startTime[0] = System.currentTimeMillis(); // Captura el tiempo de inicio
                    System.out.println("Iniciando sincronización completa...");
                })
                .then(saveAllPokemon())
                .then(saveAllEvolutions())
                .doFinally(signal -> {
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime[0];
                    System.out.println("Sincronización completa finalizada.");
                    System.out.println("Tiempo total: " + (duration / 1000) + " s");
                })
                .doOnError(e -> System.err.println("Error general: " + e.getMessage()));
    }

}