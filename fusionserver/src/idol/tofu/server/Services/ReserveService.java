package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.*;

import java.util.ArrayList;

public class ReserveService {
    public enum REQ_Code {
        SCH_LIST(1),
        CECHK_RATING(2),
        SET_SEAT(3);

        private final int value;

        REQ_Code(int value) {
            this.value = value;
        }
    }

    public enum RES_Code {
        SCH_LIST(1),
        ADULT(2),
        TEENAGER(3),
        SUCCESS_SET_SEAT(4), FAIL_SET_SEAT(5);

        private final int value;

        RES_Code(int value) {
            this.value = value;
        }
    }

    /*
        reqText example
        code 1: "ProtocolType,code,thtr_uid,mov_uid,startime"
        code 2: "ProtocolType,code,mem_id,sch_uid"
        code 3: "ProtocolType,code,mem_id,mov_uid,sch_uid,no_of_seat,seat_no(1,2,3,4)"
     */
    public String processRequest(String reqText) {
        String result = null;

        StringBuilder funcResult = new StringBuilder();
        int type = ProtocolType.RESERVE_RES.getType();
        String[] parsedText = reqText.split(",");
        REQ_Code req_code = REQ_Code.values()[Integer.parseInt(parsedText[2])];
        int res_code = 0;
        switch (req_code) {
            case SCH_LIST: {
                ScheduleDAO scheduleDAO = new ScheduleDAO();
                ArrayList<ScheduleDTO> list = new ArrayList<ScheduleDTO>();
                res_code = RES_Code.SCH_LIST.value;
                list = scheduleDAO.searchScheduleInTheater(parsedText[2], parsedText[3], parsedText[4]);
                funcResult.append(list.size()).append(",");
                for (ScheduleDTO scheduleDTO : list) {
                    funcResult.append(scheduleDTO.toString()).append(",");
                }
            }
            break;
            case CECHK_RATING: {
                int movRating;
                int memAge;
                JoinDAO joinDAO = new JoinDAO();
                movRating = joinDAO.getMovieRating(parsedText[3]);
                AccountDAO accountDAO = new AccountDAO();
                memAge = Integer.parseInt(accountDAO.getMemberAge(parsedText[2]));
                if(movRating > memAge) {
                    res_code = RES_Code.TEENAGER.value;
                }
                else {
                    res_code = RES_Code.ADULT.value;
                }
            }
            break;
            case SET_SEAT: {
                int billUID;
                String resvUID;

                JoinDAO joinDAO = new JoinDAO();
                MovieDAO movieDAO = new MovieDAO();
                SeatDAO seatDao = new SeatDAO();
                ReservationDAO reservationDAO = new ReservationDAO();

                MovieDTO movieDTO = new MovieDTO();
                ArrayList<SeatDTO> seatList = new ArrayList<SeatDTO>();

                String[] seats = new String[Integer.parseInt(parsedText[5])];
                seatList = joinDAO.isOccupied(parsedText[4]);  //선점된 좌석정보를 가져옴
                String[] seatsOccupied = new String[seatList.size()];

                if (seats.length >= 0)  // 사용자가 선택한 좌석들을 seats 에 저장
                    System.arraycopy(parsedText, 6, seats, 0, seats.length);

                int index = 0;
                for (SeatDTO seatDTO : seatList) {  //선점된 좌석번호들을 seatsOccupied 에 저장
                    seatsOccupied[index++] = Integer.toString(seatDTO.getSeatNumber());
                }

                boolean isOccupy = false;
                // 사용자가 선택한 좌석이 선점되어있는지 검사하고 선점되어 있으면 isOccupy 에 true 저장
                for (int i = 0; i < seatsOccupied.length; i++) {
                    for (int j = 0; j < seats.length; j++) {
                        if (seatsOccupied[i].equals(seats[i])) {
                            isOccupy = true;
                            break;
                        }
                    }
                    if (isOccupy) {
                        break;
                    }
                }
                // 선점된 좌석을 선택하였으면 res_code 에 4 저장
                if (isOccupy) {
                    res_code = RES_Code.FAIL_SET_SEAT.value;
                } else {  // 선택한 좌석중에 선점된 좌석이 없으면, 결제정보, 예매정보를 만든 후 좌석 DB 에 하나씩 좌석정보를 추가함.
                    int insertResult = 0;
                    movieDTO = movieDAO.showMovieDetails(parsedText[3]);
                    BillingDAO billingDAO = new BillingDAO();
                    billingDAO.insert(Integer.toString(movieDTO.getPrice()), null, null, "0", null);
                    billUID = billingDAO.getCurrentBillId();
                    reservationDAO.insert(parsedText[2], Integer.toString(billUID), parsedText[4]);
                    resvUID = reservationDAO.getReservationUid(Integer.toString(billUID));

                    for (String seat : seats) {
                        insertResult = seatDao.insert(resvUID, seat);
                    }
                    if (insertResult == 1) {
                        res_code = RES_Code.SUCCESS_SET_SEAT.value;
                    } else {
                        res_code = RES_Code.FAIL_SET_SEAT.value;
                    }
                }
            }
            break;
            default:
                break;
        }
        if(funcResult.length() != 0) {
            funcResult.deleteCharAt(funcResult.lastIndexOf(","));
            result = type + "," + res_code + funcResult.toString();
        }
        else {
            result = type + "," + res_code;
        }

        return result;
    }
}
