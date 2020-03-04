public class Map {

    private Area[][] map;
    private Coordinate entryPoint;
    private int mapSize;

    Map () {
        mapSize = 0;
        entryPoint = new Coordinate(0 ,0); // maybe read from file? JSON?
        initDemoMap();
    }

    private void initDemoMap() {
        entryPoint = new Coordinate(2, 1);
        mapSize = 3;
        map = new Area[mapSize][mapSize];
//        |  N |    N      |  Eating Area      |
//        |  N | LOBBY     |  Cafeteria        |
//        |  N | Entrance  |        N          |
        map[2][1] = new Area("Entrance", "It is a quiet Friday morning and you find yourself directly infront of the " +
                "of the VU main building. For some reason, you can not leave this area, and are forced to move north of where you are now.");
        map[1][1] = new Area("Lobby", "Everything is quieter than usual in the Lobby. The lights are off and it is, thus, darker than usual. You do, however, see a light to your East.");
        map[1][2] = new Area("Cafeteria", "The cafeteria is, like the lobby, empty. You do, however, hear some noise... you just can't tell from where :O");
        map[0][2] = new Area("Eating area", "You found the noise! It is coming from some rats eating a rotten sandwhich. Also worth noting that, if your made it here, it is the end of the DEMO map :p");
//       TODO:  Make the ITEM CLASS!!

//        map[0][2].addItem(new Item());
    }

    public String getDescription(Coordinate c) {
        int x = c.x();
        int y = c.y();
        String intro = "You are currently in the " + map[x][y].getName() + " \n";
        return intro + map[x][y].getDescription();
    }
    public Coordinate getEntryPoint() {return entryPoint;} //makes a copy since coordinate is immutable.

    public final boolean isValidMove(Coordinate c) {
        if (c.x() >= 0 && c.y() >= 0 && c.x() < mapSize && c.y() < mapSize) {
            return (map[c.x()][c.y()] != null);
        }
        return false;
    }

    public Area getArea(Coordinate c) {
        return map[c.x()][c.y()];
    }

}
