package kumoh.basicbis.util;

import javafx.concurrent.Task;
import kumoh.basicbis.persistence.RouteInfo;
import kumoh.basicbis.persistence.RouteLinkInfo;

import java.util.ArrayList;
import java.util.List;

public class RouteLinkTask extends Task<List<RouteLinkInfo>> {
    RequestManager manager = new RequestManager();
    private final List<RouteLinkInfo> list = new ArrayList<>();
    String query = "";

    public RouteLinkTask() {}

    public RouteLinkTask(String query) { this.query = query;}

    @Override
    protected List<RouteLinkInfo> call() throws Exception {

        String response = manager.sendRequest("2,4," + query + "\r\n");
        //System.out.println("[RouteTask] 받은 답변: " + response);

        String[] responseSplit = response.split("\r\n");

        for (int i = 0; i < responseSplit.length; i++) {
            String[] lineSplit = responseSplit[i].split(",");
            RouteLinkInfo routeLink = new RouteLinkInfo();

            routeLink.setRouteOrder(Integer.parseInt(lineSplit[0]));
            routeLink.setBusStopUid(Integer.parseInt(lineSplit[1]));
            routeLink.setBusStopName(lineSplit[2]);

            list.add(routeLink);
        }

        // 로딩용
        //Thread.sleep(3000);
        //updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("RouteLinkTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("RouteLinkTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("RouteLinkTask: FAILED!");
    }
}
