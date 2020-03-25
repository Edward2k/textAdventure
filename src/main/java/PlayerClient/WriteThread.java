package PlayerClient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket sock;
    private Interface inter;

    public WriteThread(Socket socket, Interface clientInterface) {
        this.sock = socket;
        this.inter = clientInterface;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        String text;

        do {
            text = inter.getInput();

            writer.println(text);

        } while (!text.equalsIgnoreCase("quit!"));

        try {
            sock.close();
        } catch (IOException ex) {
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}