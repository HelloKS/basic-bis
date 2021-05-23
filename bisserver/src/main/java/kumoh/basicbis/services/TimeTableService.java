package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.RouteDAO;
import kumoh.basicbis.persistence.RouteDTO;
import kumoh.basicbis.persistence.TimeTableDAO;
import kumoh.basicbis.persistence.TimeTableDTO;

import java.util.ArrayList;

public class TimeTableService implements BaseService {

    /*
    * timetable은 노선의 운행시작시각을 나타낸다
    * 사용되는 곳은 버스시간표안내 화면 - 시간표 클릭 & 노선 검색 화면 - 선택된 노선에서의 시간표 버튼 클릭
    * */
    //TODO : 구현해나가면서 필요한 CODE 작성
    TimeTableDAO timetableDAO = new TimeTableDAO();

    //서비스 코드
    private enum ServiceType{
        UNKNOWN(0),
        GET_ALL_BY_UID(1),
        FIRST_LAST_TIME_BY_UID(2);

        private final int value;
        ServiceType(int value){
            this.value = value;
        }
    }

    /*
    * reqText example
    * code 1 : ProtocolType, Code, tt_routeuid
    *
    * result example
    * code 1 : tt_routeuid, tt_starttime, tt_isholiday\r\n
    * ~~~....
    * */
    @Override
    public String processRequest(String reqText) {
        String result = "";
        TimeTableService.ServiceType svcType = ServiceType.UNKNOWN;

        String[] reqText_split = reqText.split(",");
        //String[] serviceHeader = reqText_split[0].split(",");

        try {
            int svc = Integer.parseInt(reqText_split[1]);
            svcType = TimeTableService.ServiceType.values()[svc];
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (svcType) {
            case GET_ALL_BY_UID:
                    result = timeTableListProvider(reqText_split[2]);
                break;
            case FIRST_LAST_TIME_BY_UID:
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
}
