package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.ScreenDAO;
import idol.tofu.server.persistence.ScreenDTO;

import java.util.ArrayList;

public class ScreenService {
    public enum Indicator {
        THTR_UID(2);

        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }

    // 각각의 요청에 대한 응답의 코드가 일치함.
    public enum Code {
        UNKNOWN(0),
        SCR_LIST_REQ(1), SCR_RES(2);  //

        private final int value;

        Code(int value) {
            this.value = value;
        }
    }

    /*
        reqText example
        code 1: "ProtocolType,code"
        code 2: "ProtocolType,code,scr_uid"
     */
    public String processRequest(String reqText) {
        String result = null;
        ScreenDAO screenDAO = new ScreenDAO();
        StringBuilder funcResult = new StringBuilder();
        ArrayList<ScreenDTO> list;
        int type = ProtocolType.SCR_RES.getType();
        String[] parsedText = reqText.split(",");
        //특정 영화관 상영관 목록 요청
        list = screenDAO.searchScreenInTheater(parsedText[Indicator.THTR_UID.value]);
        for (ScreenDTO screenDTO : list) {
            funcResult.append(screenDTO.toString()).append(",");
        }

        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = type + "," + funcResult.toString();
        return result;
    }
}