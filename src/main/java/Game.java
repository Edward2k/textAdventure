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
		gamePlayer[0] = new Player(map.getEntryPoint());
		runPlayer(gamePlayer[0]);
	}
	
	public static void main(String args[]) {
		new Game().startGame(); //Only 1 player
	}

	public static final void runPlayer(Player player) {
		//LoginUserName
		player.output("What is your name?");
		player.setName(player.getLine());
		player.output("Welcome " + player.getUserName() + " to the world of VuOrk! <PRINT DESCRIPTION ABOUT ENTRY POINT HERE!");

		//Loop prompt
		while(true) {
			Instruction currCommand = player.getCommand();
			//Here you validate and run command
			player.output(currCommand.getAction());
			player.output(Arrays.toString(currCommand.getStringArrayItems()));
			player.output("==================================================");
		}

	}




}
