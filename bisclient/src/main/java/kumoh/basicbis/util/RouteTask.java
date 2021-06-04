package kumoh.basicbis.util;

import javafx.concurrent.Task;
import kumoh.basicbis.persistence.RouteInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RouteTask extends Task<List<RouteInfo>> {
    RequestManager manager = new RequestManager();
    private final List<RouteInfo> list = new ArrayList<>();
    String query = "";

    public RouteTask() {}

    public RouteTask(String query) { this.query = query;}

    @Override
    protected List<RouteInfo> call() throws Exception {

        String response = manager.sendRequest("2,1," + query + "\r\n");
        //System.out.println("[RouteTask] 받은 답변: " + response);

        String[] responseSplit = response.split("\r\n");

        for (int i = 0; i < responseSplit.length; i++) {
            String[] lineSplit = responseSplit[i].split(",");
            RouteInfo route = new RouteInfo();

            route.setUid(lineSplit[0]);
            route.setId(lineSplit[1]);
            route.setName(lineSplit[2]);

            list.add(route);
        }

        // 로딩용
        //Thread.sleep(3000);
        //updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("RouteTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("RouteTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("RouteTask: FAILED!");
    }
}
