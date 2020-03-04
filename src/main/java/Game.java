import java.util.Arrays;
import java.util.Scanner;


public class Game {
	
	public Player[] gamePlayer;

	private Map map;
	
	Game(){
		gamePlayer = new Player[100];
		map = new Map();
	}
	
	void startGame() {

		Coordinate startingCoord = new Coordinate(0 ,0); // maybe read from file? JSON?



		gamePlayer[0] = new Player("dabber69", startingCoord);



		while(true) {
			System.out.println(Arrays.toString(gamePlayer[0].getCommand()));
			System.out.println("==================================================");
		}
	}
	
	public static void main(String args[]) {
		new Game().startGame();
	}

}
