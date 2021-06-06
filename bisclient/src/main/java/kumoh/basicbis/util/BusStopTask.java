package kumoh.basicbis.util;

import javafx.concurrent.Task;
import kumoh.basicbis.persistence.BusStopInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BusStopTask extends Task<List<BusStopInfo>> {
    RequestManager manager = new RequestManager();
    private final List<BusStopInfo> list = new ArrayList<>();
    String query = "";

    public BusStopTask(String query) { this.query = query;}

    @Override
    protected List<BusStopInfo> call() throws Exception {

        String response = manager.sendRequest("1,1," + query + "\r\n");
        //System.out.println("[BusStopTask] 받은 답변: " + response);

        String[] responseSplit = response.split("\r\n");

        for (int i = 0; i < responseSplit.length; i++) {
            String[] lineSplit = responseSplit[i].split(",");
            BusStopInfo busStop = new BusStopInfo();

            busStop.setUid(Integer.parseInt(lineSplit[0]));
            busStop.setServiceId(Integer.parseInt(lineSplit[1]));
            busStop.setName(lineSplit[2]);
            busStop.setMapX(Double.parseDouble(lineSplit[3]));
            busStop.setMapY(Double.parseDouble(lineSplit[4]));

            list.add(busStop);
        }

        // 로딩용
        //Thread.sleep(3000);
        //updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("BusStopTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("BusStopTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("BusStopTask: FAILED!");
    }
}
