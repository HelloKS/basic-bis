package kumoh.basicbis;

import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import kumoh.basicbis.persistence.BusInfo;

public class busInfoListViewCell extends ListCell<BusInfo> {
    private Text id;
    private VBox content;

    public busInfoListViewCell() {
        super();
        id = new Text();
        content = new VBox(id);
        content.setSpacing(5);
    }

    @Override
    protected void updateItem(BusInfo bus, boolean empty) {
        super.updateItem(bus,empty);
        if(bus != null && !empty){
            id.setText(bus.getId()+"번");
        } else {
            id.setText("해당사항없음");
        }
        setGraphic(content);
    }
}
