package idol.tofu.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ReservationController {
    @FXML BorderPane content_pane;
    @FXML Button prevBtn, nextBtn;
    @FXML Label top_heading;

    ReservationData reservationData;

    private enum ReservationStep {
        SELECT_MOVIE_THEATER,
        SELECT_SCHEDULE,
        SELECT_SEAT,
        BILLING
    }

    ReservationStep currentStep;

    @FXML
    public void initialize() {
        reservationData = new ReservationData();
        handleSteps(ReservationStep.SELECT_MOVIE_THEATER);
    }

    @FXML
    public void handlePrevBtn(ActionEvent actionEvent) {
        switch (currentStep) {
            case SELECT_SCHEDULE:
                handleSteps(ReservationStep.SELECT_MOVIE_THEATER);
                break;
            case SELECT_SEAT:
                handleSteps(ReservationStep.SELECT_SCHEDULE);
                break;
            case BILLING:
                handleSteps(ReservationStep.SELECT_SEAT);
                break;
        }
    }

    @FXML
    public void handleNextBtn(ActionEvent actionEvent) {
        switch (currentStep) {
            case SELECT_MOVIE_THEATER:
                handleSteps(ReservationStep.SELECT_SCHEDULE);
                break;
            case SELECT_SCHEDULE:
                handleSteps(ReservationStep.SELECT_SEAT);
                break;
            case SELECT_SEAT:
                handleSteps(ReservationStep.BILLING);
                break;
        }
    }

    private void handleSteps(ReservationStep step) {
        switch (step) {
            case SELECT_MOVIE_THEATER:
                currentStep = ReservationStep.SELECT_MOVIE_THEATER;
                ReservationStep1Controller step1 = new ReservationStep1Controller(reservationData);
                top_heading.setText("영화관 및 영화 선택");
                content_pane.setCenter(step1);
                prevBtn.setVisible(false);
                break;
            case SELECT_SCHEDULE:
                currentStep = ReservationStep.SELECT_SCHEDULE;
                ReservationStep2Controller step2 = new ReservationStep2Controller(reservationData);
                top_heading.setText("시간 선택");
                content_pane.setCenter(step2);
                prevBtn.setVisible(true);
                break;
            case SELECT_SEAT:
                currentStep = ReservationStep.SELECT_SEAT;
                break;
            case BILLING:
                currentStep = ReservationStep.BILLING;
                break;
        }
    }

}
