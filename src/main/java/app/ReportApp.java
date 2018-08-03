package app;


import app.dao.EmployeeDaoImpl;
import app.service.EmployeeService;
import app.service.EmployeeServiceImpl;

import java.util.Map;

public class ReportApp {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDaoImpl());
        employeeService.createTables("COMPANY_A");
        employeeService.createTables("COMPANY_B");

        employeeService.addTestData("COMPANY_A");
        employeeService.addTestData("COMPANY_B");

        Map<String, Long> result = employeeService.countEmployeesOfDepartment(0,100,"South America");
        System.out.println("Department | Employees");
        result.forEach((k, v) -> System.out.println(k + " | " + v));
    }
}