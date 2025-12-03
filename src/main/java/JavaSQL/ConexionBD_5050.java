package JavaSQL;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConexionBD_5050 {
    public static Connection getConexion() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/main/resources/db.properties"));

            return DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
