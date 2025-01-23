package ec.edu.uce.DemoPokedex.Model;

public class Stat {
    private String name;
    private String url;
    private int baseStat;
    private int effort;

    public Stat() {

    }
    public Stat(String name, String url, int baseStat, int effort) {
        this.name = name;
        this.url = url;
        this.baseStat = baseStat;
        this.effort = effort;

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
                ", url='" + url + '\'' +
                ", baseStat=" + baseStat +
                ", effort=" + effort +
                '}';
    }
}
