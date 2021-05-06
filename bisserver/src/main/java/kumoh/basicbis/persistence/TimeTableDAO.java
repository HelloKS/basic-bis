package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TimeTableDAO extends BaseDAOImpl{

    public TimeTableDAO(){}

    /*
    * 버스시간표 안내 화면 - 노선의 배차시간표(timetable) 요청
    * 노선의 UID를 가지고 timetable 검색
    */
    //XXX : 실패 시(검색결과가 없을 경우) : while문을 돌지 않고 바로 나오기 때문에 list가 null인 상태??

    public ArrayList<TimeTableDTO> getTimetable(String uid){
        String sql = "SELECT * FROM timetable WHERE tt_routeid = ?";
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<TimeTableDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, uid);
            result = statement.executeQuery();

            while(result.next())
            {
                TimeTableDTO timetable = new TimeTableDTO();
                timetable.setUid(result.getString("tt_uid"));
                timetable.setStartTime(result.getDate("tt_starttime"));
                timetable.setHoliday(result.getBoolean("tt_isHoliday"));
                list.add(timetable);
            }
        }catch(SQLException se){
            se.printStackTrace();
        }finally{
            try{
                if(result != null) result.close();
                if(statement != null) statement.close();
                if(conn != null) conn.close();
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return list;
    }
}