package kumoh.basicbis.util;

import javafx.concurrent.Task;
import kumoh.basicbis.persistence.BusInfo;

import java.util.ArrayList;
import java.util.List;

public class BusInfoTask2 extends Task<List<BusInfo>> {
    RequestManager manager = new RequestManager();
    private final List<BusInfo> list = new ArrayList<>();
    String query = "";

    public BusInfoTask2(String query) { this.query = query;}

    @Override
    protected List<BusInfo> call() throws Exception {
        String response = manager.sendRequest("5,4," + query + "\r\n");
        //System.out.println("[BusInfoTask2] 받은 답변: " + response);

        String[] responseSplit = response.split("\r\n");

        for (int i = 0; i < responseSplit.length; i++) {
            String[] lineSplit = responseSplit[i].split(",");
            BusInfo bus = new BusInfo();

            bus.setUid((lineSplit[0]));
            bus.setId((lineSplit[1]));
            bus.setName(lineSplit[2]);

            list.add(bus);
        }

        // 로딩용
        //Thread.sleep(3000);
        //updateProgress(1, 1);
        return list;
    }
    @Override
    protected void running() {
        super.running();
        System.out.println("BusInfoTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("BusInfoTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("BusInfoTask: FAILED!");
    }
}
