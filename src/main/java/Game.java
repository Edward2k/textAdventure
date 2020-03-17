import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Game {

	private static Map map;
	private static java.util.Date timestamp; //TODO : Neverused
	private static int PORT;
	private boolean isBusy; //Spin lock in case 2 commands at same time.

	Game() {
		map = new Map();
		timestamp = new java.util.Date();
		PORT = 1234;
		isBusy = false;
	}

	public boolean isGameBusy() {
		return isBusy;
	}

	private void runServer(){
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			while (true) {
				Socket sock = serverSocket.accept();
				System.err.println("New user connected.");
				//open new thread for new socket
				PlayerThread newUser = new PlayerThread(sock, this, map.getEntryPoint());
				newUser.start();
			}
		} catch (IOException e) {
			System.err.println("Error in server: " + e);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Ready to connect.");
		new Game().runServer();
	}

	public String getAreaDescription(Coordinate c) {
		return map.getDescription(c);
	}

	/*
	** Move this somewhere else. Not here.
	 */

	public String validateCommand(Instruction command, Player player) {
		isBusy = true; //enable lock.
		String action = command.getAction();
		String result;
		switch(action) {
			case "move":
				result = handleMove(command.getItems().get(0), player);
				break;
			case "look":
				result = (map.getDescription(player.position()));
				break;
			case "list":
				result = ("Need to implement this list later.");
				break;
			case "help":
			case "h":
				result = getHelpInstructions(player);
				break;
			default:
				result = ("I do not understand " + action);
		}

		//Movement shortcut
		if (isDirection(action)) { result = handleMove(action, player); }
		isBusy = false; //release lock.
		return result;

	}

	private static String handleMove(String direction, Player player) {
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
				return(" I do not know to move in the direction '" + direction + "'");
		}

		if (map.isValidMove(newPos)) {
			player.movePlayer(newPos);
			return map.getDescription(newPos);
		} else {
			return ("There is nothing " + direction + " of where you are now.");
		}

	}

	private static boolean isDirection(String d) {
		return d.equals("north") || d.equals("south") || d.equals("west") || d.equals("east");
	}

	private static String getHelpInstructions(Player player) {
		return "I see you are a noob, " + player.getName() + ". You are in the VuORK realm. To interact " +
				"with this realm, you must type a command with the form" +
				"<action> <item> <proposition> <item>. An example would be 'Attack broom with sword'. For movement," +
				"there are 2 ways to move: 'move' followed by either 'north', 'south, 'east' or 'west'. You can also " +
				"simply type 'south' or any other direction as a shortcut. It is important to note that all inputs are " +
				"NOT case sensitive! For a full list of commands, type 'List'. Good luck and have fun in VuORK";
	}


}
