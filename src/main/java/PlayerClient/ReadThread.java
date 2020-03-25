package PlayerClient;

import java.io.*;
import java.net.*;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private Interface inter;

    public ReadThread(Socket socket, Interface clientInterface) {
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            inter = clientInterface;
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                if (response == null) {
                    System.out.println("The server has unexpectedly quit.");
                    System.exit(-1);
                }
                inter.append(response);
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}
