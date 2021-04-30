package idol.tofu.server.persistence;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JoinDAO extends BasicDAOImpl {
    public JoinDAO() {
    }

    /*
     * 관람등급 확인 시나리오
     * 스케줄 고유번호를 받아 영화의 관람등급을 반환
     *
     * */
    public int getMovieRating(String schduleUid) {
        String sql = "SELECT MOV_RATING FROM \"movies\" WHERE MOV_UID =" +
                "(SELECT MOV_UID FROM \"schedule\" WHERE SCH_UID = ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, schduleUid);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                result = resultSet.getInt("MOV_RATING");
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return result;
    }

    /*
     * 기존 예매확인 시나리오
     * 특정 유저의 결제내역을 모두 보여주는 기능
     * */
    public ArrayList<BillingDTO> showMyBilling(String mbUid) {
        String sql = "SELECT * FROM \"billing\" WHERE bill_id IN " +
                "(SELECT BILL_ID FROM \"reservations\" WHERE mb_id = ?)";
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<BillingDTO> list = new ArrayList<>();

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, mbUid);
            result = statement.executeQuery();

            while (result.next()) {
                BillingDTO billing = new BillingDTO();
                billing.setUid(result.getInt("BILL_ID"));
                billing.setAmount(result.getInt("BILL_AMOUNT"));
                billing.setBank(result.getInt("BILL_BANK"));
                billing.setAccountNumber(result.getString("BILL_ACCNO"));
                billing.setBillingStatus(result.getInt("BILL_STATUS"));
                billing.setBillingDate(result.getTimestamp("BILL_DATE"));
                list.add(billing);
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
     * 좌석 선택 시나리오
     * 좌석 상태 중 상영관의 좌석 갯수에 대한 정보를 select하여 DTO객체로 반환
     * */
    public ScreenDTO getScreenInfo(String schUid) {
        String sql = "SELECT * FROM \"screens\" WHERE SCR_NO =" +
                "(SELECT SCR_NO FROM \"schedule\" WHERE SCH_UID = ?)" +
                "AND THTR_UID = (SELECT THTR_UID FROM \"schedule\" WHERE SCH_UID = ?)";
        PreparedStatement statement = null;
        ScreenDTO screen = null;
        ResultSet result = null;
        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, schUid);
            statement.setString(2, schUid);
            result = statement.executeQuery();
            while (result.next()) {
                screen = new ScreenDTO();
                screen.setTheaterId(result.getInt("THTR_UID"));
                screen.setScreenNumber(result.getInt("SCR_NO"));
                screen.setSeatPerRow(result.getInt("SCR_SEATNUM"));
                screen.setRowNumber(result.getInt("SCR_ROWNUM"));
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
        return screen;
    }

    /*
     * 좌석 선택 시나리오
     * 좌석 상태 전송하는 단계
     * 좌석이 결제 대기(선점) 가능한 상태인지 판단하는 단계
     * 스케쥴의 고유번호를 받아서 해당 스케줄에 선점된 좌석들의 list를 반환
     *
     * 좌석번호 비교는 Service계층에서 실시
     *
     * */
    public ArrayList<SeatDTO> isOccupied(String SCH_UID) {
        String sql = "SELECT * FROM \"seats\" WHERE RESV_UID IN" +
                "(SELECT RESV_UID FROM \"reservations\" WHERE SCH_UID = ?)";
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<SeatDTO> list = new ArrayList<>();

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, SCH_UID);
            result = statement.executeQuery();

            while (result.next()) {
                SeatDTO seat = new SeatDTO();
                seat.setReservationId(result.getInt("RESV_UID"));
                seat.setUid(result.getInt("SEATS_UID"));
                seat.setSeatNumber(result.getInt("SEATS_NO"));
                list.add(seat);
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
     * 좌석 선택 시나리오
     * 좌석 상태 전송하는 단계
     * 스케쥴의 고유번호를 받아서 해당 스케줄에 선점된 좌석들의 갯수를 반환
     * */
    public int countOccupied(String SCH_UID) {
        String sql = "SELECT * FROM \"seats\" WHERE RESV_UID IN" +
                "(SELECT RESV_UID FROM \"reservations\" WHERE SCH_UID = ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, SCH_UID);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result++;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return result;
    }


    /*
     * DB의 저장 프로시저를 실행하는 메소드
     * 원하는 영화관과 원하는 달(월)을 매개변수로 입력한다
     * 해당 영화관이 입력한 달(월) 한달 간 입금받은 금액의 총합을 반환한다
     * */
    public int callPLSQL(int month, int theaterNo) {
        String sql = "{call calculate(?,?,?)}";
        CallableStatement cstmt = null;
        int result = 0;

        try {
            getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, month);
            cstmt.setInt(2, theaterNo);
            cstmt.registerOutParameter(3, OracleTypes.NUMBER);
            cstmt.execute();
            result = cstmt.getInt(3);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (cstmt != null) cstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return result;
    }
}
