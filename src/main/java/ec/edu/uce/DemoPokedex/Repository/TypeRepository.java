package ec.edu.uce.DemoPokedex.Repository;

import ec.edu.uce.DemoPokedex.Model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Optional<Type> findByName(String name);

    @Query("SELECT DISTINCT t.name FROM Type t")
    List<String> findAllDistinctNames();
}
