package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.SeatDAO;
import idol.tofu.server.persistence.SeatDTO;
import idol.tofu.server.persistence.ScreenDAO;
import idol.tofu.server.persistence.ScreenDTO;
import idol.tofu.server.persistence.JoinDAO;

import java.util.ArrayList;

public class SeatService {
    public enum Indicator {
        CODE(1), SCH_UID(2);
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
        SeatDAO seatDAO = new SeatDAO();
        ScreenDAO screenDAO = new ScreenDAO();
        JoinDAO joinDAO = new JoinDAO();
        int seatCount;
        StringBuilder funcResult = new StringBuilder();
        ArrayList<SeatDTO> seatlist = new ArrayList<SeatDTO>();
        ScreenDTO screenlist = new ScreenDTO();
        String[] parsedText = reqText.split(",");
        screenlist = joinDAO.getScreenInfo(parsedText[Indicator.SCH_UID.value]);
        funcResult.append(screenlist.toString()).append(",");
        seatCount = joinDAO.countOccupied(parsedText[Indicator.SCH_UID.value]);
        funcResult.append(seatCount).append(",");
        seatlist = joinDAO.isOccupied(parsedText[Indicator.SCH_UID.value]);
        for(SeatDTO seatDTO : seatlist) {
            funcResult.append(seatDTO.toString()).append(",");
        }
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = funcResult.toString();
        return result;
    }
}
