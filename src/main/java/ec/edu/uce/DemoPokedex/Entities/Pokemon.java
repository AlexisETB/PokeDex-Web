package ec.edu.uce.DemoPokedex.Entities;

import java.util.List;

public class Pokemon {
    private long id;
    private String name;
    private int baseExperience;
    private int height;
    private int weight;
    private boolean isDefault;
    private String locationAreaEncounters;
    private int order;

    private List<Ability> abilitites;
    private List<Move> moves;
    private List<Form> forms;
    private List<HeldItem> heldItems;
    private List<Stat> stats;
    private List<Type> types;

    public Pokemon() {}

    public Pokemon(long id, String name,
                   int baseExperience, int height, int weight, boolean isDefault,
                   String locationAreaEncounters, int order, List<Ability> abilitites,
                   List<Move> moves, List<Form> forms,
                   List<HeldItem> heldItems, List<Stat> stats, List<Type> types) {
        this.id = id;
        this.name = name;
        this.baseExperience = baseExperience;
        this.height = height;
        this.weight = weight;
        this.isDefault = isDefault;
        this.locationAreaEncounters = locationAreaEncounters;
        this.order = order;
        this.abilitites = abilitites;
        this.moves = moves;
        this.forms = forms;
        this.heldItems = heldItems;
        this.stats = stats;
        this.types = types;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getLocationAreaEncounters() {
        return locationAreaEncounters;
    }

    public void setLocationAreaEncounters(String locationAreaEncounters) {
        this.locationAreaEncounters = locationAreaEncounters;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Ability> getAbilitites() {
        return abilitites;
    }

    public void setAbilitites(List<Ability> abilitites) {
        this.abilitites = abilitites;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }


    public List<HeldItem> getHeldItems() {
        return heldItems;
    }

    public void setHeldItems(List<HeldItem> heldItems) {
        this.heldItems = heldItems;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baseExperience=" + baseExperience +
                ", height=" + height +
                ", weight=" + weight +
                ", isDefault=" + isDefault +
                ", locationAreaEncounters='" + locationAreaEncounters + '\'' +
                ", order=" + order +
                ", abilitites=" + abilitites +
                ", moves=" + moves +
                ", forms=" + forms +
                ", heldItems=" + heldItems +
                ", stats=" + stats +
                ", types=" + types +
                '}';
    }
}
