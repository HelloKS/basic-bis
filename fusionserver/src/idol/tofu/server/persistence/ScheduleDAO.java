package idol.tofu.server.persistence;

import oracle.jdbc.*;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ScheduleDAO extends BasicDAOImpl {

    public ScheduleDAO() {
    }

    /*
     * 관리자 기능 - 영화스케줄 관리 UI 출력용
     * C가 영화스케줄 관리 버튼을 클릭 시 작동
     * 영화 스케줄에 있는 모든 row를 반환
     * */
    public ArrayList<ScheduleDTO> searchPlaying() {
        ArrayList<ScheduleDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM \"schedule\"";
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
                ScheduleDTO schedule = new ScheduleDTO();
                schedule.setUid(result.getInt("SCH_UID"));
                schedule.setScreenNumber(result.getInt("SCR_NO"));
                schedule.setTheaterId(result.getInt("THTR_UID"));
                schedule.setMovieId(result.getInt("MOV_UID"));
                schedule.setStartTime(result.getTimestamp("SCH_STARTTIME"));
                list.add(schedule);
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
     * 예매 단계
     * 영화관과 영화를 선택한 뒤의 스케줄 선택 단게에서 필요
     * C가 날짜를 선택 시, 통신
     * 영화관 고유번호, 영화 고유번호, 날짜를 받아서
     * 당일 해당 영화관 영화의 스케줄을  반환
     * */
    public ArrayList<ScheduleDTO> searchScheduleInTheater(String thtr_uid, String mov_uid, String date) {
        ArrayList<ScheduleDTO> list = new ArrayList<ScheduleDTO>();

        String sql = "SELECT * FROM \"schedule\" WHERE MOV_UID = ? AND THTR_UID = ? AND SCH_STARTTIME >= TO_DATE(?, 'YYYY-MM-DD') AND SCH_STARTTIME < TO_DATE(?, 'YYYY-MM-DD') + 1";

        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, thtr_uid);
            statement.setString(2, mov_uid);
            statement.setString(3, date);
            statement.setString(4, date);

            result = statement.executeQuery();

            while (result.next()) {
                ScheduleDTO resv = new ScheduleDTO();
                resv.setUid(result.getInt("SCH_UID"));
                resv.setMovieId(result.getInt("MOV_UID"));
                resv.setScreenNumber(result.getInt("SCR_NO"));
                resv.setTheaterId(result.getInt("THTR_UID"));
                resv.setStartTime(result.getTimestamp("SCH_STARTTIME"));
                list.add(resv);
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
     * 관리자 기능 - 영화 스케줄 추가
     * C(관리자)의 영화스케줄 관리 UI에서 추가버튼 클릭시 사용되는 기능
     * C로부터 영화스케줄 생성에 필요한 모든 instance를 전달받음
     * sql문 성공 시 반드시 1을 반환, 실패시 0 반환
     * */
    public int insert(String movUid, String scrNo, String theaterUid, String startTime) {
        String sql = "INSERT INTO \"schedule\" (SCH_UID, MOV_UID, SCR_NO, THTR_UID, SCH_STARTTIME)" +
                "values (SCH_UID_PK.NEXTVAL,?,?,?,TO_DATE(?,'YYYYMMDD'))";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, movUid);
            statement.setString(2, scrNo);
            statement.setString(3, theaterUid);
            statement.setString(4, startTime);

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
     * 관리자 기능 - 스케줄 수정
     * C(관리자)가 스케줄 관리 UI에서 특정 스케줄을 선택하여 수정을 진행
     * 오로지 스케줄 uid와 새 상영일정(시간)만을 전달
     * 다른 instance는 수정 불가
     * */
    public int update(String startTime, String scheduleUid) {
        String sql = "UPDATE \"schedule\" SET SCH_STARTTIME = TO_DATE(?,'YYYYMMDD') WHERE SCH_UID = ?";
        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, startTime);
            statement.setString(2, scheduleUid);

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
     * 관리자 기능 - 스케줄 삭제
     * C(관리자)가 스케줄 관리 UI에서 특정 스케줄을 선택하여 삭제를 진행
     * 오로지 스케줄 uid만을 전달 / uid가 PK이므로 특정 가능
     * sql 성공시 1 반환, 실패시 0 반환
     * */
    public int delete(int uid) {
        String sql = "DELETE FROM \"schedule\" WHERE SCH_UID = ?";
        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setInt(1, uid);
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
