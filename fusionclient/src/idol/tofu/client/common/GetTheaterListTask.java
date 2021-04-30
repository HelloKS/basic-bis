package idol.tofu.client.common;

import idol.tofu.client.persistence.MovieInfo;
import idol.tofu.client.persistence.TheaterInfo;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GetTheaterListTask extends Task<List<TheaterInfo>> {

    private final List<TheaterInfo> list = new ArrayList<>();

    @Override
    protected List<TheaterInfo> call() throws Exception {
        Socket sock = new Socket("127.0.0.1", 25252);
        PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        String requestText = "5,0\r\n";
        pw.write(requestText);
        pw.flush();
        sock.shutdownOutput();

        String response = br.readLine();
        System.out.println("[GetMovieTask] 받은 답변: " + response);
        String[] responseSplit = response.split(",");

        for (int i = 0; i < Integer.parseInt(responseSplit[2]); i++) {
            TheaterInfo theater = new TheaterInfo();
            for (int j = 0; j < 6; j++) {
                System.out.println(responseSplit[3+(6*i+j)]);
            }
            theater.setUid(Integer.parseInt(responseSplit[3+(6*i+0)]));
            theater.setName(responseSplit[3+(6*i+1)]);
            theater.setAddress(responseSplit[3+(6*i+2)]);
            theater.setPhoneNumber(responseSplit[3+(6*i+3)]);
            theater.setBank(Integer.parseInt(responseSplit[3+(6*i+4)]));
            theater.setAccountNumber(responseSplit[3+(6*i+5)]);

            list.add(theater);
        }

        // 로딩용
        //Thread.sleep(3000);
        updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("GetTheaterListTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("GetTheaterListTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("GetTheaterListTask: FAILED!");
    }
}
