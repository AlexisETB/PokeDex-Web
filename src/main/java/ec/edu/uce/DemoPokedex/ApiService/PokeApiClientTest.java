package ec.edu.uce.DemoPokedex.ApiService;

import ec.edu.uce.DemoPokedex.Model.Pokemon;

import java.util.List;

public class PokeApiClientTest {
    public static void main(String[] args) {
        // Crear instancia del cliente
        PokeApiClient pokeApiClient = new PokeApiClient();

        try {
            // Probar obtener todos los Pokémon
            System.out.println("Obteniendo todos los Pokémon...");
            List<Pokemon> pokemonList = pokeApiClient.getAllPokemon();

            // Imprimir los nombres de los primeros 5 Pokémon
            System.out.println("Primeros 5 Pokémon obtenidos:");
            pokemonList.stream().limit(5).forEach(pokemon -> System.out.println("- " + pokemon.getName()));

            // Probar obtener una cadena de evoluciones (opcional)
            String evolutionChainUrl = "https://pokeapi.co/api/v2/evolution-chain/1/";
            List<Long> evolutionIds = pokeApiClient.getEvolutionChain(evolutionChainUrl);
            System.out.println("\nEvolución IDs para la URL " + evolutionChainUrl + ": " + evolutionIds);

        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }
    }
   }
