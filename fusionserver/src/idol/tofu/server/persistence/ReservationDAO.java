package idol.tofu.server.persistence;

import oracle.jdbc.*;


import java.sql.*;
import java.util.ArrayList;

public class ReservationDAO extends BasicDAOImpl {

    public ReservationDAO() {
    }

    /*
        예매확인 기능 - 기존 예매 확인 시나리오
        C가 로그인 상태라는 전제조건 하에 작동
        S는 C(사용자)의 id를 매개변수로 받아 예매 테이블에서 해당 id로 예매된 내역을 검색
        select로 검색된 결과는 모두 resultSet에 삽입 후 DTO로 해당 개체의 정보를 받아 arraylist에 추가
    */
    public ArrayList<ReservationDTO> searchMyReservation(String id) {
        ArrayList<ReservationDTO> list = new ArrayList<ReservationDTO>();
        String sql = "SELECT * FROM \"reservations\" WHERE MB_ID = ?";

        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, id);

            result = statement.executeQuery();

            while (result.next()) {
                ReservationDTO resv = new ReservationDTO();
                resv.setUid(result.getInt("RESV_UID"));
                resv.setUserId(result.getString("MB_ID"));
                resv.setBillingId(result.getInt("BILL_ID"));
                resv.setScheduleId(result.getInt("SCH_UID"));
                list.add(resv);
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
    결제 기능 마지막 단계
    C(사용자)가 결제 수락을 선택 시 사용
    S는 예매테이블에 필요한 정보들을 매개변수로 받아서 DB에 추가
    이 함수는 1회 호출에 1개의 row만 추가하므로 sql문 성공 시 반드시 1을 반환
    sql문 실패시 0 반환
    */
    public int insert(String mbId, String billId, String schUid) {
        String sql = "INSERT INTO \"reservations\" (RESV_UID, MB_ID, BILL_ID, SCH_UID) values (RESV_UID_PK.NEXTVAL,?,?,?)";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, mbId);
            statement.setString(2, billId);
            statement.setString(3, schUid);

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
     * C가 예매 목록에서 특정 예매 취소 버튼 클릭시 작동
     * C가 S에게 해당 예매의 고유번호를 전송하며 예매 취소를 요청
     * S는 고유번호를 reservations에서 delete를 수행
     * 이 함수는 1회 호출에 1개의 row만 삭제하므로 sql문 성공 시 반드시 1을 반환
     * sql문 실패시 0 반환
     */
    public int cancelMyReservation(String uid) {
        String sql = "DELETE FROM \"reservations\" WHERE resv_uid = ?";
        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, uid);

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
    * 예매 단계
    * billing개체 생성 - reservation 개체 생성 후
    * seat 개체를 생성하기 위해 reservation의 고유번호를 가져오는 메소드
    * */
    public String getReservationUid(String bill_id)
    {
        String sql = "SELECT * FROM \"reservations\" WHERE BILL_ID = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String resv_uid = "";
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, bill_id);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                resv_uid = resultSet.getString("RESV_UID");
            }
        }catch (SQLException se){
            se.printStackTrace();
        }finally{
            try{
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return resv_uid;
    }
}
