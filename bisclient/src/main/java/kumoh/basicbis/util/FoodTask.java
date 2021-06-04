package kumoh.basicbis.util;

import javafx.concurrent.Task;
import kumoh.basicbis.persistence.FoodInfo;

import java.util.ArrayList;
import java.util.List;

public class FoodTask extends Task<List<FoodInfo>> {

    RequestManager manager = new RequestManager();
    private final List<FoodInfo> list = new ArrayList<>();
    String query = "";

    public FoodTask(String query) { this.query = query; }

    @Override
    protected List<FoodInfo> call() throws Exception {

        String response = manager.sendRequest("3,1," + query + "\r\n");
        System.out.println("[FoodTask] 받은 답변: " + response);

        String[] responseSplit = response.split("\r\n");

        for (int i = 0; i < responseSplit.length; i++) {
            String[] lineSplit = responseSplit[i].split(",");
            FoodInfo food = new FoodInfo();

            food.setUid(Integer.parseInt(lineSplit[0]));
            food.setName(lineSplit[1]);
            food.setAddress(lineSplit[2]);
            food.setPhone(lineSplit[3]);
            food.setMapx(Double.parseDouble(lineSplit[4]));
            food.setMapy(Double.parseDouble(lineSplit[5]));

            list.add(food);
        }

        // 로딩용
        //Thread.sleep(3000);
        //updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("FoodTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("FoodTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("FoodTask: FAILED!");
    }
}
