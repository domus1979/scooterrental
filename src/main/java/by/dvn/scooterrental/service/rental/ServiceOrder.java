package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoOrder;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.rental.Order;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceOrder extends AbstractService<Order> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceOrder.class.getName());

    public ServiceOrder(AbstractMySqlRepo<Order> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<Order> getMySqlRepo() {
        return super.getMySqlRepo();
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid order`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
//            return null;
        }
        Order obj = (Order) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoOrder.class);
        }
        log4jLogger.error("Not found order with id: " + id);
        throw new HandleNotFoundExeption("Not found order with id: " + id);
//        return null;
    }

    @Override
    public List<IDtoObject> readAll() throws HandleNotFoundExeption {
        List<Order> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoOrder.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any order was found.");
        throw new HandleNotFoundExeption("Not found any order.");
//        return null;
    }

}
