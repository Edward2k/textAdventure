import java.net.Socket;

public class PlayerThread extends Thread {
    private final Player player;
    private final Game server;
    private static final int MAX_WORDS_PER_INSTRUCTION = 18;

    public PlayerThread(Socket socket, Game server, Coordinate start) {
        this.player = new Player(socket, start);
        this.server = server;
    }

    @Override
    public void run() {
        try {
            welcomePlayer();
            runPlayer();
        } catch (NullPointerException e) {
            System.err.println("The client at Thread " + Thread.currentThread().getName() + " has unexpectedly quit.\nKilling Thread");
            server.removeThread(this);
            e.printStackTrace();
            Thread.yield();
        }
    }

    private void welcomePlayer() {
        player.output("To join the VuORK realm, you need some sort of identification. So please, What is your name?");
        player.setName(player.getLine());
        player.output("Welcome " + player.getName() + " to the world of VuOrk! <Print Help by typing 'Help'> <Send a message to other players by typing 'text' + message>");
        player.output("It's a Saturday night, 3am, and you are coming from a party at Uilenstede. There has been an emergency canvas notification of professor Thilo being held against his will somewhere in the main building of the VU. You will have to save him before the sun rises. Good luck in your adventure!");
        player.output(server.getAreaDescription(player.position()));
    }

    public final void runPlayer() {
        //Loop prompt
        while(true) {
            try{
                Instruction currCommand = player.getInstruction();      //Blocking command waiting for input
                System.err.println("INSTRUCTION : " + currCommand.toString());
                while (server.isGameBusy()) {
                }                         //Wait for game to finish other process command.
                System.err.println("Command executed.");
                String result = server.validateCommand(currCommand, player);
                player.output(result);
            } catch(ArrayIndexOutOfBoundsException e){
                player.output("OoO0oPSs! It seems like your input is too long. Please try again, and use a max of" + MAX_WORDS_PER_INSTRUCTION + " words.");
            }
        }
    }

    public void output(String message){
        player.output(message);
    }

    public String getPlayerName(){
        return player.getName();
    }

}
