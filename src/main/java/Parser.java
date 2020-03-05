import java.util.Arrays;
import java.util.Scanner;

public class Parser {

	private static final int MAX_WORDS_PER_COMMAND = 5;
	private static final int START_OF_ITEMS = 1;
	private static final int ACTION_WORD_POSITION = 0;

	//Returns a String array where first element is action.
	public String getLine() {
		Scanner in = new Scanner(System.in);
		return in.nextLine();
	}

	public String[] getLineArray() {
		Scanner parseScanner = new Scanner(System.in);
		String line = parseScanner.nextLine();
		line = line.toLowerCase();
		Scanner lineScanner = new Scanner(line);
		String[] command = new String[MAX_WORDS_PER_COMMAND];
		int i = 0;
		while(lineScanner.hasNext()) {
			command[i++] = lineScanner.next();
		}
		return command;
	}

	public Instruction getInstruction() {
		String[] parts = getLineArray();
		String act = parts[ACTION_WORD_POSITION]; //Get first element for action
		parts = Arrays.copyOfRange(parts, START_OF_ITEMS, MAX_WORDS_PER_COMMAND); //Each command can have max 4 words.
		Instruction command = new Instruction(act, parts);
		return command;
	}

}
