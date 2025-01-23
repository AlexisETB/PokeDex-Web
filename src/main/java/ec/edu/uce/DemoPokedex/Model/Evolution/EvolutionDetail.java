package ec.edu.uce.DemoPokedex.Model.Evolution;

public class EvolutionDetail {
    private String trigger;
    private int minLevel;
    private String item;
    private String heldItem;
    private String knownMove;
    private String knownMoveType;

    public EvolutionDetail(String trigger, int minLevel, String item, String heldItem, String knownMove, String knownMoveType) {
        this.trigger = trigger;
        this.minLevel = minLevel;
        this.item = item;
        this.heldItem = heldItem;
        this.knownMove = knownMove;
        this.knownMoveType = knownMoveType;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(String heldItem) {
        this.heldItem = heldItem;
    }

    public String getKnownMove() {
        return knownMove;
    }

    public void setKnownMove(String knownMove) {
        this.knownMove = knownMove;
    }

    public String getKnownMoveType() {
        return knownMoveType;
    }

    public void setKnownMoveType(String knownMoveType) {
        this.knownMoveType = knownMoveType;
    }

    @Override
    public String toString() {
        return "EvolutionDetail{" +
                "trigger='" + trigger + '\'' +
                ", minLevel=" + minLevel +
                ", item='" + item + '\'' +
                ", heldItem='" + heldItem + '\'' +
                ", knownMove='" + knownMove + '\'' +
                ", knownMoveType='" + knownMoveType + '\'' +
                '}';
    }
}
