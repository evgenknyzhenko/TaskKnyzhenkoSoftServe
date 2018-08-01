package app.dao;

import app.model.Department;
import app.model.Employee;

import java.util.Map;

public interface EmployeeDao {
    void createTables(String companyName);

    void saveEmployee(Employee employee);

    void saveDepartment(Department department);

    Map<String,Long> countEmployeesOfDepartment(int minAge, int maxEge, String districtName);
}
