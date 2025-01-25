package ec.edu.uce.DemoPokedex.Services;

import ec.edu.uce.DemoPokedex.Model.Pokemon;
public class PokeApiClientTest {
    public static void main(String[] args) {
        // Crear instancia del cliente
        PokeApiClient pokeApiClient = new PokeApiClient();

        // Probar obtener un Pokémon por nombre
        try {
            System.out.println("Obteniendo datos de Bulbasaur...");
            Pokemon bulbasaur = pokeApiClient.getPokemon("bulbasaur");
            System.out.println("ID: " + bulbasaur.getId());
            System.out.println("Nombre: " + bulbasaur.getName());
            System.out.println("Altura: " + bulbasaur.getHeight());
            System.out.println("Peso: " + bulbasaur.getWeight());
            System.out.println("Habilidades: ");
            bulbasaur.getAbilities().forEach(ability ->
                    System.out.println("Habilidad: " + ability.getName() + ", Oculta: " + ability.isHidden() + ", Slot: " + ability.getSlot())
            );
        } catch (Exception e) {
            System.err.println("Error obteniendo el Pokémon: " + e.getMessage());
        }

        // Probar obtener la cadena de evolución
        try {
            System.out.println("\nObteniendo cadena de evolución para ID 1...");
            String evolutionChain = pokeApiClient.getEvolutionChain(1);
            System.out.println("Datos de la cadena de evolución:");
            System.out.println(evolutionChain);
        } catch (Exception e) {
            System.err.println("Error obteniendo la cadena de evolución: " + e.getMessage());
        }
    }
}