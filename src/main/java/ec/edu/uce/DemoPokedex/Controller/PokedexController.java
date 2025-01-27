package ec.edu.uce.DemoPokedex.Controller;

import ec.edu.uce.DemoPokedex.ApiService.PokeService;
import ec.edu.uce.DemoPokedex.Model.Pokemon;
import ec.edu.uce.DemoPokedex.Services.PokemonService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;

public class PokedexController {

    private PokemonService pokemonService;

    private PokeService pokeService;

    @FXML
    private Label welcomeText;

    @FXML
    private Label DefensaEsp;

    @FXML
    private ComboBox<?> FiltrarHabilidad;

    @FXML
    private Label Velocidad;

    @FXML
    private Label altura;

    @FXML
    private Label ataque;

    @FXML
    private Label ataqueEsp;

    @FXML
    private Button buscarNombrePokemon;

    @FXML
    private Button buscarNumeroPokemon;

    @FXML
    private Button cargarDatosPokeApi;

    @FXML
    private Label categoria;

    @FXML
    private Label defensa;

    @FXML
    private Label descripcionPokemon;

    @FXML
    private TextField escNombrePokemon;

    @FXML
    private TextField escNumeroPokemon;

    @FXML
    private ComboBox<?> filtrarTipo;

    @FXML
    private GridPane gridPanePokemon;

    @FXML
    private ComboBox<?> habilidad;

    @FXML
    private ImageView imagenEvo1;

    @FXML
    private ImageView imagenEvo2;

    @FXML
    private ImageView imagenEvo3;

    @FXML
    private ImageView imagenPokemon;

    @FXML
    private Label nombreEvo1;

    @FXML
    private Label nombreEvo2;

    @FXML
    private Label nombreEvo3;

    @FXML
    private Label nombreNumeroPokemon;

    @FXML
    private Label peso;

    @FXML
    private Label ps;

    @FXML
    private ScrollPane scrollPanePokemon;

    @FXML
    private Label tipo;

    @FXML
    private Label tipoEvo1;

    @FXML
    private Label tipoEvo2;

    @FXML
    private Label tipoEvo3;

    @FXML
    private void buscarPorNombre()  {
        String nombre = escNombrePokemon.getText();
        List<Pokemon> pokemons = pokemonService.getPokemonByName(nombre);

        if (!pokemons.isEmpty()) {
            Pokemon pokemon = pokemons.get(0); // Mostramos el primero encontrado
            mostrarPokemonData(pokemon);
        } else {
            mostrarMensaje("No se encontró ningún Pokémon con el nombre: " + nombre);
        }
    }

    @FXML
    private void buscarPorNumero()  {
        try {
            Long id = Long.parseLong(escNumeroPokemon.getText());
            Optional<Pokemon> optionalPokemon = pokemonService.getPokemonById(id);

            if (optionalPokemon.isPresent()) {
                mostrarPokemonData(optionalPokemon.get());
            } else {
                mostrarMensaje("No se encontró ningún Pokémon con el ID: " + id);
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("El ID ingresado no es válido.");
        }
    }

    @FXML
    private void cargarDatos()  {
        try {
            pokeService.saveAllData();
            mostrarMensaje("Datos cargados exitosamente desde la API.");
        } catch (Exception e) {
            mostrarMensaje("Ocurrió un error al cargar los datos: " + e.getMessage());
        }
    }

    @FXML
    private void filtrarPorTipo()  {
        System.out.println("Filtrando por tipo");
    }

    @FXML
    private void filtrarPorHabilidad()  {
        System.out.println("Filtrando por habilidad");
    }

    @FXML
    private void mostrarEvoluciones(Long pokemonId) {
        List<Long> evolucionesIds = pokemonService.getEvolutionsById(pokemonId);

        // Verificamos las evoluciones y mostramos información
        if (evolucionesIds.isEmpty()) {
            mostrarMensaje("Este Pokémon no tiene evoluciones.");
            return;
        }

        for (int i = 0; i < evolucionesIds.size(); i++) {
            Optional<Pokemon> evolucion = pokemonService.getPokemonById(evolucionesIds.get(i));
            if (evolucion.isPresent()) {
                mostrarEvolucionData(i + 1, evolucion.get());
            } else {
                mostrarEvolucionData(i + 1, null); // Si no existe, mostramos vacío
            }
        }
    }
    private void mostrarPokemonData(Pokemon pokemon) {
        nombreNumeroPokemon.setText(pokemon.getName() + " (#" + pokemon.getId() + ")");
        ps.setText(String.valueOf(pokemon.getBase_experience()));
        altura.setText(String.valueOf(pokemon.getHeight()));
        peso.setText(String.valueOf(pokemon.getWeight()));
        categoria.setText("Categoría Pokémon");

        // Cargamos el sprite
        pokemonService.getSpriteById(pokemon.getId()).ifPresent(spriteUrl -> {

            imagenPokemon.setImage(new Image(spriteUrl));
        });

        // Mostramos evoluciones
        mostrarEvoluciones(pokemon.getId());
    }

    private void mostrarEvolucionData(int index, Pokemon evolucion) {
        if (evolucion == null) {
            switch (index) {
                case 1 -> {
                    nombreEvo1.setText("N/A");
                    imagenEvo1.setImage(null);
                }
                case 2 -> {
                    nombreEvo2.setText("N/A");
                    imagenEvo2.setImage(null);
                }
                case 3 -> {
                    nombreEvo3.setText("N/A");
                    imagenEvo3.setImage(null);
                }
            }
        } else {
            switch (index) {
                case 1 -> {
                    nombreEvo1.setText(evolucion.getName());
                    imagenEvo1.setImage(new Image(evolucion.getSprites().getFrontDefault()));
                }
                case 2 -> {
                    nombreEvo2.setText(evolucion.getName());
                    imagenEvo2.setImage(new Image(evolucion.getSprites().getFrontDefault()));
                }
                case 3 -> {
                    nombreEvo3.setText(evolucion.getName());
                    imagenEvo3.setImage(new Image(evolucion.getSprites().getFrontDefault()));
                }
            }
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}