package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusDAO extends BaseDAOImpl{
    public BusDAO(){}

    /*
    * 버스시간표 안내 화면 - 노선번호, 출발지&도착지 정보
    * rt_id : 노선명, rt_name : 노선명(출발지&도착지) 정보
    * */
    public ArrayList<BusDTO> getAllBusDetail()
    {
        String sql = "SELECT * FROM bus_route;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<BusDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                BusDTO busDTO = new BusDTO();
                busDTO.setUid(resultSet.getString("rt_uid"));
                busDTO.setId(resultSet.getString("rt_id"));
                busDTO.setName(resultSet.getString("rt_name"));
                list.add(busDTO);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
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
