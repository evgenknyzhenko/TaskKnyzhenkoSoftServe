package app.service;

import app.model.Department;
import app.model.Employee;

public interface EmployeeService {
    void createTables(String companyName);

    void saveEmployee(Employee employee);

    void saveDepartment(Department department);
}
