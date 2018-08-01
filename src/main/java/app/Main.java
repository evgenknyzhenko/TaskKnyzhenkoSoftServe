package app;


import app.dao.ModelDaoImpl;
import app.model.Department;
import app.model.District;
import app.model.Employee;
import app.service.ModelService;
import app.service.ModelServiceImpl;


public class Main {
    public static void main(String[] args) {

        ModelService modelService = new ModelServiceImpl(new ModelDaoImpl());
        //modelService.createTables("COMPANY_B");

        Employee employee = new Employee("Petro", 29,
                new Department("Office", new District("Europe")));

        modelService.saveEmployee(employee);


    }
}
