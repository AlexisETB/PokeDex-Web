package ec.edu.uce.DemoPokedex.Controller;

import ec.edu.uce.DemoPokedex.ApiService.PokeService;
import ec.edu.uce.DemoPokedex.Model.Ability;
import ec.edu.uce.DemoPokedex.Model.Pokemon;
import ec.edu.uce.DemoPokedex.Model.Stat;
import ec.edu.uce.DemoPokedex.Model.Type;
import ec.edu.uce.DemoPokedex.Services.PokemonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.awt.*;
import javafx.geometry.Insets;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private void buscarPorNombre(ActionEvent event) {
        String nombre = escNombrePokemon.getText().trim();

        Optional.ofNullable(nombre)
                .filter(n -> !n.isEmpty())
                .ifPresentOrElse(
                        n -> {
                            if (!n.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                                mostrarMensaje("El nombre no debe contener números ni caracteres especiales.");
                            } else {
                                pokemonService.getPokemonByName(n)
                                        .stream()
                                        .findFirst()
                                        .ifPresentOrElse(
                                                this::mostrarPokemonData,
                                                () -> mostrarMensaje("No se encontró ningún Pokémon con el nombre: " + n)
                                        );
                            }
                        },
                        () -> mostrarMensaje("Por favor, ingrese un nombre.")
                );
    }

    @FXML
    private void buscarPorNumero(ActionEvent event) {
        Optional.ofNullable(escNumeroPokemon.getText()) // Convertir el texto en un Optional
                .map(String::trim) // Eliminar espacios en blanco
                .filter(text -> !text.isEmpty()) // Filtrar si el texto no está vacío
                .flatMap(text -> { // Convertir el texto a Long y buscar el Pokémon
                    try {
                        Long id = Long.parseLong(text);
                        return pokemonService.getPokemonById(id);
                    } catch (NumberFormatException e) {
                        mostrarMensaje("El ID ingresado no es válido.");
                        return Optional.empty();
                    }
                })
                .ifPresentOrElse(
                        this::mostrarPokemonData, // Si el Pokémon está presente, mostrar sus datos
                        () -> mostrarMensaje("No se encontró ningún Pokémon con el ID ingresado.") // Si no, mostrar mensaje de error
                );
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
        limpiarDatosEvoluciones();
        List<Long> evolucionesIds = pokemonService.getEvolutionsById(pokemonId);

        if (evolucionesIds.isEmpty()) {
            mostrarMensaje("Este Pokémon no tiene evoluciones.");
            return;
        }

        // Usamos un Stream sobre la lista de IDs
        evolucionesIds.stream()
                .map(id -> pokemonService.getPokemonById(id))
                .forEach(optionalPokemon -> {
                    int index = evolucionesIds.indexOf(optionalPokemon.map(Pokemon::getId).orElse(null)) + 1;
                    optionalPokemon.ifPresentOrElse(
                            pokemon -> mostrarEvolucionData(index, pokemon),
                            () -> mostrarEvolucionData(index, null)
                    );
                });
    }

    private void mostrarPokemonData(Pokemon pokemon) {
        // 1. Mostrar nombre y número del Pokémon
        nombreNumeroPokemon.setText(pokemon.getName() + " (#" + pokemon.getId() + ")");

        // 2. Mostrar estadísticas del Pokémon
        mostrarEstadisticas(pokemon.getStats());

        // 3. Mostrar altura y peso
        altura.setText(String.format("%.1fm", pokemon.getHeight() / 10.0));
        peso.setText(String.format("%.1fkg", pokemon.getWeight() / 10.0));

        // 4. Mostrar tipos del Pokémon
        tipo.setText(Optional.ofNullable(pokemon.getTypes())
                .filter(types -> !types.isEmpty())
                .map(types -> types.stream()
                        .map(Type::getName)
                        .collect(Collectors.joining(", ")))
                .orElse("N/A"));

        // 5. Llenar el ComboBox de habilidades
        Optional.ofNullable(pokemon.getAbilities())
                .ifPresentOrElse(
                        abilities -> {
                            habilidad.getItems().clear();
                            abilities.stream()
                                    .map(Ability::getName)
                                    .forEach(habilidad.getItems()::add);
                            habilidad.getSelectionModel().selectFirst();
                        },
                        () -> {
                            habilidad.getItems().clear();
                            habilidad.getItems().add("Sin habilidades");
                            habilidad.getSelectionModel().selectFirst();
                        });

        // 6. Cargar el sprite del Pokémon
        pokemonService.getSpriteById(pokemon.getId())
                .ifPresent(spriteUrl -> imagenPokemon.setImage(new Image(spriteUrl)));

        // 7. Mostrar evoluciones del Pokémon
        mostrarEvoluciones(pokemon.getId());
    }

    private void mostrarEstadisticas(List<Stat> stats) {
        Map<Integer, Label> estadisticasMap = Map.of(
                0, ps,
                1, ataque,
                2, defensa,
                3, ataqueEsp,
                4, DefensaEsp,
                5, Velocidad
        );

        // Si las estadísticas son válidas, las mostramos; de lo contrario, mostramos "-"
        IntStream.range(0, estadisticasMap.size())
                .forEach(index -> estadisticasMap.get(index).setText(
                        Optional.ofNullable(stats)
                                .filter(s -> s.size() > index)
                                .map(s -> String.valueOf(s.get(index).getBaseStat()))
                                .orElse("-")
                ));
    }

    private void mostrarEvolucionData(int index, Pokemon evolucion) {
        // Definimos arreglos para los componentes de la interfaz
        Label[] nombreEvoLabels = {nombreEvo1, nombreEvo2, nombreEvo3};
        ImageView[] imagenEvoViews = {imagenEvo1, imagenEvo2, imagenEvo3};

        // Validamos que el índice esté dentro del rango válido
        if (index < 1 || index > 3) {
            throw new IllegalArgumentException("Índice de evolución no válido: " + index);
        }

        // Obtenemos los componentes correspondientes al índice
        Label nombreLabel = nombreEvoLabels[index - 1];
        ImageView imagenView = imagenEvoViews[index - 1];

        // Actualizamos los componentes según si la evolución es nula o no
        if (evolucion == null) {
            nombreLabel.setText("N/A");
            imagenView.setImage(null);
        } else {
            nombreLabel.setText(evolucion.getName());
            imagenView.setImage(new Image(evolucion.getSprites().getFrontDefault()));
        }
    }

    private void limpiarDatosEvoluciones() {
        nombreEvo1.setText("");
        nombreEvo2.setText("");
        nombreEvo3.setText("");

        imagenEvo1.setImage(null);
        imagenEvo2.setImage(null);
        imagenEvo3.setImage(null);
    }


    private void mostrarTodosLosPokemon() {
        List<Pokemon> pokemons = pokemonService.getAllPokemon().stream()
                .sorted(Comparator.comparing(Pokemon::getId))
                .collect(Collectors.toList());

        VBox contenedorTarjetas = new VBox();
        contenedorTarjetas.setSpacing(10);
        contenedorTarjetas.setPadding(new Insets(10));

        for (Pokemon pokemon : pokemons) {
            AnchorPane tarjeta = cargarTarjetaPokemon();
            if (tarjeta != null) {
                actualizarTarjetaPokemon(tarjeta, pokemon);
                contenedorTarjetas.getChildren().add(tarjeta);
            }
        }

        scrollPanePokemon.setContent(contenedorTarjetas);
    }

    private AnchorPane cargarTarjetaPokemon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pokemon.fxml"));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void actualizarTarjetaPokemon(AnchorPane tarjeta, Pokemon pokemon) {
        ImageView imagenPokemonCard = (ImageView) tarjeta.lookup("#imagenPokemonCard");
        Label numeroPokemon = (Label) tarjeta.lookup("#numeroPokemon");
        Label nombrePokemonCard = (Label) tarjeta.lookup("#nombrePokemonCard");
        Label tipoPokemonCard = (Label) tarjeta.lookup("#tipoPokemonCard");

        numeroPokemon.setText("Nro " + String.format("%03d", pokemon.getId()));
        nombrePokemonCard.setText(pokemon.getName());

        String tiposConcatenados = pokemon.getTypes().stream()
                .map(Type::getName)
                .collect(Collectors.joining(", "));
        tipoPokemonCard.setText(tiposConcatenados);

        String spriteUrl = pokemon.getSprites().getFrontDefault();
        if (spriteUrl != null) {
            imagenPokemonCard.setImage(new Image(spriteUrl));
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}