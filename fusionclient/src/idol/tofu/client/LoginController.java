package idol.tofu.client;

import idol.tofu.client.common.LoginTask;
import idol.tofu.client.persistence.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Button loginBtn;
    @FXML
    private TextField idField;
    @FXML
    private TextField pwField;


    @FXML
    protected void handleLoginBtn(ActionEvent event) {
        //TODO: Implement real login method

        if (idField.getText().isEmpty() || pwField.getText().isEmpty()) {
            showWarningDialog();
            return;
        }

        LoginTask task = new LoginTask(idField.getText(), pwField.getText());

        task.setOnSucceeded(workerStateEvent -> {
            SessionManager.setSession(task.getValue());
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.close();
        });

        task.setOnFailed(workerStateEvent -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("로그인 실패");
            alert.setHeaderText("로그인 실패");
            alert.setContentText("로그인에 실패했습니다.");
            alert.showAndWait();
            return;
        });

        Thread thread = new Thread(task, "theaterinfo-thread");
        thread.setDaemon(true);
        thread.start();

        //임시로 로그인 세션을 생성: 추후 서버 연동 후 생성하도록 수정
        //Session session = new Session(idField.getText());
        //SessionManager.setSession(session);
    }

    private void showWarningDialog() {
        try {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("입력 확인");
            alert.setHeaderText("로그인 입력 확인");
            alert.setContentText("아이디와 비밀번호 입력 여부를 확인해 주십시오.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
