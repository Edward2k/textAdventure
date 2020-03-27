import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Game {

	private static Map map;
	private static int PORT;
	private boolean isBusy; //Spin lock in case 2 commands at same time.
	private static String north = "north";
	private static String south = "south";
	private static String west = "west";
	private static String east = "east";
	private static List<PlayerThread> users;

	Game() {
		map = new Map();
		PORT = 1234;
		isBusy = false;
		users =  new ArrayList<PlayerThread>();
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
				users.add(new PlayerThread(sock, this, map.getEntryPoint()));
				users.get(users.size()-1).start();
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

	public void removeThread(PlayerThread pt) {
		users.remove(pt); //should remove object that has same mem address.
	}

	public String getAreaDescription(Coordinate c) {return map.getDescription(c);}

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
				result = listCommands();
				break;
			case "drop":
				result = handleItem(command.getItems().get(0), player, player.getBackpack(), action);
				break;
			case "take":
			case "get":
			case "pickup":
				result = handleItem(command.getItems().get(0), player, map.getArea(player.position()).getItems(), action);
				break;
			case "give":
				result = giveItem(command.getItems(), player);
				break;
			case "score":
				result = "Your score is currently: " + player.getScore();
				break;
			case "text":
			case "deliver":
			case "shout":
			case "broadcast":
				deliverMessage(command, player.getName());
				result = "message delivered ";
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

	private static Item hasItem(List<Item> contents, String item) {
		Item found = null;
		for(Item i : contents) {
			if(i.getName().equals(item)) {
				found = i;
			}
		}
		return found;
	}

	private static String giveItem(List<String> items, Player player) {
		try {
			String giveWhat = items.get(0);
			String toWhat = items.get(2);

			if (!items.get(1).equals("to")) {
				return "I do not understand what you mean";
			}
			if (hasItem(player.getBackpack(), giveWhat) == null) {
				return "You do not have a " + giveWhat + " in your backpack.";
			}
			if (toWhat != null && !neutralizeObstacles(player.position(), giveWhat, toWhat)) {
				return "There is no " + toWhat + " in this room.";
			}
		} catch (NullPointerException e) {
			return "I do not understand what you mean";
		}
		return "You have neutralized the obstacle, cool! \n" + map.getDescription(player.position());
	}

	//TODO: This needs to be simplified :O
	private static boolean neutralizeObstacles(Coordinate coord, String toNeutralize, String who) {
		Area area = map.getArea(coord);
		if (area.getObstacle() != null && area.getObstacle().getName().equals(who) &&
				area.getObstacle().getHowToNeutralize().equals(toNeutralize))
		{
			area.setObstacle(null);
			return true;
		}
		return false;
	}

	//TODO: Stick to the switchcase style used in handle command(). This will make it clearer.
	private static String handleItem(String item, Player player, List<Item> contents, String action) {
		Item toRemove = hasItem(contents, item);
		String result;
		if(toRemove == null) {
			switch(action) {
				case "drop":
					result = "You cannot drop what you do not have, there is not " + item + " in your backpack.";
					break;
				default:
					result = "I don't see " + item + " anywhere in here.";
			}
		} else {
			switch (action) {
				case "drop":
					map.getArea(player.position()).addItem(toRemove);
					player.removeItem(toRemove);
					result = "Your backpack is so much lighter when there is no " + item + " in it.";
					break;
				default:
					map.getArea(player.position()).removeItem(toRemove);
					player.addItem(toRemove);
					result = "Nice! You now have a " + item + " in your backpack";
			}
		}
		return result;
	}

	private static String handleMove(String direction, Player player) {
		Coordinate newPos;
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

		//If there is an obstacle, you can only go back the way you came.
		if (map.hasObstacles(player.position()) && !oppositeDirection(player.getLastValidDirection()).equals(direction)) {
			return "You can only proceed by neutralizing the obstacle or going back the direction you came from: " +
					"<" + oppositeDirection(player.getLastValidDirection()) + ">";
		}
		if (map.isValidMove(newPos)) {
			player.movePlayer(newPos);
			player.setLastValidDirection(direction);
			return map.getDescription(newPos);
		} else {
			if (map.isValidMove(newPos)) { return map.getArea(newPos).getObstacle().getDescription();}
			return ("There is nothing " + direction + " of where you are now.");
		}
	}

	private static boolean isDirection(String d) {
		return d.equals(north) || d.equals(south) || d.equals(west) || d.equals(east);
	}

	private static String getHelpInstructions(Player player) {
		return "I see you are a noob, " + player.getName() + ". You are in the VuORK realm. To interact " +
				"with this realm, you must type a command with the form" +
				"<action> <item> <proposition> <item>. An example would be 'Attack broom with sword'. For movement," +
				"there are 2 ways to move: 'move' followed by either 'north', 'south, 'east' or 'west'. You can also " +
				"simply type 'south' or any other direction as a shortcut. By typing 'text'<message> you can send a " +
				"message to your fellow players. It is important to note that all inputs are NOT case sensitive!" +
				"For a full list of commands, type 'List' or 'l'. Good luck and have fun in VuORK";
	}

	private static String oppositeDirection(String d) {
		switch(d) {
			case "north":
				return south;
			case "south":
				return north;
			case "west":
				return east;
			case "east":
				return west;
			default:
				System.err.println("AN INVALID DIRECTION WAS GIVEN TO OPPOSITE DIRECTION"); //TODO: throw error
		}
		return "";
	}

	//deliver message to all players
	private static void deliverMessage(Instruction command, String senderName){
		String message = createMessage(command.getItems().toString(), senderName);
		for(PlayerThread player: users){
			if(!player.getPlayerName().equals(senderName)){
				player.output(message);
			}
		}
	}

	private static String createMessage(String msg, String name){
		msg = msg.replace(",", "").replace("null", "").replace("[", "").replace("]", "");
		return name.toUpperCase() + ": " + msg;
	}

	private static String listCommands() {
		return "Below is a list of all valid commands and their arguments: \n" +
				"move + north/south/east/west. => same as just the argument, move can be ommitted \n" +
				"look => gives description of Area and all Items present\n" +
				"list => gives this menu\n" +
				"drop <item name> => drops itemName from your backpack to the Area\n" +
				"take/get/pickup <item name> => takes Item from Area and places it in your packpack\n" +
				"give <item name> to <obstacle/playername> => gives Item to the given name\n" +
				"score => displays your current score\n" +
				"text/deliver/shout/broadcast <msg> => will send every other user in the Server msg\n" +
				"help/h => gives a brief help menu\n" +
				"More will be implemented in due time\n";

	}



}