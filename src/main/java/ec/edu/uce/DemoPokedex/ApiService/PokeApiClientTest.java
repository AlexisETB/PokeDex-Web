package ec.edu.uce.DemoPokedex.ApiService;

public class PokeApiClientTest {
    public static void main(String[] args) {
        // Crear instancia del cliente
        PokeApiClient pokeApiClient = new PokeApiClient();

//        // Probar obtener datos de un Pokémon
//        try {
//            System.out.println("Obteniendo datos de Pikachu...");
//            Pokemon pikachu = pokeApiClient.getPokemon("pikachu");
//            System.out.println("ID: " + pikachu.getId());
//            System.out.println("Nombre: " + pikachu.getName());
//            System.out.println("Peso: " + pikachu.getWeight());
//            System.out.println("Altura: " + pikachu.getHeight());
//            System.out.println("Base Experience: " + pikachu.getBaseExperience());
//            System.out.println("Tipos: ");
//            pikachu.getTypes().forEach( type ->
//                    System.out.println("\t" + type.getName())
//                    );
//            System.out.println("Habilidades: ");
//            pikachu.getAbilities().forEach(ability ->
//                    System.out.println(" - " + ability.getName() + " (Hidden: " + ability.isHidden() + ")")
//            );
//            System.out.println("Movimientos: ");
//            pikachu.getMoves().forEach(move ->
//                            System.out.println(" - " + move.getName())
//                    );
//            System.out.println("stats");
//            pikachu.getStats().forEach(stat ->
//                    System.out.println(" - " + stat.getName() + ": " + stat.getBaseStat()
//                    ));
//            System.out.println("Sprites: ");
//            System.out.println(" - Front Default: " + pikachu.getSprites().getFrontDefault());
//            System.out.println(" - Front Shiny: " + pikachu.getSprites().getFrontShiny());
//            System.out.println(" - Back Default: " + pikachu.getSprites().getBackDefault());
//            System.out.println(" - Back Shiny: " + pikachu.getSprites().getBackShiny());
//        } catch (Exception e) {
//            System.err.println("Error obteniendo datos del Pokémon: " + e.getMessage());
//        }
//
//        // Probar obtener la cadena de evolución
//        try {
//            System.out.println("\nObteniendo cadena de evolución para ID 10...");
//            List<Evolution> evolutions = pokeApiClient.getEvolutionChain(10);
//            System.out.println("Evoluciones:");
//            evolutions.forEach(evolution ->
//                    System.out.println(" - " + evolution.getSpeciesName() +
//                            " (Trigger: " + evolution.getEvolutiontrigger() +
//                            ", Min Level: " + evolution.getMinLevel() + ")")
//            );
//        } catch (Exception e) {
//            System.err.println("Error obteniendo la cadena de evolución: " + e.getMessage());
//        }
   }
}