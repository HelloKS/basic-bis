package kumoh.basicbis.util;

import kumoh.basicbis.prototest.ProtocolType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestManager {
    public String sendRequest(String csvRequest) {
        String result = "";

        try {
            Socket sock = new Socket("127.0.0.1", 25252);
            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            pw.write(csvRequest);
            pw.flush();
            sock.shutdownOutput();

            result = br.readLine();
            sock.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
