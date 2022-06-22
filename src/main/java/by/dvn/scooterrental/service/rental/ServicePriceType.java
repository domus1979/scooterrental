package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoPriceType;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.rental.PriceType;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicePriceType extends AbstractService<PriceType> {
    private static final Logger log4jLogger = LogManager.getLogger(ServicePriceType.class.getName());

    public ServicePriceType(AbstractMySqlRepo<PriceType> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<PriceType> getMySqlRepo() {
        return super.getMySqlRepo();
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid price type`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
//            return null;
        }
        PriceType obj = (PriceType) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoPriceType.class);
        }
        log4jLogger.error("Not found price type with id: " + id);
        throw new HandleNotFoundExeption("Not found price type with id: " + id);
//        return null;
    }

    @Override
    public List<IDtoObject> readAll() throws HandleNotFoundExeption {
        List<PriceType> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoPriceType.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any price type was found.");
        throw new HandleNotFoundExeption("Not found any price type.");
//        return null;
    }

}
