package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.TheaterDAO;
import idol.tofu.server.persistence.TheaterDTO;

import java.util.ArrayList;

public class TheaterService {
    public enum Indicator {
        CODE(1), THTR_UID(2);

        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }

    public enum Code {
        THTR_LIST_REQ(1), THTR_REQ(2);

        private final int value;

        Code(int value) {
            this.value = value;
        }
    }
    /*
        reqText example
        code 1: "ProtocolType,code"
        code 2: "ProtocolType,code,thtr_uid"
     */
    public String processRequest(String reqText) {
        String result = null;
        TheaterDAO theaterDAO = new TheaterDAO();
        StringBuilder funcResult = new StringBuilder();
        ArrayList<TheaterDTO> list;
        int type = ProtocolType.THTR_REQ.getType();
        String[] parsedText = reqText.split(",");
        Code code = Code.values()[Integer.parseInt(parsedText[Indicator.CODE.value])];
        switch (code) {
            case THTR_LIST_REQ:   // 전체 영화관 목록 요청
                list = theaterDAO.getAllTheater();
                funcResult.append(list.size() + ",");
                for (TheaterDTO movieDTO : list) {
                    funcResult.append(movieDTO.toString()).append(",");
                }
            break;
            default:
                break;
        }
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = type + "," +code.value+ "," + funcResult.toString();
        return result;
    }
}
