package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RouteLinkDAO extends BaseDAOImpl{
    public RouteLinkDAO(){}

    /*
     * SQL 설명: 출발정류장pk와 도착정류장pk를 입력받아 둘 다 경유하는 버스 노선uid를 출력한다
     * 사용처1 : 경로검색 기능 - 길찾기 검색
     *
     *
     * */
    public ArrayList<RouteLinkDTO> getBusByBusStop(String startUid, String endUid)
    {
        String sql = "select a.*, b.st_uid from route_link a " +
                "inner join route_link b on a.st_uid = ? and b.st_uid = ?;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<RouteLinkDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, startUid);
            statement.setString(2, endUid);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                RouteLinkDTO routeLinkDTO = new RouteLinkDTO();
                routeLinkDTO.setBusId(resultSet.getString("rt_uid"));
                routeLinkDTO.setRouteNumber(resultSet.getInt("rl_num"));
                routeLinkDTO.setBusStopId(resultSet.getString("st_uid"));
                list.add(routeLinkDTO);
            }
        }catch (SQLException se) {
            se.printStackTrace();
        }finally {
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
    * 이건 어따 쓰지
    * 노선의 UID를 가지고 route_link 테이블에서 검색(노선명,정류장번호,정류장 고유번호)
    *
    * */
    public ArrayList<RouteLinkDTO> getRouteLink(String busUid)
    {
        String sql="SELECT * FROM route_link WHERE rt_uid = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<RouteLinkDTO> list = new ArrayList<>();

        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1,busUid);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                RouteLinkDTO routeLinkDTO = new RouteLinkDTO();
                routeLinkDTO.setBusId(resultSet.getString("rt_uid"));
                routeLinkDTO.setRouteNumber(resultSet.getInt("rl_num"));
                routeLinkDTO.setBusStopId(resultSet.getString("st_uid"));
                list.add(routeLinkDTO);
            }
        }catch (SQLException se) {
            se.printStackTrace();
        }finally {
            try{
                if(resultSet != null) resultSet.close();
                if(statement != null) statement.close();
                if(conn != null) conn.close();
            }catch (Exception e){
                throw new RuntimeException((e.getMessage()));
            }
        }
        return list;
    }
}
