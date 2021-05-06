package kumoh.basicbis.persistence;

import java.io.Serializable;
import java.sql.Date;

public class TimeTableDTO implements Serializable {

    private string uid;
    private Date startTime; //배차시작시각
    private boolean isHoliday; //휴일배차여부

    public TimeTableDTO() {
    }

    public TimeTableDTO(string uid, Date startTime, boolean isHoliday) {
        this.uid = uid;
        this.startTime = startTime;
        this.isHoliday = isHoliday;
    }

    public string getUid() {
        return uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }
    //uid가 Route에서 끌고온 fk이므로 setUid는 없습니다

    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public boolean isHoliday() {
        return isHoliday;
    }
    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return uid +
               "," + startTime +
               "," + isHoliday +
               ',';
    }
}
