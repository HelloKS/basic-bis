package kumoh.basicbis;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kumoh.basicbis.persistence.RouteInfo;
import kumoh.basicbis.util.RouteTask;

public class ScheduleController {
    @FXML private TableView<RouteInfo> tableView;

    @FXML
    public void initialize() {
        tableView.getColumns().setAll(getColumns());

        RouteTask task = new RouteTask();

        task.setOnSucceeded(workerStateEvent -> {
            tableView.getItems().setAll(task.getValue());
        });

        Thread thread = new Thread(task, "routetask-thread");
        thread.setDaemon(true);
        thread.start();
    }

    public TableColumn[] getColumns() {
        final TableColumn<RouteInfo, String> idColumn = new TableColumn<>("노선 번호");
        idColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getId()));

        final TableColumn<RouteInfo, String> nameColumn = new TableColumn<>("노선 이름");
        nameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getName()));
        nameColumn.setPrefWidth(400);

        final TableColumn<RouteInfo, Void> actionColumn = new TableColumn<>("시간표");
        actionColumn.setCellFactory(item -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button actionButton = new Button("보기");
                    setGraphic(actionButton);
                }
            }
        });

        return new TableColumn[]{idColumn,nameColumn,actionColumn};
    }
}
