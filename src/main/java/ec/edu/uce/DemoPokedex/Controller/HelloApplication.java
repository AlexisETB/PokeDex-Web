package ec.edu.uce.DemoPokedex.Controller;

import ec.edu.uce.DemoPokedex.DemoPokedexApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;


import java.io.IOException;

public class HelloApplication extends Application {

    private static ApplicationContext springContext;

    @Override
    public void init() throws Exception {
        // Obtén el contexto de Spring Boot
        springContext = DemoPokedexApplication.getSpringContext();
        if (springContext == null) {
            throw new IllegalStateException("Spring context is not initialized.");
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/hello-view.fxml"));

        //controladores gestionados por Spring, configuración:
        fxmlLoader.setControllerFactory(springContext::getBean);

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}