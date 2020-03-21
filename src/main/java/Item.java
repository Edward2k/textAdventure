public abstract class Item {
    private final String name;
    private final int ID; //TODO: find a way to alwasy create new ID's

    Item(String name1, int id) {
        this.name = name1;
        this.ID = id;
    }

    public final String getName() { return name; }

    public final int getID() { return ID; }

}
