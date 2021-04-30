package idol.tofu.client.common;

import idol.tofu.client.persistence.ReservationInfo;
import javafx.concurrent.Task;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GetReservationListTask extends Task<List<ReservationInfo>>{

    private final List<ReservationInfo> list = new ArrayList<>();

    @Override
    protected List<ReservationInfo> call() throws Exception {
        //여기서 네트워크로 대충 극장 정보를 받아 옴
        //현재는 대충 만들어서 던짐

        list.add(new ReservationInfo(1, "aaa", 1, 4));
        list.add(new ReservationInfo(1, "aaa", 2, 5));

        // 로딩용
        //Thread.sleep(3000);
        updateProgress(1, 1);

        return list;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("GetReservationListTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("GetReservationListTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("GetReservationListTask: FAILED!");
    }
}
