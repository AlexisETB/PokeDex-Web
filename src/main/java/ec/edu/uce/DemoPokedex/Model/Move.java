package ec.edu.uce.DemoPokedex.Model;

import java.util.List;

public class Move {
    private String name;
    public Move() {

    }

    public Move(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Move{" +
                "name='" + name + '\'' +
                '}';
    }
}
