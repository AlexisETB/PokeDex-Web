package ec.edu.uce.DemoPokedex.Model;

public class Type {
    private int slot;
    private String name;

    public Type(){

    }
    public Type(int slot, String name) {
        this.slot = slot;
        this.name = name;

    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
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
                "slot=" + slot +
                ", name='" + name + '\'' +
                '}';
    }
}
