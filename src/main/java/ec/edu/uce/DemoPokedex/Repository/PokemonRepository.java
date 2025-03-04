package ec.edu.uce.DemoPokedex.Repository;

import ec.edu.uce.DemoPokedex.Model.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {


    // Buscar Pokémon por tipo (nombre del tipo)
    @Query("SELECT DISTINCT p FROM Pokemon p LEFT JOIN FETCH p.types t WHERE t.name = :typeName")
    Page<Pokemon> findByTypeName(@Param("typeName") String typeName, Pageable pageable);

    // Buscar Pokémon por habilidad (nombre de la habilidad)
    @EntityGraph(attributePaths = {"types"})
    @Query("SELECT DISTINCT p FROM Pokemon p LEFT JOIN FETCH p.abilities a WHERE a.name = :abilityName")
    Page<Pokemon> findByAbilityName(@Param("abilityName") String abilityName, Pageable pageable);

    //Buscar Pokemon por typo y habilidad
    @Query("SELECT DISTINCT p FROM Pokemon p LEFT JOIN FETCH p.types t LEFT JOIN FETCH p.abilities a WHERE t.name = :typeName AND a.name = :abilityName")
    Page<Pokemon> findByTypeNameAndAbilityName(@Param("typeName") String typeName, @Param("abilityName") String abilityName, Pageable pageable);

    // Obtener un Pokémon junto con sus evoluciones
    @Query("SELECT p.evolutions FROM Pokemon p WHERE p.id = :id")
    List<Long> findEvolutionsById(@Param("id") Long id);

    //3 consultas basicas con carga de relaciones incluida
    //Pokemon con detalles
    @Query("SELECT DISTINCT p FROM Pokemon p LEFT JOIN FETCH p.stats LEFT JOIN FETCH p.types WHERE p.id = :id")
    Optional<Pokemon> findByIdWithStatsAndTypes(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Pokemon p LEFT JOIN FETCH p.abilities WHERE p.id = :id")
    Optional<Pokemon> findByIdWithAbilities(@Param("id") Long id);

    //Pokemon con detalles nombre
    @Query("SELECT DISTINCT p FROM Pokemon p LEFT JOIN FETCH p.stats LEFT JOIN FETCH " +
            "p.types WHERE LOWER(p.name) = LOWER(:name)")
    List<Pokemon> findByNameWithStatsAndTypes(@Param("name") String name);

    // Para la lista general
    @Query("SELECT DISTINCT p FROM Pokemon p LEFT JOIN FETCH p.types")
    Page<Pokemon> findAllWithTypes(Pageable pageable);

    // Obtener el sprite de un Pokémon
    @Query("SELECT p.sprites.frontDefault FROM Pokemon p WHERE p.id = :id")
    Optional<String> findSpriteById(@Param("id") Long id);

}
