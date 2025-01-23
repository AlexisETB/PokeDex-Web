package ec.edu.uce.DemoPokedex.Model;

public class Move {
    private String name;
    private String url;
    private List<VersionGroupDetail> versionGroupDetails;
    public Move() {

    }

    public Move(String name, String url, List<VersionGroupDetail> versionGroupDetails) {
        this.name = name;
        this.url = url;
        this.versionGroupDetails = versionGroupDetails;
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

    public List<VersionGroupDetail> getVersionGroupDetails() {
        return versionGroupDetails;
    }

    public void setVersionGroupDetails(List<VersionGroupDetail> versionGroupDetails) {
        this.versionGroupDetails = versionGroupDetails;
    }

    @Override
    public String toString() {
        return "Move{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", versionGroupDetails=" + versionGroupDetails +
                '}';
    }
}
