package idol.tofu.server.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BasicDAOImpl implements BasicDAO{
    static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String URL = "jdbc:oracle:thin:@tae.don-t.work:59599:xe";
    static final String USER = "FUSIONUSER";
    static final String PASSWORD = "enjameebye";
    Connection conn = null;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver 로딩 실패!");
            e.printStackTrace();
        }
    }

    @Override
    public void getConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
