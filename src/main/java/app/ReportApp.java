package app;

import app.dao.EmployeeDaoImpl;
import app.model.Department;
import app.model.District;
import app.model.Employee;
import app.service.EmployeeService;
import app.service.EmployeeServiceImpl;

public class ReportApp {
    public static void main(String[] args) {
        EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDaoImpl());
        //employeeService.createTables("COMPANY_B");

        /*Employee employee = new Employee("Petro", 29,
                new Department("Office", new District("Europe")));

        employeeService.saveEmployee(employee);*/

        employeeService.addTestData();
    }
}