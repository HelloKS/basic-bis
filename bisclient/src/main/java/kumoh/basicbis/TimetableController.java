package kumoh.basicbis;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import kumoh.basicbis.persistence.RouteInfo;
import kumoh.basicbis.util.RouteTask;
import kumoh.basicbis.util.TimetableTask;

import java.net.URL;
import java.util.ResourceBundle;

public class TimetableController implements Initializable {
    @FXML Label name;
    RouteInfo route;

    public void initData(RouteInfo selection) {
        route = selection;
        name.setText(route.getId() + ": " + route.getName());

        TimetableTask task = new TimetableTask(route.getUid());

        task.setOnSucceeded(null);

        Thread thread = new Thread(task, "timetabletask-thread");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
