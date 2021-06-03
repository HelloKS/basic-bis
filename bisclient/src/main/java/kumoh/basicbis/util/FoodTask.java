package kumoh.basicbis.util;

import javafx.concurrent.Task;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.persistence.Food;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodTask extends Task<List<Food>> {

    RequestManager manager = new RequestManager();
    private final List<Food> list = new ArrayList<>();
    String query = "";

    public FoodTask(String query) { this.query = query; }

    @Override
    protected List<Food> call() throws Exception {

        String response = manager.sendRequest("3,1," + query + "\r\n");
        System.out.println("[FoodTask] 받은 답변: " + response);

        String[] responseSplit = response.split("\r\n");

        for (int i = 0; i < responseSplit.length; i++) {
            String[] lineSplit = responseSplit[i].split(",");
            Food food = new Food();

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
