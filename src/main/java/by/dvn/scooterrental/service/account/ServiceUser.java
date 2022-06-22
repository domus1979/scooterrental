package by.dvn.scooterrental.service.account;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.account.DtoUser;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceUser extends AbstractService<User> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceUser.class.getName());

    public ServiceUser(AbstractMySqlRepo<User> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<User> getMySqlRepo() {
        return super.getMySqlRepo();
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid user`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
//            return null;
        }
        User obj = (User) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoUser.class);
        }
        log4jLogger.error("Not found user with id: " + id);
        throw new HandleNotFoundExeption("Not found user with id: " + id);
//        return null;
    }

    @Override
    public List<IDtoObject> readAll() throws HandleNotFoundExeption {
        List<User> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoUser.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any user was found.");
        throw new HandleNotFoundExeption("Not found any user.");
//        return null;
    }

}
