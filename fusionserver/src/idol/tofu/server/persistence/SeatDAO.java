package idol.tofu.server.persistence;

import oracle.jdbc.*;

import java.sql.*;
import java.util.ArrayList;

public class SeatDAO extends BasicDAOImpl {

    public SeatDAO() {
    }

    /*
     * 예매 기능 - 좌석 선택 단계
     * C가 선택한 좌석의 정보를 받아 좌석Table에 저장(즉, 결제/선점된 좌석으로 만들기)
     * sql 성공시 1, 실패시 0을 반환
     * */
    public int insert(String rsvUid, String no) {
        String sql = "INSERT INTO \"seats\" (SEATS_UID, RESV_UID, SEATS_NO) values (SEATS_UID_PK.NEXTVAL,?,?)";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, rsvUid);
            statement.setString(2, no);

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
     * 예매 확인 - 기존 예매 취소 기능
     * 예매 기능 - 결제 단계 - 결제 취소시
     * 선점/결제된 좌석을 좌석TABLE에서 제거함으로써 반환
     * 예매 고유번호를 매개로 받아 좌석들을 제거
     * */
    public int delete(String resvUid) {
        String sql = "DELETE FROM \"seats\" WHERE RESV_UID = ?";
        int result = 0;
        PreparedStatement statement = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, resvUid);

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
     * 결제 시나리오
     * C에게 결제금액을 보낼때 필요
     * C의 예매번호에 대해 선점된 좌석들의 갯수를 반환
     * */
    public int getSeats(String resvUid) {
        String sql = "SELECT * FROM \"seats\" WHERE RESV_UID = ?";
        int result = 0;

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, resvUid);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result++;
            }
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
