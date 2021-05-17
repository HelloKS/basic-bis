package kumoh.basicbis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    
    //버스정보검색
    @FXML protected void openSearch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bisSearch.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("버스 정보 검색");
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //버스 노선 검색
    @FXML protected void openRT(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/routeInfo.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("버스 노선 검색");
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //버스 시간표 검색
    @FXML protected void openTT(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/busSchedule.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("버스 시간표 검색");
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //경유 정류장 안내
    @FXML protected void openBS(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/busStopInfo.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("경유 정류장 안내");
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //음식점 추천
    @FXML protected void openFD(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/foodRecom.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("음식점 추천");
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
