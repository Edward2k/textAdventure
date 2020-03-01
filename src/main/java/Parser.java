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
				arg = commandScanner.nextLine().trim().replaceAll(" +", " ");
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
			return;
		}
		
	}
	
	private void move(String arg) {
		System.out.println("moved " + arg);
	}
	
	private void kill(String arg) {
		System.out.println(arg + " was killed");
	}
	
	private void use(String arg) {
		System.out.println(arg + " was used");
	}
	
}
