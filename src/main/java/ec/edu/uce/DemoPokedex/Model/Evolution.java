package ec.edu.uce.DemoPokedex.Model;

import jakarta.persistence.*;

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

    // Getters y Setters
    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }


    public String getEvolutiontrigger() {
        return evolutiontrigger;
    }

    public void setEvolutiontrigger(String evolutiontrigger) {
        this.evolutiontrigger = evolutiontrigger;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

}