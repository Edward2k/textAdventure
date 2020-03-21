import java.util.ArrayList;

public class Obstacle {
    private boolean obstacleNeutralized;
    private int health;
    private String name;
    private String description;
    private String howToNeutralize;

    Obstacle (String name, String description, String neutralize) {
        this.name = name;
        this.description = description;
        this.health = 100;
        this.obstacleNeutralized = false;
        this.howToNeutralize = neutralize;
    }

    public boolean isNeutralized() {return obstacleNeutralized;}

    public int obstacleHealth() {return health;}

    public void attackObstacle(int damage) {
        health = health - damage;
        if(health <= 0) { obstacleNeutralized = true;}
    }

    public String getName() { return this.name;}

    public String getDescription() { return this.description;}

    public String getHowToNeutralize() { return this.howToNeutralize;}
}
