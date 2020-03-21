import java.util.ArrayList;

public final class Coordinate {

    private final int xCoord;
    private final int yCoord;

    Coordinate(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public final int x() {
        return xCoord;
    }

    public final int y() {
        return yCoord;
    }
}
