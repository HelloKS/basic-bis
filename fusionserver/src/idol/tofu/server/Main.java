package idol.tofu.server;

public class Main {

    public static int PORT_NUMBER = 25252;
    public static ConnectionHelper conn;

    public static void main(String[] args) {
        System.out.println("Fusion project server starting...");
        System.out.println("Powered by Tofu(tm)");

        if (args.length > 1) {
            PORT_NUMBER = Integer.valueOf(args[1]);
        }

        System.out.println("Running on port " + PORT_NUMBER);

        conn = new ConnectionHelper();
        conn.startServer(PORT_NUMBER);
    }
}
