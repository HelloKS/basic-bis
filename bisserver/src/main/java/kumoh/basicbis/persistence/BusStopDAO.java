package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusStopDAO extends BaseDAOImpl{
    public BusStopDAO(){}


    /*
    * SQL 설명: busStopName(버스정류장명)으로 버스 노선이 경유하는 버스 정류장의 모든 정보를 가져온다
    * 사용처1: 경로검색 기능 - 출발/도착 정류장 검색에서 사용 가능
    * 사용처2: 버스 정류장 안내 화면 - 출발/도착 정류장 검색에서 사용
    *  */
    public ArrayList<BusStopDTO> getBusStopByBusName(String busStopName)
    {
        String sql = "select * from bus_stop where st_name like \"%?%\";";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<BusStopDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1,busStopName);
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

    /*
    * SQL 설명 : 정류장 테이블에서 고유번호(PK)로 원하는 노선의 상세정보 가져오기
    * PK로 검색하기 때문에 항상 반환은 1개
    * 사용처1: 버스정류장 안내 화면 - 정류장 고유번호 검색
    * */
    public ArrayList<BusStopDTO> getBusStopByBusStopID(int busstopUid)
    {
        String sql = "SELECT * FROM bus_stop WHERE st_uid = ?;";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<BusStopDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setInt(1,busstopUid);
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


    /*
    * SQL 설명 : busUid 버스 노선이 경유하는 버스 정류장의 모든 정보를 가져온다
    * 사용처1: 버스 노선 안내 - 경유 정류장 정보 출력
    * */
    public ArrayList<BusStopDTO> getBusStopByBusuid(String busUid)
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
