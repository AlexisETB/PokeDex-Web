package ec.edu.uce.DemoPokedex.Services;

import ec.edu.uce.DemoPokedex.Entities.Pokemon;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PokeService {
    private final String POKE_API_URL = "https://pokeapi.co/api/v2/pokemon?limit=100";

    public List<Pokemon> getAllPokemons() {
        RestTemplate restTemplate = new RestTemplate();
        PokemonResponse response = restTemplate.getForObject(POKE_API_URL, PokemonResponse.class);
        return response.getResults();
    }
}