package ec.edu.uce.DemoPokedex.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    private long id;
    private String name;
    private int base_experience;
    private double height;
    private double weight;

    private List<Ability> abilities;
    private List<Move> moves;
    private List<Stat> stats;
    private List<Type> types;

    private Sprites sprites;
    private List<Evolution> evolutions;

    public Pokemon() {}

    public Pokemon(long id, String name, int base_experience, double height, double weight, List<Ability> abilities, List<Move> moves,
                   List<Stat> stats, List<Type> types, Sprites sprites, List<Evolution> evolutions) {
        this.id = id;
        this.name = name;
        this.base_experience = base_experience;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.moves = moves;
        this.stats = stats;
        this.types = types;
        this.sprites = sprites;
        this.evolutions = evolutions;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baseExperience=" + base_experience +
                ", height=" + height +
                ", weight=" + weight +
                ", abilitites=" + abilities +
                ", moves=" + moves +
                ", stats=" + stats +
                ", types=" + types +
                ", sprites=" + sprites +
                ", evolutions=" + evolutions +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseExperience() {
        return base_experience;
    }

    public void setBaseExperience(int base_experience) {
        this.base_experience = base_experience;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilitites) {
        this.abilities = abilitites;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public List<Evolution> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(List<Evolution> evolutions) {
        this.evolutions = evolutions;
    }
}
