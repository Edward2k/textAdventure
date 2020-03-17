import java.net.Socket;

public class PlayerThread extends Thread{
    private final Player player;
    private final Game server;

    public PlayerThread(Socket socket, Game server, Coordinate start) {
        this.player = new Player(socket, start);
        this.server = server;
    }

    @Override
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
            Instruction currCommand = player.getInstruction();      //Blocking command waiting for input
            System.err.println("INSTRUCTION : " + currCommand.toString());
            while (server.isGameBusy()) {}                         //Wait for game to finish other process command.
            System.err.println("Command executed.");
            String result = server.validateCommand(currCommand, player);
            player.output(result);
        }
    }

}
