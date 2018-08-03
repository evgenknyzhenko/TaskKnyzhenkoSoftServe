package app.service;

import app.model.Department;
import app.model.District;
import app.model.Employee;

import java.util.Map;

public interface EmployeeService {
    String createTables(String companyName);

    Employee saveEmployee(String companyName, Employee employee);

    Department saveDepartment(String companyName, Department department);

    District saveDistrict(String companyName, District district);

    void addTestData(String companyName);

    Map<String, Long> countEmployeesOfDepartment(int minAge, int maxEge, String districtName);
}
