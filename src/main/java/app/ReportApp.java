package app;

import app.dao.ModelDaoImpl;
import app.model.Department;
import app.model.District;
import app.model.Employee;
import app.service.EmployeeService;
import app.service.EmployeeServiceImpl;

public class ReportApp {
    public static void main(String[] args) {
        EmployeeService modelService = new EmployeeServiceImpl(new ModelDaoImpl());
        //modelService.createTables("COMPANY_B");

        Employee employee = new Employee("Petro", 29,
                new Department("Office", new District("Europe")));

        modelService.saveEmployee(employee);
    }
}