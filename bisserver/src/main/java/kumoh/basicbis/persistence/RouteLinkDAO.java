package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RouteLinkDAO extends BaseDAOImpl{
    public RouteLinkDAO(){}

    /*
    * 경유 정류장 반환
    * 노선의 UID를 주면 다음을 리턴:
    * 방문순서(rl_num), 버스정류장UID(st_uid), 버스정류장이름(st_name)
    * (방문 순서로 정렬)
    * */
    public ArrayList<RouteLinkDTO> getRouteLink(String routeUid)
    {
        String sql="SELECT rl.rl_num, rl.st_uid, bs.st_name FROM gumibis.route_link rl INNER JOIN gumibis.bus_stop bs ON rl.st_uid = bs.st_uid WHERE rl.rt_uid = ? ORDER BY rl.rl_num ASC";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<RouteLinkDTO> list = new ArrayList<>();

        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, routeUid);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                RouteLinkDTO routeLinkDTO = new RouteLinkDTO();
                routeLinkDTO.setRouteOrder(resultSet.getInt("rl_num"));
                routeLinkDTO.setBusStopUid(resultSet.getInt("st_uid"));
                routeLinkDTO.setBusStopName(resultSet.getString("st_name"));
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
