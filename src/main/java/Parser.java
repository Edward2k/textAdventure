import java.util.Arrays;
import java.util.Scanner;

public class Parser {

	private static final int MAX_WORDS_PER_COMMAND = 5;
	private static final int START_OF_ITEMS = 1;
	private static final int ACTION_WORD_POSITION = 0;

	//Returns a String array where first element is action.
	protected String getLine(Interface gameInterface) {
//		Scanner in = new Scanner(System.in);
//		return in.nextLine();
		String input = gameInterface.getInput();
		return input;
	}

	private String[] getLineArray(String text) {
		text = text.toLowerCase();
		String[] command = text.split(" ");
		return command;
	}

	protected Instruction getInstruction(Interface gameInterface) {
		String text = gameInterface.getInput();
		String[] parts = getLineArray(text);
		while (parts[0] == null) {
			text = gameInterface.getInput();
			parts = getLineArray(text);
		}
		String act = parts[ACTION_WORD_POSITION]; //Get first element for action
		parts = Arrays.copyOfRange(parts, START_OF_ITEMS, MAX_WORDS_PER_COMMAND); //Each command can have max 4 words.
		Instruction command = new Instruction(act, parts);
		return command;
	}

}
