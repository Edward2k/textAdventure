import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class Parser {

	private static final int MAX_WORDS_PER_COMMAND = 5;
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

	protected Instruction getInstruction(String text) {
		String[] parts = text.split(" ");
		String act = parts[ACTION_WORD_POSITION]; //Get first element for action
		parts = Arrays.copyOfRange(parts, START_OF_ITEMS, MAX_WORDS_PER_COMMAND); //Each command can have max 4 words.
		return new Instruction(act, parts);
	}

}