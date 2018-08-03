package app.dao;

import app.model.Department;
import app.model.District;
import app.model.Employee;

import java.util.Map;

public interface EmployeeDao {
    String createTables(String companyName);

    Employee saveEmployee(String companyName, Employee employee);

    Department saveDepartment(String companyName, Department department);

    District saveDistrict(String companyName, District district);

    Map<String,Long> countEmployeesOfDepartment(int minAge, int maxEge, String districtName);
}
