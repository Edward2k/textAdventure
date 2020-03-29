import java.util.List;

public abstract class Item {
    private final String name;
    private final int ID;
    private final List<String> canBe;
    private final List<String> usedTo;

    Item(String name1, int id, List<String> itemCanBe, List<String> itemUsedTo) {
        this.name = name1;
        this.ID = id;
        this.canBe = itemCanBe;
        this.usedTo = itemUsedTo;
    }

    public final String getName() { return name; }

    public boolean isValidAction(String action) {
        for(String itemCanBe : canBe) {
            if(itemCanBe.equals(action)) { return true; }
        }
        return false;
    }

    public boolean isValidManipulation(String action) {
        for(String itemUsedTo : usedTo) {
            if(itemUsedTo.equals(action)) { return true; }
        }
        return false;
    }

}
