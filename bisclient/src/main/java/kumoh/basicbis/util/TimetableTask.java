package kumoh.basicbis.util;

import javafx.concurrent.Task;
import kumoh.basicbis.persistence.TimeTableInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimetableTask extends Task<List<TimeTableInfo>> {
    RequestManager manager = new RequestManager();
    private final List<TimeTableInfo> list = new ArrayList<>();
    String query = "";

    public TimetableTask(String query) {
        this.query = query;
    }

    @Override
    protected List<TimeTableInfo> call() throws Exception {

        String response = manager.sendRequest("4,1," + query + "\r\n");
        System.out.println("[TimetableTask] 받은 답변: " + response);

        String[] responseSplit = response.split("\r\n");

        for (int i = 0; i < responseSplit.length; i++) {
            String[] lineSplit = responseSplit[i].split(",");
            TimeTableInfo timetable = new TimeTableInfo();

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date startTime = format.parse(lineSplit[1]);

            timetable.setUid(lineSplit[0]);
            timetable.setStartTime(startTime);
            timetable.setHoliday(lineSplit[2] == "true");

            list.add(timetable);
        }

        // 로딩용
        //Thread.sleep(3000);
        //updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("TimetableTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("TimetableTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("TimetableTask: FAILED!");
    }
}
