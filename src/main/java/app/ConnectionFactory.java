package app;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
    private static Connection connection;

    static {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/db.properties");
            Properties properties = new Properties();
            properties.load(fis);

            Class.forName(properties.getProperty("driverClass"));
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("userName"),
                    properties.getProperty("password"));
        } catch (Exception e) {
            throw new RuntimeException("Failed  to create data base connection", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
