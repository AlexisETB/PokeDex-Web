package ec.edu.uce.DemoPokedex.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Pokemon {

    @Id
    private Long id;

    private String name;
    private int base_experience;
    private double height;
    private double weight;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ability> abilities;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stat> stats;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Type> types;

    @Embedded
    private Sprites sprites;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evolution> evolutions;

}
