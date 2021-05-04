import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHelper {

    ServerSocket srvSock;

    public ConnectionHelper() {
    }

    public void startServer(int port) {
        try {
            srvSock = new ServerSocket(port);
            while (!srvSock.isClosed()) {
                Socket sock = srvSock.accept();

                serverWork work = new serverWork(sock);
                work.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class serverWork extends Thread {
        private Socket clientSock;

        public serverWork(Socket sock) {
            this.clientSock = sock;
        }

        public void run() {
            InputStream is = null;
            OutputStream os = null;
            BufferedReader br = null;
            PrintWriter pw = null;

            try {
                is = clientSock.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                os = clientSock.getOutputStream();
                pw = new PrintWriter(os, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (is == null || os == null) {
                return;
            }

            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            pw.write(passWorkToService(line));
            pw.flush();

            if (br != null && pw != null) {
                try {
                    br.close();
                    pw.close();
                    clientSock.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}