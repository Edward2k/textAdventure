import java.util.List;

public class BasicItem extends Item {
    private List<String> canBe;
    private List<String> usedTo;

    BasicItem(String name1, int id, List<String> canBe, List<String> usedTo) {
        super(name1, id);
        this.canBe = canBe;
        this.usedTo = usedTo;
    }

    public boolean isValidAction(String action) {
        return true;
    }

    public boolean isValidManipulation(String action) {
        return true;
    }
}
