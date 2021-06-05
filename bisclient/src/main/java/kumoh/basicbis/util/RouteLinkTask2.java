package kumoh.basicbis.util;

import javafx.concurrent.Task;
import kumoh.basicbis.persistence.BusInfo;

import java.util.ArrayList;
import java.util.List;

public class RouteLinkTask2 extends Task<List<BusInfo>> {
    RequestManager manager = new RequestManager();
    private final List<BusInfo> list = new ArrayList<>();
    String query = "";

    public RouteLinkTask2() {}
    public RouteLinkTask2(String query) { this.query = query;}

    @Override
    protected List<BusInfo> call() throws Exception {

        String response = manager.sendRequest("2,5," + query + "\r\n");
        System.out.println("[RouteTask2] 받은 답변: " + response);

        String[] responseSplit = response.split("\r\n");

        for (int i = 0; i < responseSplit.length; i++) {
            String[] lineSplit = responseSplit[i].split(",");
            BusInfo busInfo = new BusInfo();

            busInfo.setUid(lineSplit[0]);
            busInfo.setId(lineSplit[1]);
            busInfo.setName(lineSplit[2]);

            list.add(busInfo);
        }

        // 로딩용
        //Thread.sleep(3000);
        //updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("RouteLinkTask2: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("RouteLinkTask2: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("RouteLinkTask2: FAILED!");
    }
}
