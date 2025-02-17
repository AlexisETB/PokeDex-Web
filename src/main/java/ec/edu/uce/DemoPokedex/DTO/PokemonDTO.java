package ec.edu.uce.DemoPokedex.DTO;

import ec.edu.uce.DemoPokedex.Model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PokemonDTO {

    private Long id;
    private String name;
    private double height;
    private double weight;
    private List<Long> evolutions;
    private Set<Ability> abilities = new HashSet<>();
    private List<Stat> stats;
    private Set<Type> types = new HashSet<>();
    private Sprites sprites;

    public Pokemon toPokemon(PokemonDTO pokemonDTO) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(pokemonDTO.getId());
        pokemon.setName(pokemonDTO.getName());
        pokemon.setHeight(pokemonDTO.getHeight());
        pokemon.setWeight(pokemonDTO.getWeight());
        pokemon.setEvolutions(pokemonDTO.getEvolutions());
        pokemon.setStats(pokemonDTO.getStats());
        pokemon.setTypes(pokemonDTO.getTypes());
        pokemon.setSprites(pokemonDTO.getSprites());
        return pokemon;
    }

    public PokemonDTO toPokemonDTO(Pokemon pokemon) {
        PokemonDTO pokemonDTO = new PokemonDTO();
        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setHeight(pokemon.getHeight());
        pokemonDTO.setWeight(pokemon.getWeight());
        pokemonDTO.setEvolutions(pokemon.getEvolutions());
        pokemonDTO.setStats(pokemon.getStats());
        pokemonDTO.setTypes(pokemon.getTypes());
        pokemonDTO.setSprites(pokemon.getSprites());
        return pokemonDTO;
    }


}
