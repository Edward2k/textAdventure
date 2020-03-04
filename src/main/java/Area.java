public class Area {

    private Item[] items;
    private Item obstacle;
    private boolean obstacleNeutralized;
    private String name;
    private String description;

    Area () {

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean canEnter() {
        return obstacleNeutralized;
    }

}
