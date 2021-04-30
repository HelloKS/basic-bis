package idol.tofu.server.persistence;

import java.io.Serializable;


public class ScreenDTO {
    private int screenNumber;
    private int theaterId;
    private int rowNumber; //좌석 열 번호
    private int seatPerRow; //1열당 좌석 수

    public ScreenDTO() {}
    public ScreenDTO(int screenNumber, int theaterId, int rowNumber, int seatPerRow) {
        this.screenNumber = screenNumber;
        this.theaterId = theaterId;
        this.rowNumber = rowNumber;
        this.seatPerRow = seatPerRow;
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

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatPerRow() {
        return seatPerRow;
    }

    public void setSeatPerRow(int seatPerRow) {
        this.seatPerRow = seatPerRow;
    }

    @Override
    public String toString() {
        return "" +screenNumber +  "," +
                theaterId +  "," +
                rowNumber +  "," +
                seatPerRow;

    }
}
