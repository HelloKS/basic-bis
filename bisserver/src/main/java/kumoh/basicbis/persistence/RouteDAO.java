package kumoh.basicbis.persistence;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RouteDAO extends BaseDAOImpl{

    /*
    검색으로 노선을 불러옵니다. 키워드가 없을 경우 전체 노선을 리턴합니다.
     */
    public ArrayList<RouteDTO> getRouteByName(String searchQuery) {
        String query = "SELECT * FROM gumibis.bus_route WHERE rt_id LIKE ?";
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

    public int getRouteNumber(String busUid, String busStopUid){
        String sql = "SELECT rl_num FROM route_link WHERE rt_uid = ? AND st_uid = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<RouteDTO> list = new ArrayList<>();
        int result = 0;
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1,busUid);
            statement.setString(2,busStopUid);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                result = resultSet.getInt("rl_num");
            }
        }catch (SQLException se){
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
        return result;
    }

    public ArrayList<BusDTO> getBusByBusStop(String busStopSVCID){
        String query = "select * from bus_route where rt_uid IN (" +
                "select rt_uid from route_link where st_uid in " +
                "(select st_uid from bus_stop where st_svcid = ?))";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<BusDTO> list = new ArrayList<>();

        try{
            getConnection();
            statement = conn.prepareStatement(query);
            statement.setString(1, busStopSVCID);
            resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                BusDTO busDTO = new BusDTO();
                busDTO.setUid(resultSet.getString("rt_uid"));
                busDTO.setId(resultSet.getString("rt_id"));
                busDTO.setName(resultSet.getString("rt_name"));
                list.add(busDTO);
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
}
