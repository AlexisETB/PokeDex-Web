package ec.edu.uce.DemoPokedex.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Evolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String speciesName; // Nombre del Pokémon evolutivo
    private String evolutiontrigger;     // Condición de evolución (e.g., nivel, objeto)
    private Integer minLevel;   // Nivel mínimo requerido (si aplica)

    @ManyToOne
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;

}