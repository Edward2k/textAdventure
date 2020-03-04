import java.util.List;


public class Game {
	
	private Player[] gamePlayer;
	private static Map map;
	
	Game(){
		gamePlayer = new Player[4];
		map = new Map();
	}
	
	void startGame() {
		//For the purpose of this game, we will be using only 1 player. multiplayer is easily expandable
		gamePlayer[0] = new Player(map.getEntryPoint());
		runPlayer(gamePlayer[0]);
	}
	
	public static void main(String args[]) {
		new Game().startGame(); //Only 1 player
	}

	public static final void runPlayer(Player player) {
		//LoginUserName
		player.output("To join the VuORK realm, you need some sort of identification. So please, What is your name?");
		player.setName(player.getLine());
		player.output("Welcome " + player.getUserName() + " to the world of VuOrk! <Print Help by typing 'Help'>\n======================================\n");
		player.output(map.getDescription(player.position()));

		//Loop prompt
		while(true) {
			Instruction currCommand = player.getCommand(); //Blocking command waiting for input
			validateCommand(currCommand, player); //validateCommand
		}
	}

	public static void validateCommand(Instruction command, Player player) {
		String action = command.getAction();
		//Movement shortcut
		if (isDirection(action)) {handleMove(action, player); return;}
		switch(action) {
			case "move":
				handleMove(command.getItems().get(0), player);
				break;
			case "look":
				player.output(map.getDescription(player.position()));
				break;
			case "list":
				player.output("Need to implement this list later.");
				break;
			case "help":
			case "h":
				giveHelpInstructions(player);
				break;
			default:
				player.output("I do not understand " + action);
		}
	}

	private static void handleMove(String direction, Player player) {
		Coordinate newPos = null;
		switch (direction) {
			case "north":
				newPos = new Coordinate(player.position().x() - 1, player.position().y());
				break;
			case "south":
				newPos = new Coordinate(player.position().x() + 1, player.position().y());
				break;
			case "west":
				newPos = new Coordinate(player.position().x(), player.position().y() - 1);
				break;
			case "east":
				newPos = new Coordinate(player.position().x(), player.position().y() + 1);
				break;
			default:
				player.output(" I do not know to move in the direction '" + direction + "'");
				return; //do not continue execution of this function
		}

		if (map.isValidMove(newPos)) {
			player.movePlayer(newPos);
			player.output(map.getDescription(newPos));
		} else {
			player.output("There is nothing " + direction + " of where you are now.");
		}

	}


	private static boolean isDirection(String d) {
		return d.equals("north") || d.equals("south") || d.equals("west") || d.equals("east");
	}

	private static void giveHelpInstructions(Player player) {
		player.output("I see you are a noob, " + player.getUserName() + ". You are in the VuORK realm. To interact " +
				"with this realm, you must type a command with the form" +
				"<action> <item> <proposition> <item>. An example would be 'Attack broom with sword'. For movement," +
				"there are 2 ways to move: 'move' followed by either 'north', 'south, 'east' or 'west'. You can also " +
				"simply type 'south' or any other direction as a shortcut. It is important to note that all inputs are " +
				"NOT case sensitive! For a full list of commands, type 'List'. Good luck and have fun in VuORK");
	}


}
