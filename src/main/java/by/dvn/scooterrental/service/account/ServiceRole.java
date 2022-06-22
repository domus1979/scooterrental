package by.dvn.scooterrental.service.account;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.account.DtoRole;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.account.Role;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRole extends AbstractService<Role> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceRole.class.getName());

    @Autowired
    public ServiceRole(AbstractMySqlRepo<Role> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo getMySqlRepo() {
        return super.getMySqlRepo();
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid role`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
//            return null;
        }
        Role obj = (Role) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoRole.class);
        }
        log4jLogger.error("Not found role with id: " + id);
        throw new HandleNotFoundExeption("Not found role with id: " + id);
//        return null;
    }

    @Override
    public List<IDtoObject> readAll() throws HandleNotFoundExeption {
        List<Role> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoRole.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any role was found.");
        throw new HandleNotFoundExeption("No any role was found.");
//        return null;
    }

}
