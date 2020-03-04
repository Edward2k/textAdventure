import java.util.Scanner;

public class Parser {

	public String[] getCommand() {
		Scanner parseScanner = new Scanner(System.in);
		String line = parseScanner.nextLine();
		Scanner lineScanner = new Scanner(line);
		String[] command = new String[100];
		int i = 0;
		while(lineScanner.hasNext()) {
			command[i++] = lineScanner.next();
		}
		return command;
	}

}
