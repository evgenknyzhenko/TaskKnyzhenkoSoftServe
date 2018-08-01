package app.service;

import app.dao.EmployeeDao;
import app.model.Department;
import app.model.District;
import app.model.Employee;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void addTestData() {
        int count = 1;
        List<String> departmentNames = Arrays
                .asList("IT", "Logistics", "Management", "Marketing", "Customer service", "Staff department");
        List<String> districtNames = Arrays
                .asList("Europe", "Asia", "North America", "South America", "Australia");
        Employee employee = new Employee(null, 0,
                new Department(null, new District(null)));

        for (int i = 0; i < 100000; i++) {
            employee.setName("Empl_" + count);
            employee.setAge((int) (18 + Math.random() * 80));
            employee.getDepartment().setDepartmentName(departmentNames.get((int) (Math.random() * 6)));
            employee.getDepartment().getDistrict().setDistrictName(districtNames.get((int) (Math.random() * 5)));
            count++;
            employeeDao.saveEmployee(employee);
        }
    }

    public void countEmployeesOfDepartment(int minAge, int maxEge, String districtName) {
        Map<String, Long> result = employeeDao.countEmployeesOfDepartment(minAge, maxEge, districtName);

        System.out.println("Department | Employees");
        result.forEach((k, v) -> System.out.println(k + " | " + v));
    }


}




















