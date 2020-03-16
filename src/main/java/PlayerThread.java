import java.net.Socket;

public class PlayerThread extends Thread{
    private final Player player;
    private final Game server;
    private final Socket sock;

    public PlayerThread(Socket socket, Game server, Coordinate start) {
        this.player = new Player(socket, start);
        this.server = server;
        this.sock = socket;
    }

    public void run() {
        welcomePlayer();
        runPlayer();
    }

    private void welcomePlayer() {
        player.output("To join the VuORK realm, you need some sort of identification. So please, What is your name?");
        player.setName(player.getLine());
        player.output("Welcome " + player.getName() + " to the world of VuOrk! <Print Help by typing 'Help'>\n======================================\n" + server.getAreaDescription(player.position()));
    }

    public final void runPlayer() {
        //Loop prompt
        while(true) {
            player.getInstruction();
            Instruction currCommand = player.getInstruction();      //Blocking command waiting for input
            while (!server.isGameBusy()) {}                         //Wait for game to finish other process command.
            String result = server.validateCommand(currCommand, player);
            player.output(result);
        }
    }

}
