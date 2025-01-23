package ec.edu.uce.DemoPokedex.Model;

public class GameIndex {
    private int gameIndex;
    private String versionName;
    private String versionUrl;

    public GameIndex() {

    }

    public GameIndex(int gameIndex, String versionName, String versionUrl) {
        this.gameIndex = gameIndex;
        this.versionName = versionName;
        this.versionUrl = versionUrl;
    }

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    @Override
    public String toString() {
        return "GameIndex{" +
                "gameIndex=" + gameIndex +
                ", versionName='" + versionName + '\'' +
                ", versionUrl='" + versionUrl + '\'' +
                '}';
    }
}
