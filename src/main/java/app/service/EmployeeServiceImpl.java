package app.service;

import app.dao.EmployeeDao;
import app.model.Department;
import app.model.Employee;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void createTables(String companyName) {
        employeeDao.createTables(companyName);
    }

    public void saveEmployee(Employee employee) {
        employeeDao.saveEmployee(employee);
    }

    public void saveDepartment(Department department) {
        employeeDao.saveDepartment(department);
    }


}
