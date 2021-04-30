package idol.tofu.server.persistence;

import oracle.jdbc.*;

import java.sql.*;
import java.util.ArrayList;

public class ScreenDAO extends BasicDAOImpl {

    public ScreenDAO() {
    }

    /*
     * 관리자 기능 - 상영관 관리 UI 출력
     * 상영관 관리 UI에서 전체 상영관을 출력하여 보여줌
     * 상영관이 하나도 존재하지 않다면 빈 리스트를 반환
     * */
    public ArrayList<ScreenDTO> getAllScreen() {
        ArrayList<ScreenDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM \"screens\"";
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
                ScreenDTO screen = new ScreenDTO();
                screen.setTheaterId(result.getInt("THTR_UID"));
                screen.setScreenNumber(result.getInt("SCR_NO"));
                screen.setRowNumber(result.getInt("SCR_ROWNUM"));
                screen.setSeatPerRow(result.getInt("SCR_SEATNUM"));
                list.add(screen);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if( result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return list;
    }

    /*
     * 특정 영화관에 대한 상영관 리스트
     * 영화관 고유번호를 받아서 DB내에서 검색 후 반환
     * */
    public ArrayList<ScreenDTO> searchScreenInTheater(String theaterUid) {
        ArrayList<ScreenDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM \"screens\" WHERE THTR_UID = ?";
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, theaterUid);
            result = statement.executeQuery();
            while (result.next()) {
                ScreenDTO screen = new ScreenDTO();
                screen.setTheaterId(result.getInt("THTR_UID"));
                screen.setScreenNumber(result.getInt("SCR_NO"));
                screen.setRowNumber(result.getInt("SCR_ROWNUM"));
                screen.setSeatPerRow(result.getInt("SCR_SEATNUM"));
                list.add(screen);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if(result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return list;
    }

    /*
     * 관리자 기능 - 상영관 추가 시나리오
     * C(관리자)가 관리UI에서 새로운 상영관을 추가할 때 작동
     * C(관리자)가 직접 모든 instance를 입력해야 한다
     * 이 함수는 1회 호출에 1개의 row만 추가하므로 sql문 성공 시 반드시 1을 반환
     * sql문 실패시 0 반환
     * */
    public int insert(String theaterUid, String screenNo, String rowNum, String seatNum) {
        String sql = "INSERT INTO \"screens\" (THTR_UID, SCR_NO, SCR_ROWNUM, SCR_SEATNUM) values (?,?,?,?)";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, theaterUid);
            statement.setString(2, screenNo);
            statement.setString(3, rowNum);
            statement.setString(4, seatNum);

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
     * 관리자 기능 - 상영관 수정
     * C(관리자)가 상영관 관리UI의 전체 상영관 리스트에서 특정 상영관에 대한 수정을 진행
     * C(관리자)는 오로지 해당 상영관의 좌석데이터만 수정 가능(상영관no+영화관uid가 primary key)
     * 이 함수는 1회 호출에 1개의 row만 수정하므로 sql문 성공 시 반드시 1을 반환
     * sql문 실패시 0 반환
     */
    public int update(String theaterUid, String screenNo, String rowNum, String seatNum) {
        String sql = "UPDATE \"screens\" SET SCR_ROWNUM = ?, SCR_SEATNUM = ? WHERE SCR_NO = ?, THTR_UID = ?";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, rowNum);
            statement.setString(2, seatNum);
            statement.setString(3, screenNo);
            statement.setString(4, theaterUid);

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
     * 관리자 기능 - 상영관 삭제
     * C(관리자)가 상영관 관리UI의 전체 상영관 리스트에서 특정 상영관에 대한 삭제를 진행
     * C(관리자)는 해당 상영관의 PK(상영관번호+영화관 고유번호)를 전송
     * 이 함수는 1회 호출에 1개의 row만 삭제하므로 sql문 성공 시 반드시 1을 반환
     * sql문 실패시 0 반환
     */
    public int delete(String theaterUid, String screenNo) {
        String sql = "DELETE FROM \"screens\" WHERE SCR_NO = ? AND THTR_UID = ?";
        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, screenNo);
            statement.setString(2, theaterUid);

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
