package ec.edu.uce.DemoPokedex.Optim;

import javafx.scene.image.Image;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpriteCache {
    private static final Map<Long, Image> cache = new ConcurrentHashMap<>();

    // Carga el sprite desde la caché o lo descarga si no existe
    public static Image getOrLoad(Long pokemonId, String spriteUrl) {
        if (spriteUrl == null || spriteUrl.isEmpty()) {
            return null; // Evitar URLs inválidas
        }
        return cache.computeIfAbsent(pokemonId, id -> {
            System.out.println("Cargando sprite para Pokémon ID: " + id);
            return new Image(spriteUrl, true); // "true" = carga en segundo plano
        });
    }

    // Opcional: Limpiar caché si es necesario
    public static void clearCache() {
        cache.clear();
    }
}