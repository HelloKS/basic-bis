package kumoh.basicbis;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.util.BusStopTask;

import java.net.URL;
import java.util.ResourceBundle;

public class NewBusStopInfoController implements Initializable {

    @FXML ListView<BusStopInfo> stopList;
    @FXML TextField searchField;
    @FXML Button showRoute;
    @FXML Button showFood;

    private ObservableList<BusStopInfo> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stopList.setCellFactory(busStopInfoListView -> new BusStopInfoListViewCell());
        showRoute.disableProperty().bind(stopList.getSelectionModel().selectedItemProperty().isNull());
        showFood.disableProperty().bind(stopList.getSelectionModel().selectedItemProperty().isNull());
        searchStop();
    }

    private void searchStop() {
        BusStopTask task = new BusStopTask(searchField.getText());
        list = FXCollections.observableArrayList();

        task.setOnSucceeded(workerStateEvent -> {
            list.addAll(task.getValue());
        });

        stopList.setItems(list);

        Thread thread = new Thread(task, "BusStopTask-thread");
        thread.setDaemon(true);
        thread.start();
    }
}
