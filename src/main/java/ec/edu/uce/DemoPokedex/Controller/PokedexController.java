package ec.edu.uce.DemoPokedex.Controller;

import ec.edu.uce.DemoPokedex.ApiService.PokeService;
import ec.edu.uce.DemoPokedex.Model.Ability;
import ec.edu.uce.DemoPokedex.Model.Pokemon;
import ec.edu.uce.DemoPokedex.Model.Type;
import ec.edu.uce.DemoPokedex.Services.PokemonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ec.edu.uce.DemoPokedex.DemoPokedexApplication.context;

@Controller
public class PokedexController {

    @Autowired
    private PokemonService pokemonService;

    @Autowired
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
    private ComboBox<String> habilidad;

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
    private void buscarPorNombre(ActionEvent event)  {
        String nombre = escNombrePokemon.getText().trim();

        if (nombre.isEmpty()) {
            mostrarMensaje("Por favor, ingrese un nombre.");
            return;
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarMensaje("El nombre no debe contener números ni caracteres especiales.");
            return;
        }

        List<Pokemon> pokemons = pokemonService.getPokemonByName(nombre);

        if (!pokemons.isEmpty()) {
            Pokemon pokemon = pokemons.get(0); // Mostramos el primero encontrado
            mostrarPokemonData(pokemon);
        } else {
            mostrarMensaje("No se encontró ningún Pokémon con el nombre: " + nombre);
        }

    }

    @FXML
    private void buscarPorNumero(ActionEvent event)  {
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
    private void cargarDatos(ActionEvent event)  {
        try {
            pokeService.saveAllData();
            mostrarMensaje("Datos cargados exitosamente desde la API.");

            //mostrarTodosLosPokemon();
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

        if (pokemon.getStats() != null && pokemon.getStats().size() >= 6) {
            ps.setText(String.valueOf(pokemon.getStats().get(0).getBaseStat()));
            ataque.setText(String.valueOf(pokemon.getStats().get(1).getBaseStat()));
            defensa.setText(String.valueOf(pokemon.getStats().get(2).getBaseStat()));
            ataqueEsp.setText(String.valueOf(pokemon.getStats().get(3).getBaseStat()));
            DefensaEsp.setText(String.valueOf(pokemon.getStats().get(4).getBaseStat()));
            Velocidad.setText(String.valueOf(pokemon.getStats().get(5).getBaseStat()));
        } else {
            ps.setText("-");
            ataque.setText("-");
            defensa.setText("-");
            ataqueEsp.setText("-");
            DefensaEsp.setText("-");
            Velocidad.setText("-");
        }

        // Altura y peso
        altura.setText(String.valueOf(pokemon.getHeight() / 10.0) + "m");
        peso.setText(String.valueOf(pokemon.getWeight() / 10.0) + "kg");

        List<Type> types = pokemon.getTypes();
        if (types != null && !types.isEmpty()) {
            // Concatenar nombres de los tipos separados por comas
            String tiposConcatenados = types.stream()
                    .map(Type::getName) // Obtener el nombre de cada tipo
                    .collect(Collectors.joining(", ")); // Unirlos con comas
            tipo.setText(tiposConcatenados);
        } else {
            tipo.setText("N/A"); // Si no hay tipos, mostrar "N/A"
        }

        // Llenar el ComboBox de habilidades
        List<Ability> abilities = pokemon.getAbilities();
        if (abilities != null && !abilities.isEmpty()) {
            // Obtener los nombres de las habilidades y añadirlos al ComboBox
            habilidad.getItems().clear(); // Limpiar el ComboBox
            abilities.forEach(ability -> habilidad.getItems().add(ability.getName()));
            // Seleccionar la primera habilidad como predeterminada
            habilidad.getSelectionModel().selectFirst();
        } else {
            habilidad.getItems().clear();
            habilidad.getItems().add("Sin habilidades");
            habilidad.getSelectionModel().selectFirst();
        }


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
    public void mostrarTodosLosPokemon() {
        // Obtener la lista de Pokémon ordenada por ID
        List<Pokemon> listaPokemon = pokemonService.getAllPokemon();

        // Limpiar el GridPane antes de agregar nuevas tarjetas
        gridPanePokemon.getChildren().clear();

        int columnas = 3; // Número de columnas
        int fila = 0;
        int columna = 0;

        try {
            for (Pokemon pokemon : listaPokemon) {
                // Cargar el archivo FXML de la tarjeta de Pokémon
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/pokemon.fxml"));
                loader.setControllerFactory(context::getBean); // Configura Spring para manejar la inyección
                AnchorPane pokemonCard = loader.load(); // Carga solo una vez

                // Obtener el controlador de la tarjeta y pasar los datos del Pokémon
                PokemonController pokemonController = loader.getController();
                pokemonController.mostrarPokemonData(pokemon);

                // Agregar la tarjeta al GridPane
                gridPanePokemon.add(pokemonCard, columna, fila);

                // Controlar la disposición de columnas y filas
                columna++;
                if (columna == columnas) {
                    columna = 0;
                    fila++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("Error al cargar las tarjetas de Pokémon.");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}