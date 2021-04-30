package idol.tofu.server.persistence;

import javax.naming.spi.ResolveResult;
import java.io.Serializable;

public class ReservationDTO {
    private int uid;
    private String userId;
    private int billingId; // 결제내역 고유번호
    private int scheduleId; // 상영 스케줄 고유번호

    public ReservationDTO() {}
    public ReservationDTO(int uid, String userId, int billingId, int scheduleId) {
        this.uid = uid;
        this.userId = userId;
        this.billingId = billingId;
        this.scheduleId = scheduleId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBillingId() {
        return billingId;
    }

    public void setBillingId(int billingId) {
        this.billingId = billingId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }


    @Override
    public String toString() {
        return "" + uid +  "," +
                userId +  "," +
                billingId +  "," +
                scheduleId
                ;
    }
}
