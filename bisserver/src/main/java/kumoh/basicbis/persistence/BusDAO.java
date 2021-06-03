package kumoh.basicbis.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BusDAO extends BaseDAOImpl{
    public BusDAO(){}

    /*
     * SQL 설명: 출발정류장uid와 도착정류장uid를 입력받아 둘 다 경유하는 모든 버스 노선을 출력한다
     * 사용처1 : 경로검색 기능 - 길찾기 검색
     *
     *
     * */
    public ArrayList<BusDTO> getBusByBusStop(String startUid, String endUid)
    {
        String sql = "select * from bus_route where rt_uid IN " +
                "(select a.rt_uid from route_link AS a, route_link AS b where a.rt_uid = b.rt_uid " +
                "AND a.st_uid = ? AND b.st_uid = ?);";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<BusDTO> list = new ArrayList<>();
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, startUid);
            statement.setString(2, endUid);
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

    /*
    * SQL 설명 : 버스노선 테이블에서 모든 정보를 출력한다.
    * 사용처1: 버스시간표 안내 화면 - 노선번호, 출발지&도착지 정보
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


    /*
    * SQL 설명 : 버스노선 테이블에서 ID로 버스정보 출력(UID가 아님!!)
    * 사용처1: 노선검색 화면 - 노선번호 검색으로 정보 가져오기 EX) 190 검색하면 190,190-1,190-3 등이 출력
    * */
    public ArrayList<BusDTO> getBusDetailById(String id)
    {
        String sql = "SELECT * FROM bus_route WHERE rt_id = ?;";
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


    /*
    * SQL 설명 : 버스노선 테이블에서 uid로 해당 노선의 상세정보 가져오기
    * 사용처1 : 버스 노선 안내 화면 - 1개 노선의 상세정보 가져오기
    * */
    public ArrayList<BusDTO> getOneBusDetail(String uid)
    {
        String sql = "SELECT * FROM bus_route WHERE rt_uid = ?;";
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
