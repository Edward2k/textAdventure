public class Player {

    private String name;
    private Coordinate coord;
    private Item[] backpack;
    private int score;
    private int moves;
    private int health;

    private Parser parser;

    Player (String name, Coordinate coord) {
        this.name = name;
        this.coord = coord;
        this.backpack = new Item[]{};
        score = 0;
        moves = 0;
        health = 100;

        parser = new Parser();
    }

    public Instruction getCommand(){
        return parser.getInstruction();
    }

    public String getUserName () {
        return name;
    }

    public int getHealth () {
        return health;
    }

    public Item[] getBackpack () {
        return backpack;
    }

    public boolean movePlayer (Coordinate coord) {
        return false;
    }

    public Coordinate getCurrentPosition () {
        return coord;
    }

}
