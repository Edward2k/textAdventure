import java.util.Scanner;

public class Main {
	
	Parser gameParser;
	Player gamePlayer;
	
	Main(){
		gameParser = new Parser();
		gamePlayer = new Player();
	}
	
	void startGame() {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			gameParser.parse(scanner.nextLine());
		}
	}
	
	public static void main(String args[]) {
		new Main().startGame();
	}

}
