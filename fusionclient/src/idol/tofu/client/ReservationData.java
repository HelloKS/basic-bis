package idol.tofu.client;

import idol.tofu.client.persistence.MovieInfo;
import idol.tofu.client.persistence.ScheduleInfo;
import idol.tofu.client.persistence.TheaterInfo;

public class ReservationData {
    private TheaterInfo TheaterSelection;
    private MovieInfo MovieSelection;
    private ScheduleInfo ScheduleSelection;
    private int SeatNum;

    public TheaterInfo getTheaterSelection() {
        return TheaterSelection;
    }

    public void setTheaterSelection(TheaterInfo theaterSelection) {
        TheaterSelection = theaterSelection;
    }

    public MovieInfo getMovieSelection() {
        return MovieSelection;
    }

    public void setMovieSelection(MovieInfo movieSelection) {
        MovieSelection = movieSelection;
    }

    public ScheduleInfo getScheduleSelection() {
        return ScheduleSelection;
    }

    public void setScheduleSelection(ScheduleInfo scheduleSelection) {
        ScheduleSelection = scheduleSelection;
    }

    public int getSeatNum() {
        return SeatNum;
    }

    public void setSeatNum(int seatNum) {
        SeatNum = seatNum;
    }
}
