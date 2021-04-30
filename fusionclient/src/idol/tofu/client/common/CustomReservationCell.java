package idol.tofu.client.common;

import idol.tofu.client.persistence.ReservationInfo;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CustomReservationCell extends ListCell<ReservationInfo> {
    private VBox content;
    private Text uid;
    private Text scheduleId;

    public CustomReservationCell() {
        super();
        uid = new Text();
        scheduleId = new Text();
        content = new VBox(uid, scheduleId);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(ReservationInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            uid.setText("예매내역 UID" + Integer.toString(item.getUid()));
            scheduleId.setText("스케줄 UID : " + Integer.toString(item.getScheduleId()));
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}
