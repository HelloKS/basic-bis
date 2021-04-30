package idol.tofu.server.persistence;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class ScheduleDTO {
    private int uid;
    private int movieId;
    private int screenNumber; // 상영관 번호
    private int theaterId; // 영화관 고유 번호
    private Timestamp startTime;

    public ScheduleDTO() {}

    public ScheduleDTO(int uid, int movieId, int screenNumber, int theaterId, Timestamp startTime) {
        this.uid = uid;
        this.movieId = movieId;
        this.screenNumber = screenNumber;
        this.theaterId = theaterId;
        this.startTime = startTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return  "" +uid +  "," +
                movieId +  "," +
                screenNumber +  "," +
                theaterId +  "," +
                startTime
                ;
    }
}
