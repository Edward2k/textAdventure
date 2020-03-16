import java.net.Socket;

public class Player {

    private String name;
    private Coordinate coord;
    private Item[] backpack;
    private int score;
    private int moves;
    private int health;

    private Parser parser;
    private Printer printer;

    Player (Socket sock, Coordinate coord) {
        this.coord = coord;
        this.backpack = new Item[]{};
        score = 0;
        moves = 0;
        health = 100;
        parser = new Parser(sock);
        printer = new Printer(sock);
    }

    public Instruction getInstruction(){
        return parser.getInstruction();
    }

    public String getName() {
        return name;
    }

    public void movePlayer (Coordinate c) {
        coord = c;
    }

    public void setName(String n) {this.name = n;}

    public Coordinate position() {return coord;}

    public void output(String s) {printer.output(s);}

    public String getLine() {return parser.getLine();}

    public int getHealth () {
        return health;
    }

    public Item[] getBackpack () {
        return backpack;
    }
}