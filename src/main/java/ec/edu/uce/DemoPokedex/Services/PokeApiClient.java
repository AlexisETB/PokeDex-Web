package ec.edu.uce.DemoPokedex.Services;

import ec.edu.uce.DemoPokedex.Model.Pokemon;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class PokeApiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public PokeApiClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    // Método para obtener un Pokémon por ID o nombre
    public Pokemon getPokemon(String idOrName) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "pokemon/" + idOrName).toUriString();
        try {
            String response = restTemplate.getForObject(url, String.class);
            return objectMapper.readValue(response, Pokemon.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Pokémon data: " + e.getMessage(), e);
        }
    }

    // Método para obtener la cadena de evolución por ID
    public String getEvolutionChain(int chainId) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + "evolution-chain/" + chainId).toUriString();
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching Evolution Chain: " + e.getMessage(), e);
        }
    }


}
