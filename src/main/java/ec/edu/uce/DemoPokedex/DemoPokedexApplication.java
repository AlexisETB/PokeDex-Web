package ec.edu.uce.DemoPokedex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoPokedexApplication {

	public static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(DemoPokedexApplication.class, args);
	}

	public static ConfigurableApplicationContext getSpringContext() {
		return context;
	}

}
