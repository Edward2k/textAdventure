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

    private Parser parser;
    private Printer printer;
    private Interface clientInterface;

    Player (Socket sock, Coordinate coord, Interface gameInterface) {
        this.coord = coord;
        moves = 0;
        health = MAX_HEALTH;
        parser = new Parser(sock);
        printer = new Printer(sock);
        clientInterface = gameInterface;
        backpack = new ArrayList<Item>();
    }

    public Instruction getInstruction(){
        return parser.getInstruction(clientInterface.getInput());
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

    public void output(String s) {
//        printer.output(s);
        clientInterface.append("\n" + s + "\n================================================");
    }

    public String getLine() {
//        return parser.getLine();
        return clientInterface.getInput();
    }

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
}
