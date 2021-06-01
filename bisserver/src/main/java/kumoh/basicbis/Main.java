package kumoh.basicbis;

import kumoh.basicbis.services.BusService;

public class Main {

    public static int PORT_NUMBER = 25252;
    public static ConnectionHelper conn;

    public static void main(String[] args) {
        System.out.println("Tofu babo...");
        System.out.println("Creative project bis server on");

        if (args.length > 1) {
            PORT_NUMBER = Integer.valueOf(args[1]);
        }

        System.out.println("Running on port " + PORT_NUMBER);

        conn = new ConnectionHelper();
        conn.startServer(PORT_NUMBER);
    }
}
