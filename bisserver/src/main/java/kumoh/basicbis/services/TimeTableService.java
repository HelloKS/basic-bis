package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.TimeTableDAO;
import kumoh.basicbis.persistence.TimeTableDTO;

import java.util.ArrayList;

public class TimeTableService implements BaseService{

    /*
    * timetable은 노선의 운행시작시각을 나타낸다
    * 사용되는 곳은 버스시간표안내 화면 - 시간표 클릭 & 노선 검색 화면 - 선택된 노선에서의 시간표 버튼 클릭
    *
    * */
    public enum RES_Code{
        ACCEPT(1), FAIL(2);
        private final int value;
        RES_Code(int value){
            this.value = value;
        }
    }

    /*
    * 노선uid를 주고 해당 노선의 운행시간표를 끌고와서 string으로 만들고 반환하는 함수
    * 입력 : 프로토콜 타입+노선 uid
    * 출력 : 프로토콜 타입+운행시간표 정보
    * reqText example
    * ProtocolType,tt_routeuid
    *
    * result example
    * ProtocolType(TIMETABLE_RES),tt_routeuid,tt_starttime,tt_isholiday,tt_routeuid,....
    * */
    @Override
    public String processRequest(String reqText) {
        String result = null;
        StringBuilder funcResult = new StringBuilder();
        TimeTableDAO timeTableDAO = new TimeTableDAO();
        ArrayList<TimeTableDTO> list = null;

        int type = ProtocolType.TIMETABLE_RES.getType();
        String[] parsedText = reqText.split(",");

        list = timeTableDAO.getTimetable(parsedText[1]);    //reqText structure : ProtocolType,tt_routeuid

        //getTimeTable의 검색결과가 없어서 null이 반환될 경우 for문 이전에 null 검사가 필요

        for(TimeTableDTO index : list)
        {
            funcResult.append(index.toString()).append(",");
        }
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = type + "," + funcResult.toString();
        return result;
    }
}
