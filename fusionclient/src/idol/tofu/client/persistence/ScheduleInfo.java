package idol.tofu.client.persistence;

import java.util.Calendar;

public class ScheduleInfo {
    private int uid;
    private int movieId;
    private int screenNumber; // 상영관 번호
    private int theaterId; // 영화관 고유 번호
    private Calendar startTime;

    public ScheduleInfo(int uid, int movieId, int screenNumber, int theaterId, Calendar startTime) {
        this.uid = uid;
        this.movieId = movieId;
        this.screenNumber = screenNumber;
        this.theaterId = theaterId;
        this.startTime = startTime;
    }

    /*
    public ScheduleInfo(int uid, int movieId, int screenNumber, int theaterId, String startTime) {
        this.uid = uid;
        this.movieId = movieId;
        this.screenNumber = screenNumber;
        this.theaterId = theaterId;
        this.startTime = new Date(Timestamp.valueOf(startTime).getTime());
    }

     */

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

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

}
