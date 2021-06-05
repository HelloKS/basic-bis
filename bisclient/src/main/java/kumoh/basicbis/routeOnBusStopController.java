package kumoh.basicbis;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kumoh.basicbis.persistence.BusInfo;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.util.RouteLinkTask2;

import java.net.URL;
import java.util.ResourceBundle;

public class routeOnBusStopController implements Initializable {
    @FXML private TableView<BusInfo> busInfoTableView = new TableView<>();
    @FXML private Label name;

    private BusStopInfo busStopInfo;
    private ObservableList<BusInfo> busList = FXCollections.observableArrayList();
    private ObservableList<BusStopInfo> busStopList = FXCollections.observableArrayList();

    public TableColumn[] getColumn(){
        final TableColumn<BusInfo, String> busIdColumn = new TableColumn<>("노선번호");
        busIdColumn.setCellValueFactory(item -> {
            return new ReadOnlyStringWrapper(item.getValue().getId());
        });
        busIdColumn.setPrefWidth(130);

        final TableColumn<BusInfo, String> busNameColumn = new TableColumn<>("노선명");
        busNameColumn.setCellValueFactory(item -> {
            return new ReadOnlyStringWrapper(item.getValue().getName());
        });
        busNameColumn.setPrefWidth(450);

        return new TableColumn[]{busIdColumn, busNameColumn};
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){}

    public void searchBus(BusStopInfo selection){
        name.setText(selection.getName());

        busStopInfo = selection;
        String query = Integer.toString(selection.getServiceId());
        busInfoTableView.getColumns().setAll(getColumn());

        RouteLinkTask2 task = new RouteLinkTask2(query);
        task.setOnSucceeded(workerStateEvent -> {
                    for (BusInfo entry : task.getValue()) {
                        busInfoTableView.getItems().add(entry);
                    }
                }
        );
        Thread thread = new Thread(task, "routetask-thread");
        thread.setDaemon(true);
        thread.start();
    }
}
