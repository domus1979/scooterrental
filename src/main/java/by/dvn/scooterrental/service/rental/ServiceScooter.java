package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoScooter;
import by.dvn.scooterrental.dto.viewreport.ViewOrderInfo;
import by.dvn.scooterrental.handlerexception.HandleBadCondition;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.rental.Order;
import by.dvn.scooterrental.model.rental.Scooter;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.repository.rental.MySqlRepoScooter;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceScooter extends AbstractService<Scooter> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceScooter.class.getName());

    private ServiceScooterModel serviceScooterModel;

    private ServiceRentalPoint serviceRentalPoint;

    public ServiceScooter(AbstractMySqlRepo<Scooter> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public MySqlRepoScooter getMySqlRepo() {
        return (MySqlRepoScooter) super.getMySqlRepo();
    }

    public ServiceScooterModel getServiceScooterModel() {
        return serviceScooterModel;
    }

    public ServiceRentalPoint getServiceRentalPoint() {
        return serviceRentalPoint;
    }

    @Autowired
    public void setServiceScooterModel(ServiceScooterModel serviceScooterModel) {
        this.serviceScooterModel = serviceScooterModel;
    }

    @Autowired
    public void setServiceRentalPoint(ServiceRentalPoint serviceRentalPoint) {
        this.serviceRentalPoint = serviceRentalPoint;
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid scooter`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
        }
        Scooter obj = (Scooter) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoScooter.class);
        }
        log4jLogger.error("Not found scooter with id: " + id);
        throw new HandleNotFoundExeption("Not found scooter with id: " + id);
    }

    @Override
    public List<IDtoObject> readAll() throws HandleNotFoundExeption {
        List<Scooter> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoScooter.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any scooter was found.");
        throw new HandleNotFoundExeption("Not found any scooter.");
    }

    public List<ViewOrderInfo> getInfoScooterRental(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid scooter`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
        }

        Scooter scooter = (Scooter) getMySqlRepo().read(id);
        if (scooter == null) {
            log4jLogger.error("Not found scooter with id: " + id);
            throw new HandleNotFoundExeption("Not found scooter with id: " + id);
        }

        List<ViewOrderInfo> viewOrderList = new ArrayList<>();

        List<Order> orderList = getMySqlRepo().getAllScootersAtOrder(scooter);
        if (orderList != null && orderList.size() > 0) {
            for (Order order : orderList) {
                viewOrderList.add(new ViewOrderInfo(order.getId(),
                        scooter.getScooterModel().getName() + " " + scooter.getName(),
                        order.getUser().getFirstName() + " " + order.getUser().getLastName(),
                        order.getStartRentalPoint().getFullName("", order.getStartRentalPoint()),
                        order.getBeginTime(),
                        order.getActualDuration(),
                        order.getEndTime(),
                        order.getFinishRentalPoint().getFullName("", order.getFinishRentalPoint()),
                        order.getCost()));
            }
        }
        return viewOrderList;
    }

    public boolean checkObject(IModelObject obj, boolean findById) throws HandleBadCondition {
        if (obj == null) {
            log4jLogger.error("Scooter is null.");
            throw new HandleBadCondition("Scooter is null.");
        }
        if (!(obj instanceof Scooter)) {
            log4jLogger.error("This is not Scooter object.");
            throw new HandleBadCondition("You wont to use scooter from another object.");
        }
        if (findById && getMySqlRepo().read((obj).getId()) == null) {
            log4jLogger.error("Scooter with id: " + (obj).getId() +
                    " not found.");
            throw new HandleBadCondition("Scooter with id: " + (obj).getId() +
                    " not found.");
        }

        if (!getServiceScooterModel().checkObject(((Scooter) obj).getScooterModel(), true) ||
                !getServiceRentalPoint().checkObject(((Scooter) obj).getRentalPoint(), true)) {
            return false;
        }
        return true;
    }

}
