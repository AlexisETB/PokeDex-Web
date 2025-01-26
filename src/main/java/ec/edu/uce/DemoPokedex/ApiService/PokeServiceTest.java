package ec.edu.uce.DemoPokedex.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PokeServiceTest implements CommandLineRunner {

    @Autowired
    private PokeService pokeService;

    @Override
    public void run(String... args) throws Exception {
        // Prueba completa
        System.out.println("Probando sincronización completa...");
        pokeService.saveAllPokemon();
    }
}