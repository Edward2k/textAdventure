import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private Coordinate coord;
    private List<Item> backpack;
    private int score;
    private int moves;
    private int health;

    private Parser parser;
    private Printer printer;

    Player (Socket sock, Coordinate coord) {
        this.coord = coord;
        score = 0;
        moves = 0;
        health = 100;
        parser = new Parser(sock);
        printer = new Printer(sock);
        backpack = new ArrayList<Item>();
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

    public List<Item> getBackpack () {
        return backpack;
    }

    public void addItem(Item i) { backpack.add(i); }

    public final void removeItem(Item i) { backpack.remove(i); }
}
