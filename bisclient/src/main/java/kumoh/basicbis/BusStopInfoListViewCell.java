package kumoh.basicbis;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import kumoh.basicbis.persistence.BusStopInfo;

import java.io.IOException;

public class BusStopInfoListViewCell extends ListCell<BusStopInfo> {
    private Text name;
    private Text id;
    private VBox content;
    public BusStopInfoListViewCell() {
        super();
        name = new Text();
        id = new Text();
        content = new VBox(name,id);
        content.setSpacing(10);
    }
    @Override
    protected void updateItem(BusStopInfo bus, boolean empty){
        super.updateItem(bus, empty);
        if(bus != null && !empty){
            name.setText(bus.getName());
            id.setText(Integer.toString(bus.getServiceId()));
            setGraphic(content);
        } else {
           setGraphic(null);
        }
    }
}
