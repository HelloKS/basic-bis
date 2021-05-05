package kumoh.basicbis.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseDAOImpl implements BaseDAO {
    static final String URL = "jdbc:mariadb://tae.0xffff.host:7389/gumibis?user=root&password=helloks";
    Connection conn = null;

    //Class forName 필요없음

    @Override
    public void getConnection() {
        try {
            conn = DriverManager.getConnection(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
