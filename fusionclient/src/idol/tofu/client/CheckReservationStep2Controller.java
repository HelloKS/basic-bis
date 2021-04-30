package idol.tofu.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CheckReservationStep2Controller {
    @FXML
    private Button okBtn;
    @FXML
    private Button cancelBtn;

    public void handleCancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void handleOkBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }
}
