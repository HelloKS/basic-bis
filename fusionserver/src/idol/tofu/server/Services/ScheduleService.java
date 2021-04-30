package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.ScheduleDAO;
import idol.tofu.server.persistence.ScheduleDTO;

import java.util.ArrayList;

public class ScheduleService {
    public enum Indicator {
        CODE(1);
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
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        StringBuilder funcResult = new StringBuilder();
        ArrayList<ScheduleDTO> list = new ArrayList<ScheduleDTO>();
        int type = ProtocolType.SCH_RES.getType();
        list = scheduleDAO.searchPlaying();
        for(ScheduleDTO scheduleDTO : list) {
            funcResult.append(scheduleDTO.toString()).append(",");
        }
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = type+funcResult.toString();
        return result;
    }
}
