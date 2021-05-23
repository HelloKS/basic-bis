package kumoh.basicbis;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import kumoh.basicbis.persistence.RouteInfo;
import kumoh.basicbis.util.RouteTask;

import java.io.IOException;

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

        final TableColumn<RouteInfo, RouteInfo> actionColumn = new TableColumn<>("시간표");
        actionColumn.setCellValueFactory(item -> new ReadOnlyObjectWrapper<>(item.getValue()));
        actionColumn.setCellFactory(item -> new TableCell<>() {
            @Override
            protected void updateItem(RouteInfo item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setGraphic(null);
                } else {
                    Button actionButton = new Button("보기");
                    actionButton.setOnAction(
                            event -> handleOpenTimetableBtn(item)
                    );
                    setGraphic(actionButton);
                }
            }
        });

        return new TableColumn[]{idColumn,nameColumn,actionColumn};
    }

    public void handleOpenTimetableBtn(RouteInfo route) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/busTimeTable.fxml"));
            root = (Parent) loader.load();

            TimetableController controller = loader.getController();
            controller.initData(route);

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("시간표 확인");
            stage.setScene(scene);

            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}