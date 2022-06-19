package by.dvn.scooterrental.service;

import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService<T extends IModelObject> implements IService {
    private static final Logger log4jLogger = LogManager.getLogger(AbstractService.class.getName());
    private AbstractMySqlRepo<T> mySqlRepo;
    private ModelMapper modelMapper;

    @Autowired
    public AbstractService(AbstractMySqlRepo<T> mySqlRepo, ModelMapper modelMapper) {
        this.mySqlRepo = mySqlRepo;
        this.modelMapper = modelMapper;
    }

    public AbstractMySqlRepo<T> getMySqlRepo() {
        return mySqlRepo;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Override
    public void create(IModelObject obj) {
        if (obj != null) {
            mySqlRepo.create(obj);
        }
        log4jLogger.error("No object to create.");
    }

    @Override
    public boolean update(IModelObject obj) {
        if (obj != null) {
            return mySqlRepo.update(obj);
        }
        log4jLogger.error("No object to update.");
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        if (id != null) {
            return mySqlRepo.delete(id);
        }
        log4jLogger.error("No object to delete.");
        return false;
    }

}
