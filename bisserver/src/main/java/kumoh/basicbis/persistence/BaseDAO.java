package kumoh.basicbis.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface BaseDAO {

    public void getConnection();

}