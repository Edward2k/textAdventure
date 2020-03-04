public class Area {

    private Item[] items;
    private Item obstacle;
    private boolean obstacleNeutralized;
    private String name;
    private String description;

    Area (String name, String description) {
        this.name = name;
        this.description = description;
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
