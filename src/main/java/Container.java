import java.util.List;

public class Container extends Item {
    private List<BasicItem> entities;
    private String description;
    private boolean isOpen = false;

    Container(String name1, int id, String description, List<BasicItem> items, List<String> itemCanBe, List<String> itemUsedTo) {
        super(name1, id, itemCanBe, itemUsedTo);
        this.description = description;
        this.entities = items;
    }

    public List<BasicItem> getEntities() { return this.entities;}

    public String getDescription() { return this.description; }

    public void toggle() { this.isOpen = true;}

    public boolean isOpen() {return this.isOpen;}
}
