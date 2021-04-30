package idol.tofu.client.common;

import idol.tofu.client.persistence.MovieInfo;
import idol.tofu.client.persistence.Session;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GetMovieListTask extends Task<List<MovieInfo>> {

    private final List<MovieInfo> list = new ArrayList<>();

    @Override
    protected List<MovieInfo> call() throws Exception {
        //여기서 네트워크로 대충 극장 정보를 받아 옴
        //현재는 대충 만들어서 던짐

        Socket sock = new Socket("127.0.0.1", 25252);
        PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        String requestText = "9,0\r\n";
        pw.write(requestText);
        pw.flush();
        sock.shutdownOutput();

        String response = br.readLine();
        System.out.println("[GetMovieTask] 받은 답변: " + response);
        String[] responseSplit = response.split(",");

        for (int i = 0; i < Integer.parseInt(responseSplit[2]); i++) {
            MovieInfo movie = new MovieInfo();
            for (int j = 0; j < 10; j++) {
                System.out.println(responseSplit[3+(10*i+j)]);
            }
            movie.setUid(Integer.parseInt(responseSplit[3+(10*i+0)]));
            movie.setRating(Integer.parseInt(responseSplit[3+(10*i+1)]));
            movie.setName(responseSplit[3+(10*i+2)]);
            movie.setActor(responseSplit[3+(10*i+3)]);
            movie.setGenre(responseSplit[3+(10*i+4)]);
            movie.setReleaseDateFromString(responseSplit[3+(10*i+5)]);
            movie.setStatus(responseSplit[3+(10*i+6)]);
            movie.setPrice(Integer.parseInt(responseSplit[3+(10*i+7)]));
            movie.setRuntime(Integer.parseInt(responseSplit[3+(10*i+8)]));
            movie.setDetail(responseSplit[3+(10*i+9)].replace("$comma", ","));
            list.add(movie);
        }

        // 로딩용
        //Thread.sleep(3000);
        updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("GetMovieListTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("GetMovieListTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("GetMovieListTask: FAILED!");
    }
}
