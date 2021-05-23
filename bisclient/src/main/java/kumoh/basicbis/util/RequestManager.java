package kumoh.basicbis.util;

import java.io.*;
import java.net.Socket;

public class RequestManager {
    public String sendRequest(String csvRequest) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            Socket sock = new Socket("127.0.0.1", 25252);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8")));
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));

            pw.write(csvRequest);
            pw.flush();
            sock.shutdownOutput();

            String line = "";
            while ((line = br.readLine()) != null) {
                //System.out.println("[RequestManager] 수신: " + line);
                stringBuilder.append(line).append("\r\n");
            }
            sock.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
