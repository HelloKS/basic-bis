package idol.tofu.client;

import idol.tofu.client.common.CustomReservationCell;
import idol.tofu.client.common.GetReservationListTask;
import idol.tofu.client.persistence.ReservationInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckReservationController {
    @FXML
    private ListView<ReservationInfo> reservation_list;
    @FXML private BorderPane pane;
    @FXML private ProgressIndicator indicator;

    private List<ReservationInfo> reservationInfoList = new ArrayList<>();

    @FXML
    public void initialize() {

        reservation_list.setCellFactory(new Callback<ListView<ReservationInfo>, ListCell<ReservationInfo>>() {
            @Override
            public ListCell<ReservationInfo> call(ListView<ReservationInfo> reservationInfoListView) {
                return new CustomReservationCell();
            }
        });

        GetReservationListTask task = new GetReservationListTask();

        task.setOnSucceeded(workerStateEvent -> {
            reservation_list.getItems().setAll(task.getValue());
            pane.getChildren().remove(indicator);
        });

        Thread thread = new Thread(task, "reservationinfo-thread");
        thread.setDaemon(true);
        thread.start();
    }

    public void handleNextBtn(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/checkReservation_step2.fxml"));
            root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("예매 취소 확인");
            stage.getIcons().add(new Image("file:src/img/play-circle.png"));
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
