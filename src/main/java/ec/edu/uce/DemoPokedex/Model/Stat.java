package ec.edu.uce.DemoPokedex.Model;

import jakarta.persistence.*;

@Entity
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int baseStat;
    private int effort;

    public Stat() {

    }
    public Stat(String name, int baseStat, int effort) {
        this.name = name;
        this.baseStat = baseStat;
        this.effort = effort;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(int baseStat) {
        this.baseStat = baseStat;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "name='" + name + '\'' +
                ", baseStat=" + baseStat +
                ", effort=" + effort +
                '}';
    }
}
