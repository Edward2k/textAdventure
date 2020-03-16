import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Printer {
    private PrintWriter send;

    public Printer(Socket s) {
        try {
            OutputStream outStream = s.getOutputStream();
            send = new PrintWriter(outStream, true);
        } catch (IOException e) {
            System.out.println("Could not create player output");
            e.printStackTrace();
        }
    }

    void output(String output) {
       send.println(output);
    }

}
