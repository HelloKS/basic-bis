package idol.tofu.server.persistence;

import java.io.Serializable;

public class SeatDTO {
    private int uid;
    private int seatNumber; // 좌석번호, 일렬로 세웠을 때의 좌석 번호
    private int reservationId; // 예매 고유 번호

    public SeatDTO() {}

    public SeatDTO(int uid, int seatNumber, int reservationId) {
        this.uid = uid;
        this.seatNumber = seatNumber;
        this.reservationId = reservationId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public String toString() {
        return "" + uid +  "," +
                seatNumber +  "," +
                reservationId
                ;
    }
}
