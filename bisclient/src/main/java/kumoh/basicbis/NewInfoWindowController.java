package kumoh.basicbis;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NewInfoWindowController implements Initializable {

    @FXML
    TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }
}
