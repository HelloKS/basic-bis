package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.ReservationDAO;

public class RefundService {
    public enum RES_Code {
        ACCEPT(1), FAIL(2);

        private final int value;
        RES_Code(int value) {
            this.value = value;
        }
    }
    /*
        reqText example
        code : unused
        "ProtocolType,resv_uid"
     */
    public String processRequest(String reqText) {
        String result = null;
        int cancelResult = 0;
        String[] pasredText = reqText.split(",");
        int res_code;
        int type = ProtocolType.REFUND_RES.getType();
        ReservationDAO reservationDAO = new ReservationDAO();
        cancelResult = reservationDAO.cancelMyReservation(pasredText[1]);

        if(cancelResult == 1) {
            res_code = RES_Code.ACCEPT.value;
        }
        else {
            res_code = RES_Code.FAIL.value;
        }

        result = type + "," + res_code;

        return result;
    }
}
