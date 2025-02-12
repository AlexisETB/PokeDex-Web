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
import java.util.Optional;
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

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private PokeService pokeService;

    @FXML
    public void cargarDatosPokemon() {
        // Ejemplo: Cargar un Pokémon por ID
        Long pokemonId = 25L; // ID de Pikachu (puedes obtenerlo dinámicamente)
        Optional<Pokemon> pokemonOptional = pokemonService.getPokemonById(pokemonId);

        // Si el Pokémon existe, mostrar sus datos
        pokemonOptional.ifPresentOrElse(
                this::mostrarPokemonData, // Mostrar datos si el Pokémon existe
                () -> mostrarMensaje("No se encontró el Pokémon con ID: " + pokemonId) // Mensaje si no existe
        );
    }


    protected void mostrarPokemonData(Pokemon pokemon) {
        // Mostrar nombre y número del Pokémon
        nombrePokemonCard.setText(pokemon.getName());
        numeroPokemon.setText("Nro " + pokemon.getId());

        // Mostrar tipos del Pokémon
        tipoPokemonCard.setText(
                Optional.ofNullable(pokemon.getTypes())
                        .filter(types -> !types.isEmpty())
                        .map(types -> types.stream()
                                .map(Type::getName)
                                .collect(Collectors.joining(", ")))
                        .orElse("N/A") // Si no hay tipos, mostrar "N/A"
        );

        // Cargar la imagen del Pokémon
        pokemonService.getSpriteById(pokemon.getId())
                .ifPresent(spriteUrl -> imagenPokemonCard.setImage(new Image(spriteUrl)));
    }


    private void mostrarMensaje(String mensaje) {
        // Aquí puedes implementar la lógica para mostrar un mensaje en la interfaz
        System.out.println(mensaje);
    }


}
