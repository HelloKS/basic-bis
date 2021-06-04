package kumoh.basicbis;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.persistence.FoodInfo;
import kumoh.basicbis.util.FoodTask;

import java.net.URL;
import java.util.ResourceBundle;

public class FoodRecomController implements Initializable {
    @FXML private TableView<FoodInfo> foodTableView = new TableView<>();
    @FXML private Label name;

    private BusStopInfo busStopInfo;
    private ObservableList<BusStopInfo> list = FXCollections.observableArrayList();
    private ObservableList<FoodInfo> foodList = FXCollections.observableArrayList();

    public TableColumn[] getColumns() {
        final TableColumn<FoodInfo, String> foodNameColumn = new TableColumn<>("업소명");
        foodNameColumn.setCellValueFactory(item -> {
            return new ReadOnlyStringWrapper(item.getValue().getName());
        });
        foodNameColumn.setPrefWidth(130);

        final TableColumn<FoodInfo, String> foodAddressColumn = new TableColumn<>("도로명 주소");
        foodAddressColumn.setCellValueFactory(item -> {
            return new ReadOnlyStringWrapper(item.getValue().getAddress());
        });
        foodAddressColumn.setPrefWidth(350);

        final TableColumn<FoodInfo, String> foodPhoneColumn = new TableColumn<>("전화번호");
        foodPhoneColumn.setCellValueFactory(item -> {
            return new ReadOnlyStringWrapper(item.getValue().getPhone());
        });
        foodPhoneColumn.setPrefWidth(100);

        return new TableColumn[]{foodNameColumn, foodAddressColumn, foodPhoneColumn};
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void searchFood(BusStopInfo selection) {
        name.setText(selection.getName());

        busStopInfo = selection;
        String query = Integer.toString(selection.getServiceId());
        foodTableView.getColumns().setAll(getColumns());

        FoodTask task = new FoodTask(query);
        task.setOnSucceeded(workerStateEvent -> {
                    for (FoodInfo entry : task.getValue()) {
                        foodTableView.getItems().add(entry);
                    }
                }
        );
        Thread thread = new Thread(task, "foodtask-thread");
        thread.setDaemon(true);
        thread.start();
    }

}
