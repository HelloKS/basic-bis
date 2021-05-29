package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TimeTableDAO extends BaseDAOImpl{

    public TimeTableDAO(){}

    /*
    * SQL 설명 : timetable에서 버스UID의 시간표 전체 출력
    * 사용처1 : 버스시간표 안내 화면 - 노선의 배차시간표(timetable) 요청
    * XXX : 실패 시(검색결과가 없을 경우) : while문을 돌지 않고 바로 나오기 때문에 list가 null인 상태??
    */
    public ArrayList<TimeTableDTO> getTimetableByBusUid(String uid){
        String sql = "SELECT * FROM timetable WHERE tt_routeid = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<TimeTableDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, uid);
            resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                TimeTableDTO timetable = new TimeTableDTO();
                timetable.setUid(resultSet.getString("tt_routeid"));
                timetable.setStartTime(resultSet.getTime("tt_starttime"));
                timetable.setHoliday(resultSet.getBoolean("tt_isholiday"));
                list.add(timetable);
            }
        }catch(SQLException se){
            se.printStackTrace();
        }finally{
            try{
                if(resultSet != null) resultSet.close();
                if(statement != null) statement.close();
                if(conn != null) conn.close();
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return list;
    }

    /*
    * SQL 설명 : 각각 버스노선UID의 !!평일!! 첫차시간과 막차시간을 가져옴
    * 사용처1 : 버스 노선 안내 화면 - 첫차시간&막차시간 출력
    * 사용처2 : 노선 검색 화면 - 결과항목 중 노선 하나 선택시, 노선 정보가 출력
    * 솔직히 제대로 작동하는지는 테스트 해봐야함
    * */
    public ArrayList<TimeTableDTO> getFirstLastTimetable(String uid){

        // 가능하면 쿼리는 한번만 날리는게 어떤가요?
        String firstSql =
                "(SELECT * FROM timetable WHERE tt_isholiday = '0' AND tt_routeid = ? ORDER BY tt_starttime ASC LIMIT 1)" +
                "UNION ALL" + //속도가 빨라지는 마법
                "(SELECT * FROM timetable WHERE tt_isholiday = '0' AND tt_routeid = ? ORDER BY tt_starttime DESC LIMIT 1);";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<TimeTableDTO> list = new ArrayList<>();
        try {
            getConnection();
            statement = conn.prepareStatement(firstSql);
            statement.setString(1, uid); //ASC
            statement.setString(2, uid); //DESC
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                TimeTableDTO timetable = new TimeTableDTO();
                timetable.setUid(resultSet.getString("tt_routeid"));
                timetable.setStartTime(resultSet.getTime("tt_starttime"));
                timetable.setHoliday(resultSet.getBoolean("tt_isHoliday"));
                list.add(timetable);
            }

        } catch(SQLException se) {
            se.printStackTrace();
        } finally {
            try{
                if(resultSet != null) resultSet.close();
                if(statement != null) statement.close();
                if(conn != null) conn.close();
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return list;
    }
}