package idol.tofu.server;

import idol.tofu.server.Services.LoginService;
import idol.tofu.server.Services.MovieService;
import idol.tofu.server.Services.TheaterService;

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
            case LOG_REQ:
            case LOG_RES:
                //Login
                result = new LoginService().processRequest(request);
                break;
            case MOV_REQ:
            case MOV_RES:
                result = new MovieService().processRequest(request);
                //Movie
                break;
            case REG_REQ:
            case REG_RES:
                //Register
                break;
            case RVW_REQ:
            case RVW_RES:
                //Review
                break;
            case SCH_REQ:
            case SCH_RES:
                break;
            case SCR_REQ:
            case SCR_RES:
                break;
            case BILL_REQ:
            case BILL_RES:
                break;
            case THTR_REQ:
            case THTR_RES:
                result = new TheaterService().processRequest(request);
                break;
            case MANAGE_REQ:
            case MANAGE_RES:
                break;
            case PAYOUT_REQ:
            case PAYOUT_RES:
                break;
            case REFUND_REQ:
            case REFUND_RES:
                break;
            case TICKET_REQ:
            case TICKET_RES:
                break;
            case RESERVE_REQ:
            case RESERVE_RES:
                break;
            case SEAT_STATUS_REQ:
            case SEAT_STATUS_RES:
                break;
            default:
                System.out.println("아무래도 망했어요");
        }

        result = result + "\r\n";
        System.out.println("[ConnectionHelper] 보낸 답변: " + result);

        return result;
    }
}
