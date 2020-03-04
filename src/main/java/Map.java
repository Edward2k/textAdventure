public class Map {

    private Area[][] map;

    Map () {
        map = new Area[100][100];
        map[0][0] = new Area("penIsland", "your pen is our business");
    }

    public String getDescription(int x, int y) {
        return map[x][y].getDescription();
    }

}
