package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoRentalPoint;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.rental.RentalPoint;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRentalPoint extends AbstractService<RentalPoint> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceRentalPoint.class.getName());

    public ServiceRentalPoint(AbstractMySqlRepo<RentalPoint> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<RentalPoint> getMySqlRepo() {
        return super.getMySqlRepo();
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid rental point`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
//            return null;
        }
        RentalPoint obj = (RentalPoint) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoRentalPoint.class);
        }
        log4jLogger.error("Not found rental point with id: " + id);
        throw new HandleNotFoundExeption("Not found rental point with id: " + id);
//        return null;
    }

    @Override
    public List<IDtoObject> readAll() throws HandleNotFoundExeption {
        List<RentalPoint> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoRentalPoint.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any rental point was found.");
        throw new HandleNotFoundExeption("Not found any rental point.");
//        return null;
    }

}
