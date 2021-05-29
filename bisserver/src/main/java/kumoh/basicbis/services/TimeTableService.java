package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.RouteDAO;
import kumoh.basicbis.persistence.RouteDTO;
import kumoh.basicbis.persistence.TimeTableDAO;
import kumoh.basicbis.persistence.TimeTableDTO;

import java.util.ArrayList;

public class TimeTableService implements BaseService {
    public enum Indicator {
        CODE(1),
        BUS_UID(2);

        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }

    private enum Code {
        UNKNOWN(0),
        GET_ALL_BY_UID(1),
        FIRST_LAST_TIME_BY_UID(2);

        private final int value;

        Code(int value) {
            this.value = value;
        }
    }

    TimeTableDAO timetableDAO = new TimeTableDAO();

    /*
     * reqText example
     * code 1 : ProtocolType, Code, tt_routeuid
     *
     * result example
     * code 1 : tt_routeuid, tt_starttime, tt_isholiday\r\n
     * code 2 :
     * ~~~....
     * */
    @Override
    public String processRequest(String reqText) {
        String result = "";
        String[] parsedText = reqText.split(",");

        int codeIndex = Integer.parseInt(parsedText[Indicator.CODE.value]);
        TimeTableService.Code code = TimeTableService.Code.values()[codeIndex];

        switch (code) {
            case GET_ALL_BY_UID:
                result = timeTableListProvider(parsedText[Indicator.BUS_UID.value]);
                break;
            case FIRST_LAST_TIME_BY_UID:
                result = fisrtEndTimeProvider(parsedText[Indicator.BUS_UID.value]);
                break;
            default:
                break;
        }
        return result;
    }

    private String timeTableListProvider(String query) {
        ArrayList<TimeTableDTO> tables;
        StringBuilder sbuilder = new StringBuilder();

        tables = timetableDAO.getTimetableByBusUid(query);

        for (TimeTableDTO time : tables) {
            sbuilder.append(time.toString()).append("\r\n");
        }

        return sbuilder.toString();
    }

    private String fisrtEndTimeProvider(String requestBody){
        ArrayList<TimeTableDTO> list;
        StringBuilder stringBuilder = new StringBuilder();
        list = timetableDAO.getFirstLastTimetable(requestBody);

        for(TimeTableDTO index : list){
            stringBuilder.append(index.toString()).append("\r\n");
        }
        return stringBuilder.toString();
    }
}
