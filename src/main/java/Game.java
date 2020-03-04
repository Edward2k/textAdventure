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



		gamePlayer[0] = new Player(startingCoord);

		while(true) {
			Player player = gamePlayer[0];
			Instruction currCommand = player.getCommand();
			player.output(currCommand.getAction());
			player.output(Arrays.toString(currCommand.getStringArrayItems()));
			player.output("==================================================");
		}
	}
	
	public static void main(String args[]) {
		new Game().startGame();
	}

}
