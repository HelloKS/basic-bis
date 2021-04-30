package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.ReservationDAO;
import idol.tofu.server.persistence.ReservationDTO;

import java.util.ArrayList;

public class TicketService {
    public enum Indicator {
        CODE(1), MB_ID(2);
        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }
    /*
        reqText example
        code : unused
        "ProtocolType"
     */
    public String processRequest (String reqText) {
        String result = null;
        ReservationDAO reservationDAO = new ReservationDAO();
        StringBuilder funcResult = new StringBuilder();
        ArrayList<ReservationDTO> list = new ArrayList<ReservationDTO>();
        String[] parsedText = reqText.split(",");
        list = reservationDAO.searchMyReservation(parsedText[Indicator.MB_ID.value]);
        for(ReservationDTO reservationDTO : list) {
            funcResult.append(reservationDTO.toString()).append(",");
        }
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = funcResult.toString();
        return result;
    }
}
