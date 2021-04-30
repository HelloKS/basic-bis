package idol.tofu.client.common;

import idol.tofu.client.persistence.MovieInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class CustomMovieCell extends ListCell<MovieInfo> {
    private HBox row;
    private StackPane age;
    private Text ageText;
    private Text name;

    public CustomMovieCell() {
        super();
        name = new Text();
        age = new StackPane();
        ageText = new Text();

        ageText.setFill(Color.WHITE);
        age.getChildren().add(new Circle(12,12,12,Color.SKYBLUE));
        age.getChildren().add(ageText);
        age.setAlignment(Pos.CENTER);

        row = new HBox(age, name);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 10, 10, 10));
        row.setSpacing(20);
    }

    @Override
    protected void updateItem(MovieInfo item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            name.setText(item.getName());
            ageText.setText(item.getRating() + "");
            setGraphic(row);
        } else {
            setGraphic(null);
        }
    }
}
