package kumoh.basicbis.persistence;

import java.io.Serializable;
import java.sql.Date;

public class TimeTableDTO implements Serializable {

    private String uid;
    //FIXME : 배차시작시각 자료형 논의 후 수정
    private Date startTime; //배차시작시각
    private boolean isHoliday; //휴일배차여부

    public TimeTableDTO() {
    }

    public TimeTableDTO(String uid, Date startTime, boolean isHoliday) {
        this.uid = uid;
        this.startTime = startTime;
        this.isHoliday = isHoliday;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }
    //FIXME : 자료형에 따라 getter setter 메소드 유의해야 함.
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

    @Override
    public String toString() {
        return uid +
               "," + startTime +
               "," + isHoliday +
               ',';
    }
}
