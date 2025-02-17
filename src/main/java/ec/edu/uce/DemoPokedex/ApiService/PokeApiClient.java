package ec.edu.uce.DemoPokedex.ApiService;

import com.fasterxml.jackson.databind.JsonNode;
import ec.edu.uce.DemoPokedex.Model.*;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;


@Service
public class PokeApiClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public PokeApiClient(WebClient.Builder webClientBuilder) {
       this.webClient = webClientBuilder.baseUrl(BASE_URL).codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)).build();
        this.objectMapper = new ObjectMapper();
    }

    public Flux<Pokemon> getAllPokemon() {
        return webClient.get()
                .uri("pokemon?limit=1025&offset=0")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMapMany(root -> {
                    JsonNode results = root.get("results");
                    List<JsonNode> pokemonNodes = new ArrayList<>();
                    results.forEach(pokemonNodes::add);
                    return Flux.fromIterable(pokemonNodes);
                })
                .flatMap(pokemonNode -> {
                    String pokemonUrl = pokemonNode.get("url").asText();
                    return getPokemonByUrl(pokemonUrl);
                });
    }

    public Mono<Pokemon> getPokemonByUrl(String url) {
        return webClient.get()
                .uri(url).retrieve().bodyToMono(String.class)
                .flatMap(response -> {
                    try{
                        JsonNode root = objectMapper.readTree(response);
                        return Mono.just(mapJsonToPokemon(root));
                    }catch(Exception e){
                        return Mono.error(e);
                    }
                });
    }

    // Mapea el JSON a un objeto Pokemon
    private Pokemon mapJsonToPokemon(JsonNode root) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(root.get("id").asLong());
        pokemon.setName(root.get("name").asText());
        pokemon.setHeight(root.get("height").asDouble());
        pokemon.setWeight(root.get("weight").asDouble());
        pokemon.setBase_experience(root.get("base_experience").asInt());

        // Habilidades
        Set<Ability> abilities = new HashSet<>();
        root.get("abilities").forEach(abilityNode -> {
            Ability ability = new Ability();
            ability.setName(abilityNode.get("ability").get("name").asText());
            abilities.add(ability);
        });
        pokemon.setAbilities(abilities);

        // Tipos
        Set<Type> types = new HashSet<>();
        root.get("types").forEach(typeNode -> {
            Type type = new Type();
            type.setName(typeNode.get("type").get("name").asText());
            types.add(type);
        });
        pokemon.setTypes(types);

        // Estadísticas
        List<Stat> stats = new ArrayList<>();
        root.get("stats").forEach(statNode -> {
            Stat stat = new Stat();
            stat.setName(statNode.get("stat").get("name").asText());
            stat.setBaseStat(statNode.get("base_stat").asInt());
            stat.setEffort(statNode.get("effort").asInt());
            stats.add(stat);
        });
        pokemon.setStats(stats);

        // Sprites
        Sprites sprites = new Sprites();
        sprites.setFrontDefault(getSpriteValue(root, "front_default"));
        pokemon.setSprites(sprites);

        return pokemon;
    }

public Mono<String> getEvolutionChainUrl(Long pokemonId) {
    return webClient.get()
            .uri("pokemon-species/{id}/", pokemonId)
            .retrieve()
            .bodyToMono(JsonNode.class)
            .map(root -> root.get("evolution_chain").get("url").asText())
            .onErrorMap(e -> new RuntimeException("Error obteniendo cadena de evolución: " + pokemonId, e));
}

    public Mono<List<Long>> getEvolutionIdsByUrl(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(root -> {
                    List<Long> evolutionIds = new ArrayList<>();
                    extractEvolutionIds(root.get("chain"), evolutionIds);
                    return Mono.just(evolutionIds);
                })
                .onErrorMap(e -> new RuntimeException("Error obteniendo evoluciones: " + e.getMessage(), e));
    }

    private void extractEvolutionIds(JsonNode chainNode, List<Long> evolutionIds) {
        if (chainNode == null) return;

        String speciesUrl = chainNode.get("species").get("url").asText();
        Long speciesId = extractIdFromUrl(speciesUrl);
        if (speciesId != null) {
            evolutionIds.add(speciesId);
        }

        chainNode.get("evolves_to").forEach(next -> extractEvolutionIds(next, evolutionIds));
    }

    private Long extractIdFromUrl(String url) {
        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1];
        if (lastPart.isEmpty() && parts.length >= 2) {
            lastPart = parts[parts.length - 2];
        }
        try {
            return Long.parseLong(lastPart);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    private String getSpriteValue(JsonNode root, String spriteKey) {
        return root.get("sprites").has(spriteKey) && !root.get("sprites").get(spriteKey).isNull()
                ? root.get("sprites").get(spriteKey).asText()
                : null;
    }


}
