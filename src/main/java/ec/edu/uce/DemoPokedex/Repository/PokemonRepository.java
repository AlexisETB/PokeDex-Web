package ec.edu.uce.DemoPokedex.Repository;

import ec.edu.uce.DemoPokedex.Model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    // Buscar Pokémon por nombre (case insensitive)
    List<Pokemon> findByNameIgnoreCase(String name);

    // Buscar Pokémon por tipo (nombre del tipo)
    @Query("SELECT p FROM Pokemon p JOIN p.types t WHERE t.name = :typeName")
    List<Pokemon> findByTypeName(@Param("typeName") String typeName);

    // Buscar Pokémon por habilidad (nombre de la habilidad)
    @Query("SELECT p FROM Pokemon p JOIN p.abilities a WHERE a.name = :abilityName")
    List<Pokemon> findByAbilityName(@Param("abilityName") String abilityName);

    // Obtener un Pokémon junto con sus evoluciones
    @Query("SELECT p.evolutions FROM Pokemon p WHERE p.id = :id")
    List<Long> findEvolutionsById(@Param("id") Long id);

    // Obtener el sprite de un Pokémon
    @Query("SELECT p.sprites.frontDefault FROM Pokemon p WHERE p.id = :id")
    Optional<String> findSpriteById(@Param("id") Long id);

    @Query("SELECT p FROM Pokemon p LEFT JOIN FETCH p.types LEFT JOIN FETCH p.abilities")
    List<Pokemon> findAllWithTypesAndAbilities();


    // Obtener un Pokémon junto con sus evoluciones (con JOIN FETCH)
    @Query("SELECT p FROM Pokemon p LEFT JOIN FETCH p.evolutions WHERE p.id = :id")
    Optional<Pokemon> findByIdWithEvolutions(@Param("id") Long id);
}
