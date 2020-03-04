public final class Coordinate {

    private final int xCoord;
    private final int yCoord;

    Coordinate(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public final int getx() {
        return xCoord;
    }

    public final int gety() {
        return yCoord;
    }
}
