import java.util.List;

public class Area {

    private List<Item> items;
    private Item obstacle;
    private boolean obstacleNeutralized;
    private String name;
    private String description;

    Area (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean canEnter() {
        return obstacleNeutralized;
    }

    public final void addItem(Item i) {
        this.items.add(i);
    }

}
