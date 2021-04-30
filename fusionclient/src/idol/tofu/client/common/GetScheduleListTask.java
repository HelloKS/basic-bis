package idol.tofu.client.common;

import idol.tofu.client.persistence.MovieInfo;
import idol.tofu.client.persistence.ScheduleInfo;
import idol.tofu.client.persistence.TheaterInfo;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetScheduleListTask extends Task<List<ScheduleInfo>> {

    private final List<ScheduleInfo> list = new ArrayList<>();
    private Calendar dateSelection;
    private MovieInfo movieSelection;
    private TheaterInfo theaterSelection;

    public GetScheduleListTask(Calendar date, MovieInfo mov, TheaterInfo theater) {
        dateSelection = date;
        movieSelection = mov;
        theaterSelection = theater;
    }

    @Override
    protected List<ScheduleInfo> call() throws Exception {
        //여기서 네트워크로 대충 극장 정보를 받아 옴
        //현재는 대충 만들어서 던짐
        Calendar testDate = (Calendar) dateSelection.clone();
        testDate.add(Calendar.HOUR, 1);
        list.add(new ScheduleInfo(0, movieSelection.getUid(), 1, theaterSelection.getUid(), testDate));
        list.add(new ScheduleInfo(1, movieSelection.getUid(), 2, theaterSelection.getUid(), testDate));
        Calendar testDate2 = (Calendar) testDate.clone();
        testDate.add(Calendar.HOUR, 1);
        list.add(new ScheduleInfo(2, movieSelection.getUid(), 1, theaterSelection.getUid(), dateSelection));
        list.add(new ScheduleInfo(3, movieSelection.getUid(), 2, theaterSelection.getUid(), dateSelection));

        // 로딩용
        Thread.sleep(3000);
        updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("GetScheduleListTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("GetScheduleListTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("GetScheduleListTask: FAILED!");
    }
}
