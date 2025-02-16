package ec.edu.uce.DemoPokedex.Controller;

import ec.edu.uce.DemoPokedex.ApiService.PokeService;
import ec.edu.uce.DemoPokedex.Model.Ability;
import ec.edu.uce.DemoPokedex.Model.Pokemon;
import ec.edu.uce.DemoPokedex.Model.Stat;
import ec.edu.uce.DemoPokedex.Model.Type;
import ec.edu.uce.DemoPokedex.Optim.SpriteCache;
import ec.edu.uce.DemoPokedex.Services.PokemonService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private ComboBox<String> filtrarHabilidad;

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
    private ComboBox<String> filtrarTipo;

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

    private int currentPage = 0;
    private boolean isLoading = false;
    private final ExecutorService backgroundExecutor = Executors.newFixedThreadPool(4);

    @FXML
    public void initialize() {
        configurarScrollInfinito();
    }


    private void cargarTiposEnComboBox() {
        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() {
                return pokemonService.getAllTypes();
            }
        };

        task.setOnSucceeded(e -> {
            List<String> tipos = task.getValue();
            Platform.runLater(() -> {
                filtrarTipo.getItems().clear();
                tipos.add(0, "All Types");
                ObservableList<String> tiposObservable = FXCollections.observableArrayList(tipos); // Crear una lista observable
                filtrarTipo.setItems(tiposObservable);
                filtrarTipo.getSelectionModel().selectFirst();
            });
        });

        task.setOnFailed(e -> {
            Platform.runLater(() -> mostrarMensaje("Error al cargar tipos: " + task.getException().getMessage()));
        });

        backgroundExecutor.execute(task);
    }

    private void cargarHabilidadesEnComboBox() {
        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() {
                return pokemonService.getAllAbilities();
            }
        };

        task.setOnSucceeded(e -> {
            List<String> habilidades = task.getValue();
            Platform.runLater(() -> {
                filtrarHabilidad.getItems().clear();
                habilidades.add(0, "All Abilities");
                ObservableList<String> habilidadesObservable = FXCollections.observableArrayList(habilidades); // Crear una lista observable
                filtrarHabilidad.setItems(habilidadesObservable);
                filtrarHabilidad.getSelectionModel().selectFirst();
            });
        });

        task.setOnFailed(e -> {
            Platform.runLater(() -> mostrarMensaje("Error al cargar habilidades: " + task.getException().getMessage()));
        });

        backgroundExecutor.execute(task);
    }

    private void configurarScrollInfinito() {
        scrollPanePokemon.vvalueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0.95 && !isLoading) {
                cargarPagina(currentPage + 1);
            }
        });
    }

    private void cargarPagina(int page) {
        if (isLoading) return;
        isLoading = true;

        Task<Page<Pokemon>> task = new Task<>() {
            @Override
            protected Page<Pokemon> call() {
                Pageable pageable = PageRequest.of(page, 30);

                String typeFilter = filtrarTipo.getSelectionModel().getSelectedItem();
                String abilityFilter = filtrarHabilidad.getSelectionModel().getSelectedItem();

                if(typeFilter == null || abilityFilter == null){
                    typeFilter = "All Types";
                    abilityFilter = "All Abilities";
                }
                    boolean filtrarPorTipo = !typeFilter.equals("All Types");
                    boolean filtrarPorHabilidad = !abilityFilter.equals("All Abilities");

                    if (filtrarPorTipo && filtrarPorHabilidad) {
                        return pokemonService.getPokemonByTypeAndAbility(typeFilter, abilityFilter, pageable);
                    } else if(filtrarPorTipo){
                        return pokemonService.getPokemonByType(typeFilter, pageable);
                    } else if (filtrarPorHabilidad) {
                        return pokemonService.getPokemonByAbility(abilityFilter, pageable);
                    }else {
                        return pokemonService.getAllPokemon(pageable);
                    }
            }
        };

        task.setOnSucceeded(e -> {
            Page<Pokemon> pagina = task.getValue();
            Platform.runLater(() -> {
                mostrarTodosLosPokemon(pagina.getContent());
                currentPage = pagina.getNumber();
                isLoading = false;
            });
        });

        task.setOnFailed(e -> {
            Platform.runLater(() -> {
                mostrarMensaje("Error al cargar: " + task.getException().getMessage());
                isLoading = false;
            });
        });

        backgroundExecutor.execute(task);
    }

    private void mostrarTodosLosPokemon(List<Pokemon> pokemons) {

        gridPanePokemon.setHgap(20);
        gridPanePokemon.setVgap(20);

        int columnas = 3; // Número de columnas en el GridPane
        int fila = gridPanePokemon.getRowCount();;
        int columna = 1;

        for (Pokemon pokemon : pokemons) {
            AnchorPane tarjeta = cargarTarjetaPokemon(pokemon);
            if (tarjeta != null) {
                gridPanePokemon.add(tarjeta, columna, fila);

                tarjeta.setOnMouseClicked(event -> {
                    Long pokemonId = pokemon.getId();
                    ActionEvent fakeEvent = new ActionEvent(event.getSource(), event.getTarget());
                    escNombrePokemon.setText(pokemon.getName());
                    escNumeroPokemon.setText(String.valueOf(pokemonId));
                    buscarPorNumero(fakeEvent);
                });

                columna++;
                if (columna > columnas) {
                    columna = 1;
                    fila++;
                }
            }
        }

    }

    private AnchorPane cargarTarjetaPokemon(Pokemon pokemon) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pokemon.fxml"));
            AnchorPane tarjeta = loader.load();
            actualizarTarjetaPokemon(tarjeta, pokemon);
            return tarjeta;
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

        numeroPokemon.setText("# " + String.format("%03d", pokemon.getId()));
        nombrePokemonCard.setText(pokemon.getName());

        String tiposConcatenados = pokemon.getTypes().stream()
                .map(Type::getName)
                .collect(Collectors.joining(", "));
        tipoPokemonCard.setText(tiposConcatenados);

        if (pokemon.getSprites() != null) {
            String spriteUrl = pokemon.getSprites().getFrontDefault();
            imagenPokemonCard.setImage(SpriteCache.getOrLoad(pokemon.getId(), spriteUrl));
        }
    }

    @FXML
    private void buscarPorNombre(ActionEvent event) {
        String nombre = escNombrePokemon.getText().trim();
        if (!esNombreValido(nombre)) {
            mostrarMensaje("Nombre inválido");
            return;
        }

        Task<List<Pokemon>> task = new Task<>() {
            @Override
            protected List<Pokemon> call() {
                return pokemonService.getPokemonByName(nombre);
            }
        };

        task.setOnSucceeded(e -> Optional.ofNullable(task.getValue())
                .filter(resultados -> !resultados.isEmpty())
                .ifPresentOrElse(
                        resultados -> Platform.runLater(() -> mostrarPokemonData(resultados.get(0))),
                        () -> mostrarMensaje("Pokémon no encontrado")
                )
        );

        backgroundExecutor.execute(task);
    }

    private static boolean esNombreValido(String nombre) {
        return !nombre.isEmpty() && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");
    }

    @FXML
    private void buscarPorNumero(ActionEvent event) {
        parseLong(escNumeroPokemon.getText().trim())
                .ifPresentOrElse(this::cargarPokemonPorId,
                        () -> mostrarMensaje("ID inválido"));
    }

    private Optional<Long> parseLong(String text) {
        try {
            return Optional.of(Long.parseLong(text));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private void cargarPokemonPorId(Long id) {
        Task<Optional<Pokemon>> task = new Task<>() {
            @Override
            protected Optional<Pokemon> call() {
                return pokemonService.getPokemonById(id);
            }
        };

        task.setOnSucceeded(e -> task.getValue()
                .ifPresentOrElse(p -> Platform.runLater(() -> mostrarPokemonData(p)),
                        () -> mostrarMensaje("Pokémon no encontrado"))
        );

        backgroundExecutor.execute(task);
    }

    @FXML
    private void filtrarPorTipo()  {
        System.out.println("Filtrando por tipo");
        currentPage = 0;
        gridPanePokemon.getChildren().clear();
        cargarPagina(currentPage);
    }

    @FXML
    private void filtrarPorHabilidad()  {
        System.out.println("Filtrando por habilidad");
        currentPage = 0;
        gridPanePokemon.getChildren().clear();
        cargarPagina(currentPage);
    }

    @FXML
    private void filtrarPokemon(){

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
                .ifPresent(url -> imagenPokemon.setImage(SpriteCache.getOrLoad(pokemon.getId(), url)));

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

    private void mostrarEvoluciones(Long pokemonId) {
        limpiarDatosEvoluciones();
        Task<List<Long>> task = new Task<>() {
            @Override
            protected List<Long> call() {
                return pokemonService.getEvolutionsById(pokemonId);
            }
        };

        task.setOnSucceeded(e -> {
            List<Long> evolutionIds = task.getValue();
            if (evolutionIds.isEmpty()) {
                Platform.runLater(() -> mostrarMensaje("Sin evoluciones"));
            } else {
                evolutionIds.stream()
                        .limit(3)
                        .forEach(id -> cargarEvolucion(id, evolutionIds.indexOf(id) + 1));
            }
        });

        backgroundExecutor.execute(task);
    }


    private void cargarEvolucion(Long evolutionId, int position) {
        Task<Optional<Pokemon>> task = new Task<>() {
            @Override
            protected Optional<Pokemon> call() {
                return pokemonService.getPokemonById(evolutionId);
            }
        };

        task.setOnSucceeded(e -> {
            Optional<Pokemon> evolucion = task.getValue();
            Platform.runLater(() -> actualizarUIEvolucion(evolucion, position));
        });

        backgroundExecutor.execute(task);
    }

    private void actualizarUIEvolucion(Optional<Pokemon> evolucion, int position) {
        Label[] nombreLabels = {nombreEvo1, nombreEvo2, nombreEvo3};
        Label[] tipoLabels = {tipoEvo1, tipoEvo2, tipoEvo3};
        ImageView[] imagenViews = {imagenEvo1, imagenEvo2, imagenEvo3};

        if (position < 1 || position > 3) return;

        evolucion.ifPresentOrElse(
                p -> {
                    nombreLabels[position-1].setText(p.getName());
                    tipoLabels[position-1].setText(p.getTypes().stream()
                            .map(Type::getName)
                            .collect(Collectors.joining(", ")));
                    pokemonService.getSpriteById(p.getId())
                            .ifPresent(url -> imagenViews[position-1].setImage(SpriteCache.getOrLoad(p.getId(), url)));
                },
                () -> {
                    nombreLabels[position-1].setText("N/A");
                    imagenViews[position-1].setImage(null);
                }
        );
    }

    @FXML
    private void cargarDatos(ActionEvent event) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                pokeService.saveAllData();
                return null;
            }
        };

        task.setOnSucceeded(e -> Platform.runLater(() -> {
            mostrarMensaje("Datos cargados exitosamente");
            gridPanePokemon.getChildren().clear();
            cargarTiposEnComboBox();
            cargarHabilidadesEnComboBox();
            currentPage = 0;
            cargarPagina(currentPage);
        }));

        backgroundExecutor.execute(task);
    }

    private void limpiarDatosEvoluciones() {
        nombreEvo1.setText("");
        nombreEvo2.setText("");
        nombreEvo3.setText("");

        tipoEvo1.setText("");
        tipoEvo2.setText("");
        tipoEvo3.setText("");

        imagenEvo1.setImage(null);
        imagenEvo2.setImage(null);
        imagenEvo3.setImage(null);
    }


    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void shutdown() {
        backgroundExecutor.shutdown();
    }


}