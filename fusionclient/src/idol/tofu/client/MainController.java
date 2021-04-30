package idol.tofu.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML private Button loginBtn;
    @FXML private Button regBtn;
    @FXML private Button managerBtn;
    @FXML private Label statusLabel;

    @FXML protected void handleLoginBtn(ActionEvent event) {
        Parent root;

        // 로그인 상태에서 로그아웃 하는 경우 처리
        if (SessionManager.isLogin()) {
            SessionManager.deleteSession();
            loginBtn.setText("로그인");
            statusLabel.setText("비로그인 상태입니다");
            managerBtn.setVisible(false);
            managerBtn.setManaged(false);
            regBtn.setVisible(true);
            regBtn.setManaged(true);

            return;
        }

        // 로그인 되지 않은 상태이면 로그인 창 띄우고 로그인 시킴
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/login.fxml"));
            root = (Parent) loader.load();
            Scene scene = new Scene(root);

            LoginController loginController = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("회원 로그인");
            stage.getIcons().add(new Image("file:src/img/play-circle.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(root.getScene().getWindow());
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (SessionManager.isLogin()) {
            statusLabel.setText(SessionManager.getSession().getId() + "님 환영합니다.");
            loginBtn.setText("로그아웃");
            //현재 로그인시 관리자 버튼 활성화 되도록 설정됨
            //매니저 확인하는 기능 생성시 그쪽으로 이동하도록 하십쇼
            if(SessionManager.getSession().isManager()) {
                managerBtn.setVisible(true);
                managerBtn.setManaged(true);
            }
            regBtn.setVisible(false);
            regBtn.setManaged(false);
        }
    }

    @FXML protected void handleRegBtn(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/register.fxml"));
            root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("회원가입");
            stage.getIcons().add(new Image("file:src/img/play-circle.png"));
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void handleReservationBtn(ActionEvent event) {
        Parent root;

        if (!SessionManager.isLogin()) {
            showLoginRequiredDialog();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/reservation.fxml"));
            root = (Parent) loader.load();
            Scene scene = new Scene(root, 500, 300);

            Stage stage = new Stage();
            stage.setTitle("예매");
            stage.getIcons().add(new Image("file:src/img/play-circle.png"));
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML protected void handleCheckReservationBtn(ActionEvent event) {
        Parent root;

        if (!SessionManager.isLogin()) {
            showLoginRequiredDialog();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/checkReservation.fxml"));
            root = (Parent) loader.load();
            Scene scene = new Scene(root, 500, 300);

            Stage stage = new Stage();
            stage.setTitle("예매확인");
            stage.getIcons().add(new Image("file:src/img/play-circle.png"));
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML protected void handleMovieBtn(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/movie.fxml"));
            root = (Parent) loader.load();
            Scene scene = new Scene(root, 700, 500);

            Stage stage = new Stage();
            stage.setTitle("영화");
            stage.getIcons().add(new Image("file:src/img/play-circle.png"));
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML protected void handleTheaterBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("layout/theater.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root, 500, 300);

            Stage stage = new Stage();
            stage.setTitle("영화관");
            stage.getIcons().add(new Image("file:src/img/play-circle.png"));
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleManagerBtn(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/manager.fxml"));
            root = (Parent) loader.load();
            Scene scene = new Scene(root, 500, 300);

            Stage stage = new Stage();
            stage.setTitle("관리자 페이지");
            stage.getIcons().add(new Image("file:src/img/play-circle.png"));
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLoginRequiredDialog() {
        try {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("로그인 필요");
            alert.setHeaderText("로그인이 필요합니다");
            alert.setContentText("이용하려는 서비스는 로그인이 필요합니다.\n계정이 없을 경우 회원가입 하십시오.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
