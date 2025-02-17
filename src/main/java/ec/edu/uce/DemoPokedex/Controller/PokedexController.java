package ec.edu.uce.DemoPokedex.Controller;


import ec.edu.uce.DemoPokedex.ApiService.PokeService;
import ec.edu.uce.DemoPokedex.Model.Pokemon;
import ec.edu.uce.DemoPokedex.Services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PokedexController {

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private PokeService pokeService;

    @GetMapping("/Pokemon/{id}")
    public ResponseEntity<Pokemon> getPokemonById(@PathVariable Long id) {
        return pokemonService.getPokemonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/Pokemon/name/{name}")
    public ResponseEntity<Pokemon> getPokemonByName(@PathVariable String name) {
        List<Pokemon> resultados  = pokemonService.getPokemonByName(name);
        if (resultados == null || resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultados.get(0));
        }

    }

    @GetMapping("/Pokemon")
    public ResponseEntity<Page<Pokemon>> getPokemonList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String ability) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Pokemon> result;

        // Si se provee un nombre, se usa el endpoint de búsqueda por nombre.
        if (name != null && !name.isEmpty()) {
            List<Pokemon> list = pokemonService.getPokemonByName(name);
            if (list.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            // Se convierte la lista en una página (en este caso sin paginación real)
            result = new PageImpl<>(list, pageable, list.size());
        } else if (type != null && !type.isEmpty() && ability != null && !ability.isEmpty()) {
            // Filtrado por tipo y habilidad
            result = pokemonService.getPokemonByTypeAndAbility(type, ability, pageable);
        } else if (type != null && !type.isEmpty()) {
            // Filtrado solo por tipo
            result = pokemonService.getPokemonByType(type, pageable);
        } else if (ability != null && !ability.isEmpty()) {
            // Filtrado solo por habilidad
            result = pokemonService.getPokemonByAbility(ability, pageable);
        } else {
            // Sin filtros
            result = pokemonService.getAllPokemon(pageable);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/load")
    public ResponseEntity<Void> loadPokemonData() {
        pokeService.saveAllData().subscribe();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/Pokemon/evolutions/{id}")
    public ResponseEntity<List<Long>> getPokemonEvolutions(@PathVariable Long id) {
        List<Long> evolutions = pokemonService.getEvolutionsById(id);
        return ResponseEntity.ok(evolutions);
    }

    @GetMapping("/sprite/{id}")
    public ResponseEntity<String> getSpriteById(@PathVariable Long id) {
        Optional<String> spriteOpt = pokemonService.getSpriteById(id);
        return spriteOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllTypes() {
        List<String> types = pokemonService.getAllTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/abilities")
    public ResponseEntity<List<String>> getAllAbilities() {
        List<String> abilities = pokemonService.getAllAbilities();
        return ResponseEntity.ok(abilities);
    }

}
