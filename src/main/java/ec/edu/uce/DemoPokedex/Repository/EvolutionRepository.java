package ec.edu.uce.DemoPokedex.Repository;

import ec.edu.uce.DemoPokedex.Model.Evolution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvolutionRepository extends JpaRepository<Evolution, Long> {

}
