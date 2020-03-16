public class Player {

    private String name;
    private Coordinate coord;
    private Item[] backpack;
    private int score;
    private int moves;
    private int health;

    private Parser parser;
    private Printer printer;
    private Interface gameInterface;

    Player (Coordinate coord) {
        this.coord = coord;
        this.backpack = new Item[]{};
        score = 0;
        moves = 0;
        health = 100;
        parser = new Parser();
        printer = new Printer();
        gameInterface = new Interface();
    }

    public Instruction getInstruction(){
        return parser.getInstruction(gameInterface);
    }

    public String getName() {
        return name;
    }

    public void movePlayer (Coordinate c) {
        coord = c;
    }

    public void setName(String n) {this.name = n;}

    public Coordinate position() {return coord;}

    public void output(String s) {printer.output(s, gameInterface);}

    public String getLine() {return parser.getLine(gameInterface);}

    public int getHealth () {
        return health;
    }

    public Item[] getBackpack () {
        return backpack;
    }
}
