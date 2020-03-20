public class BasicItem extends Item {
    String[] canBe;
    String[] usedTo;

    BasicItem(String name1, int id) {
        super(name1, id);
    }

    public boolean isValidAction(String action) {
        return true;
    }

    public boolean isValidManipulation(String action) {
        return true;
    }
}
