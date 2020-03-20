import java.util.List;
import java.util.ArrayList;

public class Area {

    private List<Item> items;
    private Item obstacle;
    private boolean obstacleNeutralized;
    private String name;
    private String description;

    Area (String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<Item>();
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

    public final void addItem(Item i) { this.items.add(i); }

    public final void removeItem(Item i) {
        this.items.remove(i);
    }

    public List<Item> getItems() { return items; }

}
