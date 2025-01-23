package ec.edu.uce.DemoPokedex.Model;

public class Sprites {
    private String frontDefault;
    private String frontShiny;
    private String backDefault;
    private String backShiny;

    public Sprites(String frontDefault, String frontShiny, String backDefault, String backShiny) {
        this.frontDefault = frontDefault;
        this.frontShiny = frontShiny;
        this.backDefault = backDefault;
        this.backShiny = backShiny;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    public String getBackDefault() {
        return backDefault;
    }

    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public void setBackShiny(String backShiny) {
        this.backShiny = backShiny;
    }

    @Override
    public String toString() {
        return "Sprites{" +
                "frontDefault='" + frontDefault + '\'' +
                ", frontShiny='" + frontShiny + '\'' +
                ", backDefault='" + backDefault + '\'' +
                ", backShiny='" + backShiny + '\'' +
                '}';
    }
}
