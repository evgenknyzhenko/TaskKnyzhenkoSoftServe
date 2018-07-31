package app.dao;

import app.Factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelDaoImpl implements ModelDao {
    private Connection connection;

    public void createTables(String companyName) {
        connection = Factory.getConnection();
        String createTablesQuery =
                "CREATE SCHEMA " + companyName + ";\n" +

                "CREATE TABLE " + companyName + ".DISTRICTS (\n" +
                "ID BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "DISTRICT VARCHAR(100) NOT NULL\n" +
                ");\n" +

                "CREATE TABLE " + companyName + ".DEPARTMENTS (\n" +
                "ID BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "DEPARTMENT VARCHAR(100) NOT NULL,\n" +
                "FK_DISTRICT_ID BIGINT NOT NULL ,\n" +
                "CONSTRAINT FK_DEPARTMENTS_TO_DISTRICTS FOREIGN KEY (FK_DISTRICT_ID)\n" +
                "REFERENCES DISTRICTS (ID));\n" +

                "CREATE TABLE " + companyName + ".EMPLOYEES (\n" +
                "ID BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "NAME VARCHAR(100) NOT NULL,\n" +
                "AGE INT(3) NOT NULL,\n" +
                "FK_DEPARTMENT_ID BIGINT NOT NULL,\n" +
                "CONSTRAINT FK_EMPLOYEES_TO_DEPARTMENTS FOREIGN KEY (FK_DEPARTMENT_ID)\n" +
                "REFERENCES DEPARTMENTS (ID));\n"
                ;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTablesQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
