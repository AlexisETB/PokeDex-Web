package ec.edu.uce.DemoPokedex.Model.Evolution;

public class EvolutionChain {
    private int id;
    private ChainLink chain;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChainLink getChain() {
        return chain;
    }

    public void setChain(ChainLink chain) {
        this.chain = chain;
    }

    public EvolutionChain(int id, ChainLink chain) {
        this.id = id;
        this.chain = chain;

    }

    @Override
    public String toString() {
        return "EvolutionChain{" +
                "id=" + id +
                ", chain=" + chain +
                '}';
    }
}
