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

    public String createTables(String companyName) {
        String createTablesQuery =
                "CREATE SCHEMA " + companyName + ";\n" +

                        "CREATE TABLE " + companyName + ".DISTRICTS (" +
                        "ID BIGINT PRIMARY KEY AUTO_INCREMENT," +
                        "DISTRICT VARCHAR(100) NOT NULL," +
                        "UNIQUE(DISTRICT));" +

                        "CREATE TABLE " + companyName + ".DEPARTMENTS (" +
                        "ID BIGINT PRIMARY KEY AUTO_INCREMENT," +
                        "DEPARTMENT VARCHAR(100) NOT NULL," +
                        "FK_DISTRICT_ID BIGINT NOT NULL ," +
                        "UNIQUE(DEPARTMENT)," +
                        "CONSTRAINT FK_DEPARTMENTS_TO_DISTRICTS FOREIGN KEY (FK_DISTRICT_ID)" +
                        "REFERENCES DISTRICTS (ID));" +

                        "CREATE TABLE " + companyName + ".EMPLOYEES (" +
                        "ID BIGINT PRIMARY KEY AUTO_INCREMENT," +
                        "NAME VARCHAR(100) NOT NULL," +
                        "AGE INT(3) NOT NULL," +
                        "UNIQUE(NAME)," +
                        "FK_DEPARTMENT_ID BIGINT NOT NULL," +
                        "CONSTRAINT FK_EMPLOYEES_TO_DEPARTMENTS FOREIGN KEY (FK_DEPARTMENT_ID)" +
                        "REFERENCES DEPARTMENTS (ID))";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTablesQuery);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create tables", e);
        }
        return "Tables are created";
    }

    public Employee saveEmployee(String companyName, Employee employee) {
        String query = "INSERT INTO " + companyName + ".EMPLOYEES (NAME, AGE, FK_DEPARTMENT_ID) VALUES(?,?,?);";
        Long departmentId = getIdByDepartment(companyName,employee.getDepartment());

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employee.getName());
            statement.setInt(2, employee.getAge());
            statement.setLong(3, departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save employee", e);
        }
        return employee;
    }

    public Department saveDepartment(String companyName, Department department) {
        Long districtId = getIdByDistrict(companyName,department.getDistrict());
        String query = "INSERT INTO " + companyName + ".DEPARTMENTS (DEPARTMENT, FK_DISTRICT_ID) VALUES(?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, department.getDepartmentName());
            statement.setLong(2, districtId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save department", e);
        }
        return department;
    }

    public District saveDistrict(String companyName, District district) {
        String query = "INSERT INTO " + companyName + ".DISTRICTS (DISTRICT) VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, district.getDistrictName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save district", e);
        }
        return district;
    }

    public Map<String, Long> countEmployeesOfDepartment(int minAge, int maxEge, String districtName) {
        Map<String, Long> resultBothComanies = new HashMap<>();

        String queryCompany_A = "SELECT DEPARTMENTS.DEPARTMENT, COUNT (EMPLOYEES.NAME) " +
                "FROM COMPANY_A.EMPLOYEES " +
                "INNER JOIN COMPANY_A.DEPARTMENTS ON EMPLOYEES.FK_DEPARTMENT_ID=DEPARTMENTS.ID " +
                "INNER JOIN COMPANY_A.DISTRICTS ON DEPARTMENTS.FK_DISTRICT_ID=DISTRICTS.ID " +
                "WHERE DISTRICTS.DISTRICT=(?) AND EMPLOYEES.AGE>(?) AND EMPLOYEES.AGE<(?) " +
                "GROUP BY DEPARTMENTS.DEPARTMENT";
        Map<String, Long> resultCompany_A = new HashMap<>(addResultToMap(queryCompany_A, minAge, maxEge, districtName));

        String queryCompany_B = "SELECT DEPARTMENTS.DEPARTMENT, COUNT (EMPLOYEES.NAME)" +
                "FROM COMPANY_B.EMPLOYEES " +
                "INNER JOIN COMPANY_B.DEPARTMENTS ON EMPLOYEES.FK_DEPARTMENT_ID=DEPARTMENTS.ID " +
                "INNER JOIN COMPANY_B.DISTRICTS ON DEPARTMENTS.FK_DISTRICT_ID=DISTRICTS.ID " +
                "WHERE DISTRICTS.DISTRICT=(?) AND EMPLOYEES.AGE>(?) AND EMPLOYEES.AGE<(?) " +
                "GROUP BY DEPARTMENTS.DEPARTMENT";
        Map<String, Long> resultCompany_B = new HashMap<>(addResultToMap(queryCompany_B, minAge, maxEge, districtName));

        resultBothComanies.putAll(resultCompany_A);
        resultBothComanies.putAll(resultCompany_B);
        resultCompany_A.forEach((k1,v1) -> {
            resultCompany_B.forEach((k2,v2)->{
                if (k1.equals(k2)) {
                    resultBothComanies.put(k1, v1+v2);
                }
            });
        });
        return resultBothComanies;
    }

    private Map<String, Long> addResultToMap(String query, int minAge, int maxEge, String districtName) {
        Map<String, Long> resultOneCompany = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, districtName);
            statement.setInt(2, minAge);
            statement.setInt(3, maxEge);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                resultOneCompany.put(resultSet.getString(1), resultSet.getLong(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to query result", e);
        }
        return resultOneCompany;
    }

    private long getIdByDepartment(String companyName, Department department) {
        String query = "SELECT ID FROM " + companyName + ".DEPARTMENTS WHERE DEPARTMENT=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, department.getDepartmentName());
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            saveDepartment(companyName, department);
            return getIdByDepartment(companyName, department);
        }
    }

    private long getIdByDistrict(String companyName, District district) {

        String query = "SELECT ID FROM " + companyName + ".DISTRICTS WHERE DISTRICT=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, district.getDistrictName());
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return resultSet.getLong(1);
        } catch (Exception e) {
            saveDistrict(companyName, district);
            return getIdByDistrict(companyName, district);
        }
    }
}
