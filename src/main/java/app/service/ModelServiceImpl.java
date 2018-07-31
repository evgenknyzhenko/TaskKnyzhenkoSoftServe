package app.service;

import app.dao.ModelDao;

public class ModelServiceImpl implements ModelService {

    private ModelDao modelDao;

    public ModelServiceImpl(ModelDao modelDao) {
        this.modelDao = modelDao;
    }

    public void createTables(String companyName) {
        modelDao.createTables(companyName);
    }

}
