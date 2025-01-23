package ec.edu.uce.DemoPokedex.Model.Evolution;

public class ChainLink {
    private String speciesName;
    private String speciesUrl;
    private List<EvolutionDetail> evolutionDetails;
    private List<ChainLink> evolvesTo;

    public ChainLink(String speciesName, String speciesUrl, List<EvolutionDetail> evolutionDetails, List<ChainLink> evolvesTo) {
        this.speciesName = speciesName;
        this.speciesUrl = speciesUrl;
        this.evolutionDetails = evolutionDetails;
        this.evolvesTo = evolvesTo;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getSpeciesUrl() {
        return speciesUrl;
    }

    public void setSpeciesUrl(String speciesUrl) {
        this.speciesUrl = speciesUrl;
    }

    public List<EvolutionDetail> getEvolutionDetails() {
        return evolutionDetails;
    }

    public void setEvolutionDetails(List<EvolutionDetail> evolutionDetails) {
        this.evolutionDetails = evolutionDetails;
    }

    public List<ChainLink> getEvolvesTo() {
        return evolvesTo;
    }

    public void setEvolvesTo(List<ChainLink> evolvesTo) {
        this.evolvesTo = evolvesTo;
    }

    @Override
    public String toString() {
        return "ChainLink{" +
                "speciesName='" + speciesName + '\'' +
                ", speciesUrl='" + speciesUrl + '\'' +
                ", evolutionDetails=" + evolutionDetails +
                ", evolvesTo=" + evolvesTo +
                '}';
    }
}
