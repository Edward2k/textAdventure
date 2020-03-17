package PlayerClient;

import java.net.*;
import java.io.*;

public class PlayerClient {
    private String hostname;
    private int port;
    private String userName;

    public PlayerClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");
            new ReadThread(socket).start(); //Start threads.
            new WriteThread(socket).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }

    }

    public static void main(String[] args) {
        PlayerClient client = new PlayerClient("127.0.0.1", 1234);
        client.execute();
    }


}





