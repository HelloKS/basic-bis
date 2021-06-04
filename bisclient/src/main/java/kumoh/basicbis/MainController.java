package kumoh.basicbis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    
    //조건 검색(경로추천, 내위치기반정류장)
    @FXML protected void openSearch(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bisSearch.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("조건 검색");
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //버스 안내 (노선/정류장)
    @FXML protected void openRT(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewInfoWindow.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("버스 안내");
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
