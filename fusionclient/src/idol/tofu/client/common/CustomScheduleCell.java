package idol.tofu.client.common;

import idol.tofu.client.persistence.ScheduleInfo;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CustomScheduleCell extends ListCell<ScheduleInfo> {
    private VBox content;
    private Text timeText, screenNumText;

    public CustomScheduleCell() {
        super();
        timeText = new Text();
        screenNumText = new Text();
        content = new VBox(timeText, screenNumText);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(ScheduleInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            SimpleDateFormat format = new SimpleDateFormat("a hh:mm", Locale.KOREA);
            timeText.setText(format.format(item.getStartTime().getTime()));
            screenNumText.setText(item.getScreenNumber() + "관");
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}