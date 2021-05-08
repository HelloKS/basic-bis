package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.TimeTableDAO;
import kumoh.basicbis.persistence.TimeTableDTO;

import java.util.ArrayList;

public class TimeTableService implements BaseService{

    /*
    * timetable은 노선의 운행시작시각을 나타낸다
    * 사용되는 곳은 버스시간표안내 화면 - 시간표 클릭 & 노선 검색 화면 - 선택된 노선에서의 시간표 버튼 클릭
    * */
    //TODO : 구현해나가면서 필요한 CODE 작성
    public enum Indicator{
        CODE(1), TIMETABLE_UID(2);
        private final int value;
        Indicator(int value){
            this.value = value;
        }
    }
    public enum Code{
        TIMETABLE(1);
        private final int value;
        Code(int value){
            this.value = value;
        }
    }

    /*
    * reqText example
    * code 1 : ProtocolType, Code, tt_routeuid
    *
    * result example
    * code 1 : ProtocolType, Code, tt_routeuid, tt_starttime, tt_isholiday,....
    * */
    @Override
    public String processRequest(String reqText) {
        String result = null;
        StringBuilder funcResult = new StringBuilder();
        TimeTableDAO timeTableDAO = new TimeTableDAO();
        ArrayList<TimeTableDTO> list = null;

        int type = ProtocolType.TIMETABLE_RES.getType();
        String[] parsedText = reqText.split(",");
        Code code = Code.values()[Integer.parseInt(parsedText[TimeTableService.Indicator.CODE.value])];

        switch (code){
            case TIMETABLE: { // 배차운행시간표 화면 - 해당 노선의 배차시간표 요청
                // parsedText structure : ProtocolType,code,tt_routeuid
                // getTimeTable의 검색결과가 없어서 null이 반환될 경우 for문 이전에 null 검사가 필요
                list = timeTableDAO.getTimetable(parsedText[Indicator.TIMETABLE_UID.value]);

                for (TimeTableDTO index : list) {
                    funcResult.append(index.toString()).append(",");
                }
                break;
            }
            default:
                break;
        }
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = type + "," + code.value + "," + funcResult.toString();
        return result;
    }
}
