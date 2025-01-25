package ec.edu.uce.DemoPokedex.Repository;

import ec.edu.uce.DemoPokedex.Model.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoveRepository extends JpaRepository<Move, Long> {
    Optional<Move> findByName(String name);
}
