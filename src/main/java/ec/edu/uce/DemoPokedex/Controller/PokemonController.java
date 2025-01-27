package ec.edu.uce.DemoPokedex.Controller;

import ec.edu.uce.DemoPokedex.ApiService.PokeService;
import ec.edu.uce.DemoPokedex.Model.Ability;
import ec.edu.uce.DemoPokedex.Model.Pokemon;
import ec.edu.uce.DemoPokedex.Model.Type;
import ec.edu.uce.DemoPokedex.Services.PokemonService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PokemonController {

    @FXML
    private ImageView imagenPokemonCard;

    @FXML
    private Label nombrePokemonCard;

    @FXML
    private Label numeroPokemon;

    @FXML
    private Label tipoPokemonCard;

    @FXML
    public void cargarDatosPokemon() {
        // Cargar datos del pokemon
    }
    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private PokeService pokeService;

    protected void mostrarPokemonData(Pokemon pokemon) {
        nombrePokemonCard.setText(pokemon.getName() + " (#" + pokemon.getId() + ")");
        numeroPokemon.setText("Nro "+pokemon.getId());

//        List<Type> types = pokemon.getTypes();
//        if (types != null && !types.isEmpty()) {
//            // Concatenar nombres de los tipos separados por comas
//            String tiposConcatenados = types.stream()
//                    .map(Type::getName) // Obtener el nombre de cada tipo
//                    .collect(Collectors.joining(", ")); // Unirlos con comas
//            tipoPokemonCard.setText(tiposConcatenados);
//        } else {
//            tipoPokemonCard.setText("N/A"); // Si no hay tipos, mostrar "N/A"
//        }


        // Cargamos el sprite
        pokemonService.getSpriteById(pokemon.getId()).ifPresent(spriteUrl -> {

            imagenPokemonCard.setImage(new Image(spriteUrl));
        });
    }



}
