package ec.edu.uce.DemoPokedex.Model;

public class Evolution {
    private String speciesName; // Nombre del Pokémon evolutivo
    private String trigger;     // Condición de evolución (e.g., nivel, objeto)
    private Integer minLevel;   // Nivel mínimo requerido (si aplica)


    // Getters y Setters
    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }


    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

}