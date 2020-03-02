import java.util.Scanner;

public class Parser {

	public void parse(String string) {

		Scanner commandScanner = new Scanner(string);
		if (commandScanner.hasNext()) {
			String command = commandScanner.next();
			String arg;
			
			if (!commandScanner.hasNext()) {
				System.out.println("what do you want to " + command + "?");
				return;
			} else {
				arg = commandScanner.nextLine().trim().replaceAll(" +", " "); // replace all multiple spaces with a single one
			}
			
			switch (command) {
			case "move":
				move(arg);
				break;
			case "kill":
				kill(arg);
				break;
			case "use":
				use(arg);
				break;
			default:
				System.out.println("command not recongnised");
			}
			
		} else {
			System.out.println("command expected");
		}
	}
	
	private void move(String arg) {
		switch (arg) {
			case "north":
				System.out.println("moved " + arg);
				break;
			case "east":
				System.out.println("moved " + arg);
				break;
			case "south":
				System.out.println("moved " + arg);
				break;
			case "west":
				System.out.println("moved " + arg);
				break;
			default:
				System.out.println("direction not recognised, choose north, east, south or west.");
		}
	}
	
	private void kill(String arg) {
		System.out.println(arg + " was killed");
	}
	
	private void use(String arg) {
		System.out.println(arg + " was used");
	}
	
}
