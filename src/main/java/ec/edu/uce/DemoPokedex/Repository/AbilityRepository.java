package ec.edu.uce.DemoPokedex.Repository;

import ec.edu.uce.DemoPokedex.Model.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AbilityRepository extends JpaRepository<Ability, Long> {
    Optional<Ability> findByName(String name);

    @Query("SELECT DISTINCT a.name FROM Ability a")
    List<String> findAllDistinctNames();
}
