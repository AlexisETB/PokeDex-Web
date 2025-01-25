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
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int baseStat;
    private int effort;

    @Override
    public String toString() {
        return "Stat{" +
                "name='" + name + '\'' +
                ", baseStat=" + baseStat +
                ", effort=" + effort +
                '}';
    }
}
