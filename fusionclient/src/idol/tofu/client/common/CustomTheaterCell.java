package idol.tofu.client.common;

import idol.tofu.client.persistence.TheaterInfo;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CustomTheaterCell extends ListCell<TheaterInfo> {
    private VBox content;
    private Text name;
    private Text address;

    public CustomTheaterCell() {
        super();
        name = new Text();
        address = new Text();
        content = new VBox(name, address);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(TheaterInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            name.setText(item.getName());
            address.setText(item.getAddress());
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}