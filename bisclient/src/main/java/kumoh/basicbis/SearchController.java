package kumoh.basicbis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.util.BusStopTask;

import java.util.List;

public class SearchController {
    /* 일단 경로 검색 */
    @FXML private Button rtBt1;
    @FXML private Button rtBt2;
    @FXML private Button rtBt3;
    @FXML private TextField rtTextField1;
    @FXML private TextField rtTextField2;
    @FXML private ListView<BusStopInfo> rtListView;

    private ObservableList<BusStopInfo> list;

    public void initialize() {
        BusStopTask task = new BusStopTask();
        list = FXCollections.observableArrayList();

        task.setOnSucceeded(workerStateEvent -> {
            list.addAll(task.getValue()); }
        );

//        rtListView.setItems(list);
//        rtListView.setCellFactory(new Callback<ListView<BusStopInfo>, ListCell<BusStopInfo>>() {
//            @Override
//            public ListCell<BusStopInfo> call(ListView<BusStopInfo> busStopInfoListView) {
//                return new BusStopInfoListViewCell();
//            }
//        });

        Thread thread = new Thread(task, "BusStopTask-thread");
        thread.setDaemon(true);
        thread.start();
    }
}
