package ec.edu.uce.DemoPokedex.ApiService;

import com.fasterxml.jackson.databind.JsonNode;
import ec.edu.uce.DemoPokedex.Model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


@Service
public class PokeApiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
   private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public PokeApiClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public List<Pokemon> getAllPokemon() {
        List<Pokemon> pokemonList = new ArrayList<>();
        String url = BASE_URL + "pokemon?limit=10&offset=0";


        try {
            // Obtener la lista inicial de Pokémon (IDs y nombres)
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            JsonNode results = root.get("results");

            // Iterar sobre cada Pokémon en la lista y obtener sus datos completos
            if (results != null) {
                for (JsonNode pokemonNode : results) {
                    String pokemonUrl = pokemonNode.get("url").asText();
                    Pokemon pokemon = getPokemonByUrl(pokemonUrl);
                    pokemonList.add(pokemon);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los datos de los Pokémon: " + e.getMessage(), e);
        }

        return pokemonList;
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

    public List<Long> getEvolutionChain(String evolutionChainUrl) {
        List<Long> evolutionIds = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(restTemplate.getForObject(evolutionChainUrl, String.class));
            JsonNode chain = root.get("chain");

            // Recorrer la cadena de evoluciones
            while (chain != null) {
                String speciesUrl = chain.get("species").get("url").asText();
                Long id = extractIdFromUrl(speciesUrl);
                evolutionIds.add(id);

                chain = chain.get("evolves_to").isEmpty() ? null : chain.get("evolves_to").get(0);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la cadena de evoluciones: " + e.getMessage(), e);
        }

        return evolutionIds;
    }

    /**
     * Extrae el ID de un Pokémon desde su URL.
     * @param url URL del Pokémon o especie.
     * @return ID del Pokémon.
     */
    private Long extractIdFromUrl(String url) {
        String[] parts = url.split("/");
        return Long.parseLong(parts[parts.length - 1]);
    }
    private String getSpriteValue(JsonNode root, String spriteKey) {
        return root.get("sprites").has(spriteKey) && !root.get("sprites").get(spriteKey).isNull()
                ? root.get("sprites").get(spriteKey).asText()
                : null;
    }


}
