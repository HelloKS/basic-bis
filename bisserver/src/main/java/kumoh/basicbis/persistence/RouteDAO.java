package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RouteDAO extends BaseDAOImpl{

    /*
    검색으로 노선을 불러옵니다. 키워드가 없을 경우 전체 노선을 리턴합니다.
     */
    public ArrayList<RouteDTO> getRouteByName(String searchQuery) {
        String query = "SELECT * FROM gumibis.bus_route WHERE rt_name LIKE ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<RouteDTO> list = new ArrayList<>();

        try{
            getConnection();
            statement = conn.prepareStatement(query);
            statement.setString(1, "%" + searchQuery + "%");
            resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                RouteDTO routeDTO = new RouteDTO();
                routeDTO.setUid(resultSet.getString("rt_uid"));
                routeDTO.setId(resultSet.getString("rt_id"));
                routeDTO.setName(resultSet.getString("rt_name"));
                list.add(routeDTO);
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

    public String getRouteByBusId(String busUid) {
        String sql = "SELECT st_name FROM bus_stop WHERE st_uid IN" +
                "(SELECT st_uid FROM route_link WHERE rt_uid = ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<BusStopDTO> list = new ArrayList<>();
        StringBuilder sqlResult = new StringBuilder();
        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, busUid);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                sqlResult.append(resultSet.getString("st_name")).append(",");
            }
        }catch (SQLException se){
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
        return sqlResult.toString();
    }
}
