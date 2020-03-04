import java.util.List;


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
			executeCommand(currCommand, player);
		}

	}

	public static void executeCommand(Instruction command, Player player) {
		String action = command.getAction().toLowerCase();
		switch(action) {
			case "move":
				handleMove(command.getItems(), player);
				break;
			default:
				player.output("I do not understand " + action);
		}
	}

	private static void handleMove(List<String> items, Player player) {
		String direction = items.get(0).toLowerCase();
		switch (direction) {
			case "north":
				player.output("You are going north");
				break;
			case "south":
				player.output("You are going south");
				break;
			case "west":
				player.output("You are going west");
				break;
			case "east":
				player.output("You are heading east");
				break;
			default:
				player.output(" I do not know to move " + direction);
				break;
		}
	}



}
