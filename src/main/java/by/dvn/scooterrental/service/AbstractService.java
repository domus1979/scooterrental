package by.dvn.scooterrental.service;

import by.dvn.scooterrental.handlerexception.HandleBadRequestBody;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
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
    public boolean create(IModelObject obj) throws HandleBadRequestBody {
        if (obj != null) {
            if (mySqlRepo.create(obj)) {
                return true;
            } else {
                throw new HandleBadRequestBody("Not valid body object. Can`t create object.");
            }
        }
        log4jLogger.error("No object to create.");
        throw new HandleBadRequestBody("No object to create.");
//        return false;
    }

    @Override
    public boolean update(IModelObject obj) throws HandleBadRequestBody {
        if (obj != null) {
            if (mySqlRepo.update(obj)) {
                return true;
            } else {
                throw new HandleBadRequestBody("Not valid body object. Can`t update object.");
            }
        }
        log4jLogger.error("No object to update.");
        throw new HandleBadRequestBody("No object to create.");
//        return false;
    }

    @Override
    public boolean delete(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id != null && id > 0) {
            if (mySqlRepo.delete(id)) {
                return true;
            } else {
                throw new HandleNotFoundExeption("Not found object with id: " + id);
            }
        }
        log4jLogger.error("No object to delete.");
        throw new HandleBadRequestPath("Not valid request path.");
//        return false;
    }

}
