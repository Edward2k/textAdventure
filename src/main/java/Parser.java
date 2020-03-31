import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Parser {

	private static final int MAX_WORDS_PER_INSTRUCTION = 18;
	private static final int START_OF_ITEMS = 1;
	private static final int ACTION_WORD_POSITION = 0;
	private BufferedReader input;

	public Parser(Socket s) {
		try {
			input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			System.out.println("Can not create Input stream from Socket.");
			e.printStackTrace();
		}
	}

	//Returns a String array where first element is action.
	protected String getLine() {
		String message = "";
		try {
			while (message.isEmpty()) {
				message = input.readLine();
			}
			return message;
		} catch (IOException e) {
			System.out.println("Could not get Input : " + e.getMessage());
			return "";
		}
	}

	private String[] getLineArray() {
		String line = getLine();
		line = line.toLowerCase();
		Scanner lineScanner = new Scanner(line);
		String[] command = new String[MAX_WORDS_PER_INSTRUCTION];
		int i = 0;
			while (lineScanner.hasNext()) {
				command[i++] = lineScanner.next();
			}
			return command;
	}

	protected Instruction getInstruction() {
		String[] parts = getLineArray();
		while (parts[0] == null) {
			parts = getLineArray();
		}
		String act = parts[ACTION_WORD_POSITION]; //Get first element for action
		parts = Arrays.copyOfRange(parts, START_OF_ITEMS, MAX_WORDS_PER_INSTRUCTION); //Each command can have max 4 words.
		return new Instruction(act, parts);
	}

}