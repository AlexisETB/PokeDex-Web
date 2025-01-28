package ec.edu.uce.DemoPokedex.ApiService;

import com.fasterxml.jackson.databind.JsonNode;
import ec.edu.uce.DemoPokedex.Model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PokeApiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private final ExecutorService executor = Executors.newFixedThreadPool(15);

    public PokeApiClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public List<Pokemon> getAllPokemon() {
        List<Pokemon> pokemonList = new ArrayList<>();
        String url = BASE_URL + "pokemon?limit=1025&offset=0";


        try {
            // Obtener la lista inicial de Pokémon (IDs y nombres)
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            JsonNode results = root.get("results");

            // Procesar URLs en paralelo con CompletableFuture
            List<CompletableFuture<Pokemon>> futures = new ArrayList<>();
            for (JsonNode pokemonNode : results) {
                String pokemonUrl = pokemonNode.get("url").asText();
                CompletableFuture<Pokemon> future = CompletableFuture.supplyAsync(
                        () -> getPokemonByUrl(pokemonUrl),
                        executor
                );
                futures.add(future);
            }

            // Esperar a que todos los procesos terminen y recopilar los resultados
            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los datos de los Pokémon: " + e.getMessage(), e);
        }
        //return pokemonList;
    }

    public Pokemon getPokemonByUrl(String url) {
        try{
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            Pokemon pokemon = new Pokemon();
            pokemon.setId(root.get("id").asLong());
            pokemon.setName(root.get("name").asText());
            pokemon.setHeight(root.get("height").asDouble());
            pokemon.setWeight(root.get("weight").asDouble());
            pokemon.setBase_experience(root.get("base_experience").asInt());

            //abilities
            List<Ability> abilities = new ArrayList<>();
            for (JsonNode abilityNode : root.get("abilities")) {
                Ability ability = new Ability();
                ability.setName(abilityNode.get("ability").get("name").asText());
                abilities.add(ability);
            }
            pokemon.setAbilities(abilities);

            //types
            List<Type> types = new ArrayList<>();
            for (JsonNode typeNode : root.get("types")) {
                Type type = new Type();
                type.setName(typeNode.get("type").get("name").asText());
                types.add(type);
            }
            pokemon.setTypes(types);

            //stast
            List<Stat> stats = new ArrayList<>();
            for (JsonNode statNode : root.get("stats")) {
                Stat stat = new Stat();
                stat.setName(statNode.get("stat").get("name").asText());
                stat.setBaseStat(statNode.get("base_stat").asInt());
                stat.setEffort(statNode.get("effort").asInt());
                stats.add(stat);
            }
            pokemon.setStats(stats);

            //Sprites
            Sprites sprites = new Sprites();
            sprites.setFrontDefault(getSpriteValue(root, "front_default"));
            pokemon.setSprites(sprites);

            return pokemon;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

public String getEvolutionChainUrl(Long pokemonId) {
    try {
        String speciesUrl = BASE_URL + "pokemon-species/" + pokemonId + "/";
        String response = restTemplate.getForObject(speciesUrl, String.class);

        JsonNode root = objectMapper.readTree(response);
        return root.get("evolution_chain").get("url").asText();
    } catch (Exception e) {
        throw new RuntimeException("Error fetching evolution chain URL for Pokémon ID: " + pokemonId, e);
    }
}

    public List<Long> getEvolutionIdsByUrl(String url) {
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode chain = root.get("chain");

            List<Long> evolutionIds = new ArrayList<>();
            extractEvolutionIds(chain, evolutionIds);

            return evolutionIds;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching evolution IDs: " + e.getMessage(), e);
        }
    }

    private void extractEvolutionIds(JsonNode chainNode, List<Long> evolutionIds) {
        if (chainNode == null) return;

        String speciesUrl = chainNode.get("species").get("url").asText();
        Long speciesId = extractIdFromUrl(speciesUrl);
        if (speciesId != null) {
            evolutionIds.add(speciesId);
        }

        for (JsonNode next : chainNode.get("evolves_to")) {
            extractEvolutionIds(next, evolutionIds);
        }
    }

    private Long extractIdFromUrl(String url) {
        String[] parts = url.split("/");
        try {
            return Long.parseLong(parts[parts.length - 1]);
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
