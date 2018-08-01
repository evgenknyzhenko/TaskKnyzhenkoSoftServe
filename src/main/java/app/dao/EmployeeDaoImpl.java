package app.dao;

import app.ConnectionFactory;
import app.model.Department;
import app.model.District;
import app.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection = ConnectionFactory.getConnection();

    public void createTables(String companyName) {
        String createTablesQuery =
                "CREATE SCHEMA " + companyName + ";\n" +

                        "CREATE TABLE " + companyName + ".DISTRICTS (\n" +
                        "ID BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                        "DISTRICT VARCHAR(100) NOT NULL,\n" +
                        "UNIQUE(DISTRICT));\n" +

                        "CREATE TABLE " + companyName + ".DEPARTMENTS (\n" +
                        "ID BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                        "DEPARTMENT VARCHAR(100) NOT NULL,\n" +
                        "FK_DISTRICT_ID BIGINT NOT NULL ,\n" +
                        "UNIQUE(DEPARTMENT),\n" +
                        "CONSTRAINT FK_DEPARTMENTS_TO_DISTRICTS FOREIGN KEY (FK_DISTRICT_ID)\n" +
                        "REFERENCES DISTRICTS (ID));\n" +

                        "CREATE TABLE " + companyName + ".EMPLOYEES (\n" +
                        "ID BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                        "NAME VARCHAR(100) NOT NULL,\n" +
                        "AGE INT(3) NOT NULL,\n" +
                        "UNIQUE(NAME),\n" +
                        "FK_DEPARTMENT_ID BIGINT NOT NULL,\n" +
                        "CONSTRAINT FK_EMPLOYEES_TO_DEPARTMENTS FOREIGN KEY (FK_DEPARTMENT_ID)\n" +
                        "REFERENCES DEPARTMENTS (ID));\n";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTablesQuery);
        } catch (SQLException e) {
            System.out.println("Exception, tables isn't createt");
        }
    }

    public void saveEmployee(Employee employee) {
        String query = "INSERT INTO COMPANY_B.EMPLOYEES (NAME, AGE, FK_DEPARTMENT_ID) VALUES(?,?,?);";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employee.getName());
            statement.setInt(2, employee.getAge());
            statement.setLong(3, getIdByDepartment(employee.getDepartment()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception, employee isn't saved");
        }
    }

    public void saveDepartment(Department department) {
        String query = "INSERT INTO COMPANY_B.DEPARTMENTS (DEPARTMENT, FK_DISTRICT_ID) VALUES(?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, department.getDepartmentName());
            statement.setLong(2, getIdByDistrict(department.getDistrict()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception, department isn't saved");
        }
    }



    public Map<String, Long> countEmployeesOfDepartment(int minAge, int maxEge, String districtName) {
        Map<String, Long> result = new HashMap<String, Long>();
        String query = "SELECT DEPARTMENTS.DEPARTMENT, COUNT (EMPLOYEES.NAME)\n" +
                "FROM COMPANY_B.EMPLOYEES\n" +
                "INNER JOIN COMPANY_B.DEPARTMENTS ON EMPLOYEES.FK_DEPARTMENT_ID=DEPARTMENTS.ID\n" +
                "INNER JOIN COMPANY_B.DISTRICTS ON DEPARTMENTS.FK_DISTRICT_ID=DISTRICTS.ID\n" +
                "WHERE DISTRICTS.DISTRICT=(?) AND EMPLOYEES.AGE>(?) AND EMPLOYEES.AGE<(?)\n" +
                "GROUP BY DEPARTMENTS.DEPARTMENT";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, districtName);
            statement.setInt(2,minAge);
            statement.setInt(3,maxEge);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.put(resultSet.getString(1), resultSet.getLong(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Exception, query didn't give result");
        }
        return result;
    }



    private long getIdByDepartment(Department department) {
        String query = "SELECT ID FROM COMPANY_B.DEPARTMENTS WHERE DEPARTMENT=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, department.getDepartmentName());
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            saveDepartment(department);
            return getIdByDepartment(department);
        }
    }

    private long getIdByDistrict(District district) {

        String query = "SELECT ID FROM COMPANY_B.DISTRICTS WHERE DISTRICT=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, district.getDistrictName());
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return resultSet.getLong(1);
        } catch (Exception e) {
            saveDistrict(district);
            return getIdByDistrict(district);
        }
    }

    private void saveDistrict(District district) {
        String query = "INSERT INTO COMPANY_B.DISTRICTS (DISTRICT) VALUES(?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, district.getDistrictName());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception, district isn't saved");
        }
    }
}







