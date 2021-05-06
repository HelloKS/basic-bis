package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusStopDAO extends BaseDAOImpl{
    public BusStopDAO(){}


    /*
    *
   * */
    public ArrayList<BusStopDTO> getBusStop(String busUid)
    {
        String sql = "SELECT * FROM bus_stop WHERE st_uid IN " +
                "(SELECT st_uid FROM route_link WHERE rt_uid = ?);";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<BusStopDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1,busUid);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                BusStopDTO busStopDTO = new BusStopDTO();
                busStopDTO.setUid(resultSet.getInt("st_uid"));
                busStopDTO.setServiceId(resultSet.getInt("st_svcid"));
                busStopDTO.setName(resultSet.getString("st_name"));
                busStopDTO.setMapX(resultSet.getDouble("st_mapx"));
                busStopDTO.setMapY(resultSet.getDouble("st_mapy"));
                list.add(busStopDTO);
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
