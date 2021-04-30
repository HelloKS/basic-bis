package idol.tofu.server.persistence;

import oracle.jdbc.*;

import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;

public class BillingDAO extends BasicDAOImpl {
    public BillingDAO() {
    }

    /*
        결제 수락 - 결제 수락 or 취소 시나리오
        C가 결제를 수락한다는 응답을 함
        수락한 결제에 대한 정보를 매개변수로 받아 DB에 추가
        insert 함수는 1회 호출에 1개의 row만 추가하므로 sql문 성공 시 반드시 1을 반환
        sql문 실패시 0 반환
     */
    public int insert(String amount, String bank, String accno, String status, String date) {
        String sql = "INSERT INTO \"billing\" (BILL_ID, BILL_AMOUNT, BILL_BANK, BILL_ACCNO, BILL_STATUS, BILL_DATE) values (BILL_ID_PK.NEXTVAL,?,?,?,?,?)";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, amount);
            statement.setString(2, bank);
            statement.setString(3, accno);
            statement.setString(4, status);
            statement.setString(5, date);

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
        결제 취소 - 결제 수락 or 결제 취소 시나리오
        C가 결제를 취소 한다는 응답을하면 함수 호출
        C가 해당 결제의 uid를 전송 하면서 결제 취소를 요청
        S는 C가 전송한 uid를 받아서 BILL_STATUS 를 2(결제취소) 로 update
     */
    public int cancelBilling(String bill_uid) {
        String sql = "UPDATE \"billing\" SET BILL_STATUS = 2, BILL_DATE = SYSDATE WHERE BILL_ID = ?";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, bill_uid);
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
    *
    * billing개체를 새롭게 만든 후
    * 예매 개체를 만들기 위해 새롭게 insert한 billing개체의 고유번호를 얻어오는 메소드
    * */
    public int getCurrentBillId()
    {
        String sql = "SELECT BILL_ID_PK.NEXTVAL FROM DUAL";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int result = 0;
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                result = resultSet.getInt("NEXTVAL");
            }
        }catch(SQLException se){
            se.printStackTrace();
        }finally {
            try{
                if(statement != null) statement.close();
                if(conn != null) conn.close();
            }catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return  result - 1;
    }
}
