package kumoh.basicbis;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import kumoh.basicbis.persistence.RouteInfo;
import kumoh.basicbis.persistence.TimeTableInfo;
import kumoh.basicbis.util.RouteTask;
import kumoh.basicbis.util.TimetableTask;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class TimetableController implements Initializable {
    @FXML Label name;
    RouteInfo route;
    @FXML private TableView<TimeTableInfo> weekTable;
    @FXML private TableView<TimeTableInfo> holidayTable;

    public void initData(RouteInfo selection) {
        route = selection;
        name.setText(route.getId() + ": " + route.getName());

        weekTable.getColumns().setAll(getColumns());
        holidayTable.getColumns().setAll(getColumns());

        TimetableTask task = new TimetableTask(route.getUid());

        task.setOnSucceeded(workerStateEvent -> {
            for (TimeTableInfo entry : task.getValue()) {
                if (entry.isHoliday()) {
                    holidayTable.getItems().add(entry);
                } else {
                    weekTable.getItems().add(entry);
                }
            }
        });

        Thread thread = new Thread(task, "timetabletask-thread");
        thread.setDaemon(true);
        thread.start();
    }

    public TableColumn[] getColumns() {
        final TableColumn<Void, String> indexColumn = new TableColumn<>("배차 순번");
        indexColumn.setCellFactory(item -> new TableCell<>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index));
                }
            }
        });

        final TableColumn<TimeTableInfo, String> timeColumn = new TableColumn<>("기점 출발");
        timeColumn.setCellValueFactory(item -> {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return new ReadOnlyStringWrapper(format.format(item.getValue().getStartTime()));
        });
        timeColumn.setPrefWidth(100);

        return new TableColumn[]{indexColumn, timeColumn};
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
