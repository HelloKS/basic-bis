package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodDAO extends BaseDAOImpl{

    public FoodDAO(){}


    public ArrayList<FoodDTO> getFoodbyMap(double mapx, double mapy)
    {
        //버스정류장 좌표값 +-1로 범위 잡아서 단순하게 출력을 해볼까 고민
        String sql = "select * from restaurant " +
                "WHERE res_mapx > ? AND res_mapx < ?" +
                "AND res_mapy > ? AND res_mapy < ?;";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<FoodDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setDouble(1,mapx-0.01);
            statement.setDouble(2,mapx+0.01);
            statement.setDouble(3,mapy-0.01);
            statement.setDouble(4,mapy+0.01);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                FoodDTO foodDTO = new FoodDTO();
                foodDTO.setUid(resultSet.getInt("res_uid"));
                foodDTO.setName(resultSet.getString("res_name"));
                foodDTO.setAddress(resultSet.getString("res_address"));
                foodDTO.setPhone(resultSet.getString("res_phone"));
                foodDTO.setMapx(resultSet.getDouble("res_mapx"));
                foodDTO.setMapy(resultSet.getDouble("res_mapy"));
                list.add(foodDTO);
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
