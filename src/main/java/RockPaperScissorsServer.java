/**
 * Created by mustafaiman on 22/10/15.
 */
public class RockPaperScissorsServer {

    public static void main(String[] args) {
        try {
            if(args.length != 1) {
                System.out.println("Wrong number of arguments. Usage:\n"
                        + "\tRockPaperScissors port-number");
            } else {
                GameServer server = new GameServer(Integer.parseInt(args[0]));
                server.runServer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
