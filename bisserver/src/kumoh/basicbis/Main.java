package kumoh.basicbis;

public class Main {

    public static void main(String[] args) {
	// write your code here
        public static int PORT_NUMBER = 7389;
        public static ConnectionHelper conn;

        public static void main(String[] args) {
            System.out.println("Gumi_BIS is running");
            System.out.println("Powered by GOD");

            if (args.length > 1) {
                PORT_NUMBER = Integer.valueOf(args[1]);
            }

            System.out.println("Running on port " + PORT_NUMBER);

            conn = new ConnectionHelper();
            conn.startServer(PORT_NUMBER);
        }
    }
}
