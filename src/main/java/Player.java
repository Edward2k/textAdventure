import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private Coordinate coord;
    private List<Item> backpack;
    private int moves;
    private int health;
    private static int MAX_HEALTH = 100;
    private String lastValidDirection; //This is used to navigate back from an obstacle.

    private Parser parser;
    private Printer printer;

    Player (Socket sock, Coordinate coord) {
        this.coord = coord;
        moves = 0;
        health = MAX_HEALTH;
        parser = new Parser(sock);
        printer = new Printer(sock);
        backpack = new ArrayList<Item>();
        lastValidDirection = "";
    }

    public Instruction getInstruction(){
        return parser.getInstruction();
    }

    public String getName() {
        return name;
    }

    public void movePlayer (Coordinate c) {
        moves += 1;
        coord = c;
    }

    public void setName(String n) {this.name = n;}

    public Coordinate position() {return coord;}

    public void output(String s) { printer.output(s); }

    public String getLine() { return parser.getLine(); }

    public int getHealth () {
        return health;
    }

    public List<Item> getBackpack () {
        return backpack;
    }

    public void addItem(Item i) { backpack.add(i); }

    public final void removeItem(Item i) { backpack.remove(i); }

    public final int getMoves() {return moves;}

    public int getScore() {return moves/(MAX_HEALTH + 1 - health);}

    public void setLastValidDirection(String d) {lastValidDirection = d;}
    public String getLastValidDirection() {return lastValidDirection;}
}
