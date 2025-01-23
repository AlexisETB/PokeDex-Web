package ec.edu.uce.DemoPokedex.Controller;

import ec.edu.uce.DemoPokedex.Entities.Pokemon;
import ec.edu.uce.DemoPokedex.Services.PokeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PokeController {
    private final PokeService pokeService;

    public PokeController(PokeService pokeService) {
        this.pokeService = pokeService;
    }

    @GetMapping("/api/pokemons")
    public List<Pokemon> getAllPokemons() {
        return pokeService.getAllPokemons();
    }
}