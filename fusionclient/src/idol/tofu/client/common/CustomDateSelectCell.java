package idol.tofu.client.common;

import idol.tofu.client.persistence.TheaterInfo;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomDateSelectCell extends ListCell<Calendar> {
    private HBox content;
    private Text dateText;

    public CustomDateSelectCell() {
        super();
        dateText = new Text();
        content = new HBox(dateText);
        content.setPadding(new Insets(1,1,1,1));
    }

    @Override
    protected void updateItem(Calendar item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            SimpleDateFormat format = new SimpleDateFormat("dd (EEE)", Locale.KOREA); //Example: 20 (금)
            dateText.setText(format.format(item.getTime()));
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}