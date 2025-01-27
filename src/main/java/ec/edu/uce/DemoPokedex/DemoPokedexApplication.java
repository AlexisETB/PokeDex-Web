package ec.edu.uce.DemoPokedex;

import ec.edu.uce.DemoPokedex.Controller.HelloApplication;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoPokedexApplication {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(DemoPokedexApplication.class, args);
		// Iniciar JavaFX
		Application.launch(HelloApplication.class, args);
	}

	public static ConfigurableApplicationContext getSpringContext() {
		return context;
	}

}
