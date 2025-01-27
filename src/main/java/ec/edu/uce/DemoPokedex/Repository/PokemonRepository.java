package ec.edu.uce.DemoPokedex.Repository;

import ec.edu.uce.DemoPokedex.Model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    // Metodo para buscar Pokémon por nombre (case insensitive)
    List<Pokemon> findByNameIgnoreCase(String name);

    // Metodo para buscar Pokémon por tipo

    List<Pokemon> findByType(String typeName);

    // Metodo para buscar Pokémon por habilidad

    List<Pokemon> findByAbility(String abilityName);

    @Query("SELECT p FROM Pokemon p LEFT JOIN FETCH p.evolutions WHERE p.id = :id")
    Optional<Pokemon> findByIdWithEvolutions(@Param("id") Long id);
}
