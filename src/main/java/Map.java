public class Map {

    private Area[][] map;
    private Coordinate entryPoint;

    Map () {
        entryPoint = new Coordinate(0 ,0); // maybe read from file? JSON?
        map = new Area[100][100];
        map[0][0] = new Area("penIsland", "your pen is our business");
    }

    public String getDescription(int x, int y) {
        return map[x][y].getDescription();
    }
    public Coordinate getEntryPoint() {return entryPoint;} //makes a copy since coordinate is immutable.

}
