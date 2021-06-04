package kumoh.basicbis;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.persistence.Food;
import kumoh.basicbis.persistence.RouteInfo;
import kumoh.basicbis.persistence.TimeTableInfo;
import kumoh.basicbis.util.BusStopTask3;
import kumoh.basicbis.util.FoodTask;
import kumoh.basicbis.util.TimetableTask;

import java.net.URL;
import java.util.ResourceBundle;

public class FoodRecomController implements Initializable {
    @FXML
    private ComboBox<BusStopInfo> bsComboBox = new ComboBox<>();
    @FXML
    private Button bsBt;
    @FXML
    private TableView<Food> foodTableView = new TableView<>();

    private BusStopInfo busStopInfo;
    private ObservableList<BusStopInfo> list = FXCollections.observableArrayList();
    private ObservableList<Food> foodList = FXCollections.observableArrayList();

    public TableColumn[] getColumns() {
        final TableColumn<Food, String> foodNameColumn = new TableColumn<>("업소명");
        foodNameColumn.setCellValueFactory(item -> {
            return new ReadOnlyStringWrapper(item.getValue().getName());
        });
        foodNameColumn.setPrefWidth(134);

        final TableColumn<Food, String> foodAddressColumn = new TableColumn<>("주소(도로명)");
        foodAddressColumn.setCellValueFactory(item -> {
            return new ReadOnlyStringWrapper(item.getValue().getAddress());
        });
        foodAddressColumn.setPrefWidth(250);

        final TableColumn<Food, String> foodPhoneColumn = new TableColumn<>("연락처");
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
        busStopInfo = selection;
        String query = Integer.toString(selection.getServiceId());
        foodTableView.getColumns().setAll(getColumns());

        FoodTask task = new FoodTask(query);
        task.setOnSucceeded(workerStateEvent -> {
                    for (Food entry : task.getValue()) {
                        foodTableView.getItems().add(entry);
                    }
                }
        );
        Thread thread = new Thread(task, "foodtask-thread");
        thread.setDaemon(true);
        thread.start();
    }

}
