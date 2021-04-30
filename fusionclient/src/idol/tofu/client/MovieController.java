package idol.tofu.client;

import idol.tofu.client.common.CustomMovieCell;
import idol.tofu.client.common.GetMovieListTask;
import idol.tofu.client.persistence.MovieInfo;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieController {
    @FXML private ListView<MovieInfo> movie_list;
    @FXML private StackPane pane;
    @FXML private ProgressIndicator indicator;

    //상세정보 표시용 Widgets
    @FXML private Label movie_name;
    @FXML private Label movie_genre;
    @FXML private Label movie_release_date;
    @FXML private Label movie_runtime;
    @FXML private Label movie_actor;
    @FXML private Label movie_detail;
    @FXML private ImageView movie_poster;


    private List<MovieInfo> movieInfoList = new ArrayList<>();

    @FXML
    public void initialize() {

        movie_list.setCellFactory(new Callback<ListView<MovieInfo>, ListCell<MovieInfo>>() {
            @Override
            public ListCell<MovieInfo> call(ListView<MovieInfo> movieInfoListView) {
                return new CustomMovieCell();
            }
        });

        GetMovieListTask task = new GetMovieListTask();

        task.setOnSucceeded(workerStateEvent -> {
            movie_list.getItems().setAll(task.getValue());
            pane.getChildren().remove(indicator);
        });

        Thread thread = new Thread(task, "movieinfo-thread");
        thread.setDaemon(true);
        thread.start();
    }

    @FXML public void handleListClick(MouseEvent me) {
        // 클릭시 옆에 상세정보 표시
        MovieInfo selectedItem = movie_list.getSelectionModel().getSelectedItem();

        movie_name.setText(selectedItem.getName());
        movie_actor.setText(selectedItem.getActor());
        movie_genre.setText(selectedItem.getGenre());
        movie_release_date.setText(selectedItem.getReleaseDateString());
        movie_runtime.setText(selectedItem.getRuntime() + "분");
        movie_detail.setText(selectedItem.getDetail());
    }


}
