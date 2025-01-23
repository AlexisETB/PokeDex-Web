package ec.edu.uce.DemoPokedex.Model;

public class HeldItem {
    private String name;
    private String url;

    public HeldItem() {}

    public HeldItem(String name, String url) {
        this.name = name;
        this.url = url;
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

    @Override
    public String toString() {
        return "HeldItem{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
