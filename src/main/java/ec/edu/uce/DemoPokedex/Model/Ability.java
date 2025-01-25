package ec.edu.uce.DemoPokedex.Model;

public class Ability {
    private String name;
    private boolean isHidden;
    private int slot;

    public Ability (){

    }
    public Ability(String name, boolean isHidden, int slot) {
        this.name = name;
        this.isHidden = isHidden;
        this.slot = slot;

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

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                ", isHidden=" + isHidden +
                ", slot=" + slot +
                '}';
    }
}
