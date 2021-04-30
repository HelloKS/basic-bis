package idol.tofu.server.persistence;

import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;


public class PayoutDTO {
    private Date payoutDate; // 수익정산일
    private int theaterId;
    private int amount; //수익금액

    public PayoutDTO() {}

    public PayoutDTO(Date payoutDate, int theaterId, int amount) {
        this.payoutDate = payoutDate;
        this.theaterId = theaterId;
        this.amount = amount;
    }

    public Date getPayoutDate() {
        return payoutDate;
    }

    public void setPayoutDate(Date payoutDate) {
        this.payoutDate = payoutDate;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(payoutDate) + ',' + theaterId + ',' + amount;
    }
}
