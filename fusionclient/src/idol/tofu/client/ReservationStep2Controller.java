package idol.tofu.client;

import idol.tofu.client.common.*;
import idol.tofu.client.persistence.MovieInfo;
import idol.tofu.client.persistence.ScheduleInfo;
import idol.tofu.client.persistence.TheaterInfo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservationStep2Controller extends StackPane {

    @FXML private ListView<Calendar> list_date;
    @FXML private ListView<ScheduleInfo> list_schedule;
    @FXML private ProgressIndicator loading_indicator;

    ReservationData data;

    private List<ScheduleInfo> ScheduleInfoList = new ArrayList<>();

    public ReservationStep2Controller(ReservationData data) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layout/reservation_step2.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.data = data;

        //위쪽 날짜선택 Listview
        list_date.setCellFactory(new Callback<ListView<Calendar>, ListCell<Calendar>>() {
            @Override
            public ListCell<Calendar> call(ListView<Calendar> movieInfoListView) {
                return new CustomDateSelectCell();
            }
        });

        List<Calendar> selectableDateList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Calendar day = Calendar.getInstance();
            day.add(Calendar.DATE, i);
            selectableDateList.add(day);
        }

        BooleanProperty isTaskRunning = new SimpleBooleanProperty(false);
        loading_indicator.visibleProperty().bind(isTaskRunning);

        //아래쪽 스케줄 Listview
        list_schedule.setCellFactory(new Callback<ListView<ScheduleInfo>, ListCell<ScheduleInfo>>() {
            @Override
            public ListCell<ScheduleInfo> call(ListView<ScheduleInfo> movieInfoListView) {
                return new CustomScheduleCell();
            }
        });

        list_date.getItems().setAll(selectableDateList);
        list_date.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Calendar date = list_date.getSelectionModel().getSelectedItem();
                GetScheduleListTask scheduleTask = new GetScheduleListTask(date, data.getMovieSelection(), data.getTheaterSelection());

                scheduleTask.setOnSucceeded(workerStateEvent -> {
                    list_schedule.getItems().setAll(scheduleTask.getValue());
                    isTaskRunning.set(false);
                });

                Thread movieThread = new Thread(scheduleTask, "schedule-thread");
                movieThread.setDaemon(true);
                isTaskRunning.set(true);
                movieThread.start();
            }
        });

        list_schedule.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ScheduleInfo selection = list_schedule.getSelectionModel().getSelectedItem();
                data.setScheduleSelection(selection);
            }
        });
    }

}