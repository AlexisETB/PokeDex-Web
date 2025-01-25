package ec.edu.uce.DemoPokedex.Services;

import com.fasterxml.jackson.databind.JsonNode;
import ec.edu.uce.DemoPokedex.Model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
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

//     Metodo para obtener un Pokémon por ID o nombre
    public Pokemon getPokemon(String idOrName) {
        try {
            String url = BASE_URL + "pokemon/" + idOrName;
            String response = restTemplate.getForObject(url, String.class);

            JsonNode root = objectMapper.readTree(response);

            // Mapeo de los campos necesarios
            Pokemon pokemon = new Pokemon();
            pokemon.setId(root.get("id").asLong());
            pokemon.setName(root.get("name").asText());
            pokemon.setWeight(root.get("weight").asDouble());
            pokemon.setHeight(root.get("height").asDouble());
            pokemon.setBaseExperience(root.get("base_experience").asInt());

            // Habilidades
            List<Ability> abilities = new ArrayList<>();
            for (JsonNode abilityNode : root.get("abilities")) {
                Ability ability = new Ability();
                ability.setName(abilityNode.get("ability").get("name").asText());
                ability.setHidden(abilityNode.get("is_hidden").asBoolean());
                ability.setSlot(abilityNode.get("slot").asInt());
                abilities.add(ability);
            }
            pokemon.setAbilities(abilities);

            // Movimientos
            List<Move> moves = new ArrayList<>();
            for (JsonNode moveNode : root.get("moves")) {
                Move move = new Move();
                move.setName(moveNode.get("move").get("name").asText());
                moves.add(move);
            }
            pokemon.setMoves(moves);

            // Tipos
            List<Type> types = new ArrayList<>();
            for (JsonNode typeNode : root.get("types")) {
                Type type = new Type();
                type.setName(typeNode.get("type").get("name").asText());
                type.setSlot(typeNode.get("slot").asInt());
                types.add(type);
            }
            pokemon.setTypes(types);

            // Estadísticas
            List<Stat> stats = new ArrayList<>();
            for (JsonNode statNode : root.get("stats")) {
                Stat stat = new Stat();
                stat.setName(statNode.get("stat").get("name").asText());
                stat.setBaseStat(statNode.get("base_stat").asInt());
                stat.setEffort(statNode.get("effort").asInt());
                stats.add(stat);
            }
            pokemon.setStats(stats);

            // Sprites
            Sprites sprite = new Sprites();
            sprite.setFrontDefault(root.get("sprites").get("front_default").asText());
            sprite.setFrontShiny(root.get("sprites").get("front_shiny").asText());
            pokemon.setSprites(sprite);

            // Retorno del objeto mapeado
            return pokemon;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Pokémon data: " + e.getMessage(), e);
        }
    }

    // Metodo para obtener la cadena de evolución por ID
    public List<Evolution> getEvolutionChain(int chainId) {
        try {
            // Llamada al endpoint de cadena de evolución
            String url = BASE_URL + "evolution-chain/" + chainId;
            String response = restTemplate.getForObject(url, String.class);

            // Parseo de la respuesta JSON
            JsonNode root = objectMapper.readTree(response);
            JsonNode chain = root.get("chain");

            // Procesar la cadena de evolución recursivamente
            List<Evolution> evolutions = new ArrayList<>();
            processEvolutionChain(chain, evolutions);

            return evolutions;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching Evolution Chain data: " + e.getMessage(), e);
        }
    }

    private void processEvolutionChain(JsonNode chainNode, List<Evolution> evolutions) {
        if (chainNode == null) return;

        Evolution evolution = new Evolution();
        evolution.setSpeciesName(chainNode.get("species").get("name").asText());

        if (chainNode.has("evolution_details") && chainNode.get("evolution_details").size() > 0) {
            JsonNode details = chainNode.get("evolution_details").get(0);
            evolution.setTrigger(details.get("trigger").get("name").asText());
            evolution.setMinLevel(details.has("min_level") ? details.get("min_level").asInt() : null);
        }

        evolutions.add(evolution);

        // Procesar las evoluciones subsecuentes
        for (JsonNode next : chainNode.get("evolves_to")) {
            processEvolutionChain(next, evolutions);
        }
    }


}
