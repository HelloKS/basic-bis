package idol.tofu.server.persistence;

import oracle.jdbc.*;

import java.sql.*;
import java.util.ArrayList;

public class TheaterDAO extends BasicDAOImpl {

    public TheaterDAO() {
    }

    public int insert(String name, String addr, String phone, String accno, String bank) {
        String sql = "INSERT INTO \"theaters\" (THTR_UID, THTR_NAME, THTR_ADDR, THTR_PHONE, THTR_ACCNO, THTR_BANK) values (THTR_UID_PK.NEXTVAL,?,?,?,?,?)";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, addr);
            statement.setString(3, phone);
            statement.setString(4, accno);
            statement.setString(5, bank);

            result = statement.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return result;
    }

    /*
    영화관 전체목록 요청 - 영화관 전체 목록 시나리오
    C가 S에게 영화관 리스트를 요청하면
    S는 theater 테이블에서 THTR_NAME을 검색하여 ResultSet에 저장 후
    DTO에 해당 개체의 정보를 저장하여 ArrayList에 삽입후 반환
    */
    public ArrayList<TheaterDTO> getAllTheater() {
        ArrayList<TheaterDTO> list = new ArrayList<TheaterDTO>();

        String sql = "SELECT * FROM \"theaters\"";

        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                TheaterDTO theater = new TheaterDTO();
                theater.setUid(result.getInt("THTR_UID"));
                theater.setName(result.getString("THTR_NAME"));
                theater.setAddress(result.getString("THTR_ADDR"));
                theater.setAccountNumber(result.getString("THTR_ACCNO"));
                theater.setBank(result.getInt("THTR_BANK"));
                theater.setPhoneNumber(result.getString("THTR_PHONE"));
                list.add(theater);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return list;
    }

    /*
    특정 영화관 상세정보 - 영화관 상세열람 시나리오
    C가 영화관 리스트중 하나를 선택하여 S에게 thtr_uid를 전송
    S는 thtr_uid를 매개변수로 받고 DB에서 해당 thtr_uid를 포함한 레코드의 모든 정보를
    ResultSet에 저장 후 DTO에 해당 개체정보를 저장 후 반환
    */
    /*
     * 결제 시나리오
     * C에게 영화관 수익계좌와 은행을 전송하기 위한 기능
     * 매개로 받은 영화관의 고유번호를 통해 해당 영화관 정보를 DTO객체에 저장하여 반환
     * 다시 수익계좌와 은행을 추출하는 작업은 Service계층에서 필요
     * 재사용성을 위해 전체 정보를 저장하여 반환함
     * */
    public TheaterDTO getTheaterInfo(String THTR_UID) {
        String sql = "SELECT * FROM \"theaters\" WHERE THTR_UID = ?";
        PreparedStatement statement = null;
        ResultSet result = null;
        TheaterDTO theater = new TheaterDTO();

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, THTR_UID);
            result = statement.executeQuery();
            while (result.next()) {
                theater.setName(result.getString("THTR_NAME"));
                theater.setAccountNumber(result.getString("THTR_ACCNO"));
                theater.setAddress(result.getString("THTR_ADDR"));
                theater.setBank(result.getInt("THTR_BANK"));
                theater.setPhoneNumber(result.getString("THTR_PHONE"));
                theater.setUid(result.getInt("THTR_UID"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return theater;
    }

    public int update(String thtr_uid, String thtr_name, String thtr_addr,
                      String thtr_phone)
    {
        String sql = "update \"theaters\" set thtr_name = ?, thtr_addr = ?," +
                " thtr_phone  = ? where thtr_uid = ?";
        PreparedStatement statement = null;
        int result = 0;
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, thtr_name);
            statement.setString(2, thtr_addr);
            statement.setString(3, thtr_phone);
            statement.setString(3, thtr_uid);
            result = statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return result;
    }

    public int delete(String thtr_uid)
    {
        String sql = "DELETE FROM \"theaters\" WHERE thtr_uid = ?";
        PreparedStatement statement = null;
        int result = 0;
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, thtr_uid);
            result = statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return result;
    }
}

