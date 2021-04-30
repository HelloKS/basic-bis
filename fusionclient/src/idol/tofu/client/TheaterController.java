package idol.tofu.client;

import idol.tofu.client.common.CustomTheaterCell;
import idol.tofu.client.common.GetTheaterListTask;
import idol.tofu.client.persistence.TheaterInfo;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TheaterController {
    @FXML private ListView<TheaterInfo> theater_list;
    @FXML private StackPane pane;
    @FXML private ProgressIndicator indicator;

    //상세정보 표시용 Widgets
    @FXML private Label theater_name;
    @FXML private Label theater_addr;
    @FXML private Label theater_phone;

    private List<TheaterInfo> theaterInfoList = new ArrayList<>();

    @FXML
    public void initialize() {

        theater_list.setCellFactory(new Callback<ListView<TheaterInfo>, ListCell<TheaterInfo>>() {
            @Override
            public ListCell<TheaterInfo> call(ListView<TheaterInfo> theaterInfoListView) {
                return new CustomTheaterCell();
            }
        });

        GetTheaterListTask task = new GetTheaterListTask();

        task.setOnSucceeded(workerStateEvent -> {
            theater_list.getItems().setAll(task.getValue());
            pane.getChildren().remove(indicator);
        });

        Thread thread = new Thread(task, "theaterinfo-thread");
        thread.setDaemon(true);
        thread.start();
    }

    @FXML public void handleListClick(MouseEvent me) {
        // 클릭시 옆에 상세정보 표시
        TheaterInfo selectedItem = theater_list.getSelectionModel().getSelectedItem();

        theater_name.setText(selectedItem.getName());
        theater_addr.setText(selectedItem.getAddress());
        theater_phone.setText(selectedItem.getPhoneNumber());
    }

}
