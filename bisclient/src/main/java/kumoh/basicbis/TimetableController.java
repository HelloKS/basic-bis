package kumoh.basicbis;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import kumoh.basicbis.persistence.RouteInfo;

import java.net.URL;
import java.util.ResourceBundle;

public class TimetableController implements Initializable {
    @FXML Label name;
    RouteInfo route;

    public void initData(RouteInfo selection) {
        route = selection;
        name.setText(route.getId() + ": " + route.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
