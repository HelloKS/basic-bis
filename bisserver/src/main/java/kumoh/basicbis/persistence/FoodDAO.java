package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodDAO extends BaseDAOImpl{

    public FoodDAO(){}


    public ArrayList<FoodDTO> getFoodbyBusStopUid(int busstopUid)
    {
        //버스정류장 좌표값 +-1로 범위 잡아서 단순하게 출력을 해볼까 고민
        String sql = "SELECT * FROM restaurant WHERE ";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<FoodDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                FoodDTO foodDTO = new FoodDTO();
                //채워넣어
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
