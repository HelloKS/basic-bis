package kumoh.basicbis;

import kumoh.basicbis.services.BusStopService;
import kumoh.basicbis.services.FoodService;
import kumoh.basicbis.services.RouteService;
import kumoh.basicbis.services.TimeTableService;

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
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                os = clientSock.getOutputStream();
                pw = new PrintWriter(new BufferedWriter((new OutputStreamWriter(os, "UTF-8"))));
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

            // 서버 -> 클라이언트

            String serviceResult = passWorkToService(line);
            String svcSplit[] = serviceResult.split("\r\n");

            for (String entry : svcSplit) {
                //System.out.println("[ConnectionHelper] 전송: " + entry);
                pw.write(entry + "\r\n");
                pw.flush();
            }

            //pw.write("EOF\r\n");
            //pw.flush();

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

        String header = request.split("\r\n")[0];
        try{
            String[] header_split = header.split(",");
            typeCode = Integer.parseInt(header_split[0]);
            type = ProtocolType.values()[typeCode];
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (type) {
            case FOOD_REQ:
                result = new FoodService().processRequest(request);
                break;
            case ROUTE_REQ:
                result = new RouteService().processRequest(request);
                break;
            case BUS_STOP_REQ:
                result = new BusStopService().processRequest(request);
                break;
            case TIMETABLE_REQ:
                result = new TimeTableService().processRequest(request);
                break;
            default:
                System.out.println("아무래도 망했어요");
                break;
        }

        result = result + "\r\n";
        //System.out.println("[ConnectionHelper] 보낸 답변: " + result);

        return result;
    }
}