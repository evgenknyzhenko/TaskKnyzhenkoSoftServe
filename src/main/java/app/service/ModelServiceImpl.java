package app.service;

import app.dao.ModelDao;
import app.model.Department;
import app.model.Employee;

public class ModelServiceImpl implements ModelService {

    private ModelDao modelDao;

    public ModelServiceImpl(ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    public void createTables(String companyName) {
        modelDao.createTables(companyName);
    }

    public void saveEmployee(Employee employee) {
        modelDao.saveEmployee(employee);
    }

    public void saveDepartment(Department department) {
        modelDao.saveDepartment(department);
    }


}
