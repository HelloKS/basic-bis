package kumoh.basicbis;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kumoh.basicbis.persistence.BusInfo;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.util.BusInfoTask;
import kumoh.basicbis.util.BusInfoTask2;

import java.net.URL;
import java.util.ResourceBundle;

public class searchResultController implements Initializable {
    @FXML private TableView<BusInfo> busTableView = new TableView<>();
    @FXML private Label name;
    @FXML private Label subject;

    private BusStopInfo start;
    private BusStopInfo end;

    public TableColumn[] getColumns() {
        final TableColumn<Void, String> indexColumn = new TableColumn<>("추천 순위");
        indexColumn.setCellFactory(item -> new TableCell<>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index + 1));
                }
            }
        });
        indexColumn.setPrefWidth(100);

        final TableColumn<BusInfo, String> busId = new TableColumn<>("버스번호");
        busId.setCellValueFactory(item -> {
            return new ReadOnlyStringWrapper(item.getValue().getId());
        });
        busId.setPrefWidth(100);

        final TableColumn<BusInfo, String> busNameColumn = new TableColumn<>("버스노선명");
        busNameColumn.setCellValueFactory(item -> {
            return new ReadOnlyStringWrapper(item.getValue().getName());
        });
        busNameColumn.setPrefWidth(250);

        if(subject.getText() =="경로 검색 결과"){
            return new TableColumn[]{indexColumn, busId, busNameColumn};
        }
        else if(subject.getText() == "노선 추천 결과"){

            final TableColumn<BusInfo, String> busNumber = new TableColumn<>("경유 정류장 수");
            busNumber.setCellValueFactory(item -> {
                return new ReadOnlyStringWrapper(item.getValue().getUid());
            });
            busNumber.setPrefWidth(150);

            return new TableColumn[]{indexColumn, busId, busNameColumn, busNumber};
        }
        return new TableColumn[]{indexColumn, busId, busNameColumn};
    }

    public void searchBus(BusStopInfo start, BusStopInfo end) {
        this.start = start;
        this.end = end;
        subject.setText("경로 검색 결과");
        String query = start.getUid() + "," + end.getUid();
        busTableView.getColumns().setAll(getColumns());

        BusInfoTask task = new BusInfoTask(query);
        task.setOnSucceeded(workerStateEvent -> {
            for (BusInfo entry : task.getValue()) {
                busTableView.getItems().add(entry);
            }
        });
        Thread thread = new Thread(task, "bustask-thread");
        thread.setDaemon(true);
        thread.start();
    }
    public void recommendation(BusStopInfo start, BusStopInfo end) {
        this.start = start;
        this.end = end;
        subject.setText("노선 추천 결과");

        String query = start.getUid() + "," + end.getUid();
        busTableView.getColumns().setAll(getColumns());

        BusInfoTask2 task = new BusInfoTask2(query);
        task.setOnSucceeded(workerStateEvent -> {
            for (BusInfo entry : task.getValue()) {
                busTableView.getItems().add(entry);
            }
        });
        Thread thread = new Thread(task, "bustask-thread");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
