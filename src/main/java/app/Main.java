package app;


import app.dao.ModelDaoImpl;
import app.service.ModelService;
import app.service.ModelServiceImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
       /* Connection connection = Factory.getConnection();
        String query = "SELECT DEPARTMENT FROM COMPANY_A.EMPLOYEES";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.first();
        String department = resultSet.getString(1);
        System.out.println(department);*/

        ModelService modelService = new ModelServiceImpl(new ModelDaoImpl());
        modelService.createTables("COMPANY_B");

    }
}
