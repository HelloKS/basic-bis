package kumoh.basicbis;

import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.persistence.RouteInfo;

public class RouteInfoListViewCell extends ListCell<RouteInfo> {
    private Text name;
    private Text id;
    private VBox content;
    public RouteInfoListViewCell() {
        super();
        name = new Text();
        id = new Text();
        content = new VBox(name,id);
        content.setSpacing(10);
    }
    @Override
    protected void updateItem(RouteInfo route, boolean empty){
        super.updateItem(route, empty);
        if (route != null && !empty){
            name.setText(route.getId());
            id.setText(route.getName());
            setGraphic(content);
        } else {
           setGraphic(null);
        }
    }
}
