import java.util.ArrayList;
import java.util.List;

public class Container extends Item {
    private boolean isOpen;
    private List<BasicItem> entities;
    private String description;

    Container(String name1, int id, String description, List<BasicItem> items) {
        super(name1, id);
        this.description = description;
        this.entities = items;
    }

    public void toggle(boolean state) { this.isOpen = state; }

    public String getDescription() { return this.description; }

    public void addItems() {}

}
