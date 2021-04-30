package idol.tofu.client.common;

import idol.tofu.client.persistence.MovieInfo;
import idol.tofu.client.persistence.Session;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LoginTask extends Task<Session> {

    private String id, pass;

    public LoginTask(String LoginId, String LoginPw) {
        id = LoginId;
        pass = LoginPw;
    }

    @Override
    protected Session call() throws Exception {
        Session session;

        Socket sock = new Socket("127.0.0.1", 25252);
        PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        String requestText = "3,1," + id + "," + pass + "\r\n";
        pw.write(requestText);
        pw.flush();
        sock.shutdownOutput();

        String response = br.readLine();
        System.out.println("[LoginTask] 받은 답변: " + response);
        String[] responseSplit = response.split(",");

        if ("1".equals(responseSplit[1])) {
            session = new Session(id);
            if ("T".equals(responseSplit[2])) {
                session.setManager(true);
            }
        } else if ("2".equals(responseSplit[1])) {
            throw new Exception("로그인 실패");
        } else {
             throw new Exception("알려지지 않은 예외");
        }

        updateProgress(1, 1);

        return session;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("LoginTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("LoginTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("LoginTask: FAILED!");
    }
}
