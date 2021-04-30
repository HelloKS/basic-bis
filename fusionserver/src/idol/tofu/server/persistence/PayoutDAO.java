package idol.tofu.server.persistence;

import oracle.jdbc.*;

import java.sql.*;
import java.util.ArrayList;

public class PayoutDAO extends BasicDAOImpl {

    public PayoutDAO() {
    }


    public int insert(String date, String theaterUid, String amount) {
        String sql = "INSERT INTO \"payout\" (PAYOUT_DATE, THTR_UID, PAYOUT_AMOUNT) values (TO_DATE(?,'YYYYMMDD'),?,?)";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, date);
            statement.setString(2, theaterUid);
            statement.setString(3, amount);

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
