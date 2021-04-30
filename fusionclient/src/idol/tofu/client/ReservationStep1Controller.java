package idol.tofu.client;

import idol.tofu.client.common.CustomMovieCell;
import idol.tofu.client.common.CustomTheaterCell;
import idol.tofu.client.common.GetMovieListTask;
import idol.tofu.client.common.GetTheaterListTask;
import idol.tofu.client.persistence.MovieInfo;
import idol.tofu.client.persistence.TheaterInfo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class ReservationStep1Controller extends StackPane {

    @FXML private ListView<TheaterInfo> theater_list;
    @FXML private ListView<MovieInfo> movie_list;
    @FXML private ProgressIndicator loading_indicator;

    ReservationData data;

    private List<TheaterInfo> theaterInfoList = new ArrayList<>();
    private List<MovieInfo> movieInfoList = new ArrayList<>();

    public ReservationStep1Controller(ReservationData data) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/reservation_step1.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.data = data;

        // 영화관 Listview
        theater_list.setCellFactory(new Callback<ListView<TheaterInfo>, ListCell<TheaterInfo>>() {
            @Override
            public ListCell<TheaterInfo> call(ListView<TheaterInfo> theaterInfoListView) {
                return new CustomTheaterCell();
            }
        });

        //영화 Listview
        movie_list.setCellFactory(new Callback<ListView<MovieInfo>, ListCell<MovieInfo>>() {
            @Override
            public ListCell<MovieInfo> call(ListView<MovieInfo> movieInfoListView) {
                return new CustomMovieCell();
            }
        });

        movie_list.setOnMouseClicked(new handleMovieListClick());
        theater_list.setOnMouseClicked(new handleTheaterListClick());

        BooleanProperty isTaskRunning = new SimpleBooleanProperty(true);
        loading_indicator.visibleProperty().bind(isTaskRunning);

        GetTheaterListTask theaterTask = new GetTheaterListTask();
        GetMovieListTask movieTask = new GetMovieListTask();

        theaterTask.setOnSucceeded(workerStateEvent -> {
            theater_list.getItems().setAll(theaterTask.getValue());
        });

        movieTask.setOnSucceeded(workerStateEvent -> {
            movie_list.getItems().setAll(movieTask.getValue());
            isTaskRunning.set(false);
        });

        Thread theaterThread = new Thread(theaterTask, "theaterinfo-thread");
        theaterThread.setDaemon(true);
        theaterThread.start();

        Thread movieThread = new Thread(movieTask, "movieinfo-thread");
        movieThread.setDaemon(true);
        movieThread.start();
    }

    private class handleMovieListClick implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            MovieInfo selection = movie_list.getSelectionModel().getSelectedItem();
            data.setMovieSelection(selection);
        }
    }

    private class handleTheaterListClick implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            TheaterInfo selection = theater_list.getSelectionModel().getSelectedItem();
            data.setTheaterSelection(selection);
        }
    }
}