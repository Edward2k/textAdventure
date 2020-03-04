import java.util.Scanner;

public class Game {
	
	private Parser gameParser;
	public Player[] gamePlayer;
	
	Game(){
		gameParser = new Parser();
		gamePlayer = new Player[100];
	}
	
	void startGame() {

		Coordinate startingCoord = new Coordinate(0 ,0); // maybe read from file? JSON?

		Scanner parseScanner = new Scanner(System.in);

		gamePlayer[0] = new Player("dabber69", startingCoord);

		while(true) {
			gameParser.parse(parseScanner.nextLine());
			System.out.println("==================================================");
		}
	}
	
	public static void main(String args[]) {
		new Game().startGame();
	}

}
