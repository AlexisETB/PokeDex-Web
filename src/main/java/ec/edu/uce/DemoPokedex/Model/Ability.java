package ec.edu.uce.DemoPokedex.Model;

public class Ability {
    private String name;
    private String url;
    private boolean isHidden;
    private int slot;

    public Ability (){

    }
    public Ability(String name, String url, boolean isHidden, int slot) {
        this.name = name;
        this.url = url;
        this.isHidden = isHidden;
        this.slot = slot;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
                ", url='" + url + '\'' +
                ", isHidden=" + isHidden +
                ", slot=" + slot +
                '}';
    }
}
