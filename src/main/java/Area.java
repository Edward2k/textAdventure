import java.util.List;
import java.util.ArrayList;

public class Area {

    private List<Item> items;
    private Obstacle obstacle;
    private String name;
    private String description;

    Area (String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.obstacle = null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        if (!canEnter()) { return obstacle.getDescription(); }
        if (items.isEmpty()) {return description + "\nThere are no Items in this room.";}
        StringBuilder finalDescription = new StringBuilder(description + "\n");
        finalDescription.append(getItemsDescription());
        return finalDescription.toString();
    }

    public String getItemsDescription() {
        StringBuilder itemDescription = new StringBuilder("In this room, the following items are present: ");
        for (Item item: items) {
            itemDescription.append("a <").append(item.getName()).append(">, ");
        }
        return itemDescription.toString();
    }

    public boolean canEnter() {
        if (this.obstacle != null) { return obstacle.isNeutralized(); }
        else { return true; }
    }

    public final void addItem(Item i) { this.items.add(i); }

    public final void removeItem(Item i) { this.items.remove(i); }

    public List<Item> getItems() { return items; }

    public Obstacle getObstacle() { return this.obstacle;}

    public void setObstacle(Obstacle obstacle) { this.obstacle = obstacle;}


}
