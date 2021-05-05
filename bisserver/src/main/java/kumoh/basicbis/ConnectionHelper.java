package kumoh.basicbis;

import kumoh.basicbis.services.BusStopService;
import kumoh.basicbis.services.FoodService;
import kumoh.basicbis.services.RouteService;

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

    private String passWorkToService(String request) {
        String result = null;
        int typeCode;
        ProtocolType type = ProtocolType.UNKNOWN;

        System.out.println("[ConnectionHelper] 받은 요청: " + request);

        String[] splitLines = request.split(",");
        try{
            typeCode = Integer.parseInt(splitLines[0]);
            type = ProtocolType.values()[typeCode];
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (type) {
            case FOOD_REQ:
            case FOOD_RES:
                result = new FoodService().processRequest(request);
            case ROUTE_REQ:
            case ROUTE_RES:
                result = new RouteService().processRequest(request);
            case BUS_STOP_REQ:
            case BUS_STOP_RES:
                result = new BusStopService().processRequest(request);
            default:
                System.out.println("아무래도 망했어요");
        }

        result = result + "\r\n";
        System.out.println("[ConnectionHelper] 보낸 답변: " + result);

        return result;
    }
}