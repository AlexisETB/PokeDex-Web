package ec.edu.uce.DemoPokedex.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private boolean isHidden;

    @ManyToMany(mappedBy = "abilities")
    private List<Pokemon> pokemon;

    public Ability (){}

    public Ability(String name, boolean isHidden, List<Pokemon> pokemon) {
        this.name = name;
        this.isHidden = isHidden;
        this.pokemon = pokemon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                ", isHidden=" + isHidden +
                '}';
    }
}
