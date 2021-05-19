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
                timetable.setUid(resultSet.getString("tt_uid"));
                timetable.setStartTime(resultSet.getDate("tt_starttime"));
                timetable.setHoliday(resultSet.getBoolean("tt_isHoliday"));
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
    * SQL 설명 : sql을 2번 써서 각각 버스노선UID의 !!평일!! 첫차시간과 막차시간을 가져옴
    * 사용처1 : 버스 노선 안내 화면 - 첫차시간&막차시간 출력
    * 솔직히 제대로 작동하는지는 테스트 해봐야함
    * */
    public ArrayList<TimeTableDTO> getFirstLastTimetable(String uid){
        String firstSql = "select * from timetable  where isholiday = 0 and tt_routeid = ?" +
                " order by tt_starttime asc limit 1;";
        String secondSql = "select * from timetable  where isholiday = 0 and tt_routeid = 1010" +
                " order by tt_starttime desc limit 1;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<TimeTableDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(firstSql);
            statement.setString(1, uid);
            resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                TimeTableDTO timetable = new TimeTableDTO();
                timetable.setUid(resultSet.getString("tt_uid"));
                timetable.setStartTime(resultSet.getDate("tt_starttime"));
                timetable.setHoliday(resultSet.getBoolean("tt_isHoliday"));
                list.add(timetable);
            }
            statement = conn.prepareStatement(secondSql);
            statement.setString(1, uid);
            resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                TimeTableDTO timetable = new TimeTableDTO();
                timetable.setUid(resultSet.getString("tt_uid"));
                timetable.setStartTime(resultSet.getDate("tt_starttime"));
                timetable.setHoliday(resultSet.getBoolean("tt_isHoliday"));
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
}