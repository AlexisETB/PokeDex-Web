package ec.edu.uce.DemoPokedex.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "types")
    private List<Pokemon> pokemon;

    public Type(){

    }
    public Type(String name, List<Pokemon> pokemon) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Type{" +
                ", name='" + name + '\'' +
                '}';
    }
}
