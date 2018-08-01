package app.dao;

import app.model.Department;
import app.model.Employee;

public interface EmployeeDao {
    void createTables(String companyName);

    void saveEmployee(Employee employee);

    void saveDepartment(Department department);
}
