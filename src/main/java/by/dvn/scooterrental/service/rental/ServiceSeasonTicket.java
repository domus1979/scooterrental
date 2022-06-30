package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoSeasonTicket;
import by.dvn.scooterrental.handlerexception.HandleBadCondition;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.model.rental.SeasonTicket;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import by.dvn.scooterrental.service.account.ServiceUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceSeasonTicket extends AbstractService<SeasonTicket> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceSeasonTicket.class.getName());

    private ServicePriceType servicePriceType;

    private ServiceUser serviceUser;

    public ServiceSeasonTicket(AbstractMySqlRepo<SeasonTicket> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<SeasonTicket> getMySqlRepo() {
        return super.getMySqlRepo();
    }

    public ServicePriceType getServicePriceType() {
        return servicePriceType;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    @Autowired
    public void setServicePriceType(ServicePriceType servicePriceType) {
        this.servicePriceType = servicePriceType;
    }

    @Autowired
    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid season ticket`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
        }
        SeasonTicket obj = (SeasonTicket) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoSeasonTicket.class);
        }
        log4jLogger.error("Not found season ticket with id: " + id);
        throw new HandleNotFoundExeption("Not found season ticket with id: " + id);
    }

    @Override
    public List readAll() throws HandleNotFoundExeption {
        List<SeasonTicket> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoSeasonTicket.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any season ticket was found.");
        throw new HandleNotFoundExeption("Not found any season ticket.");
    }

    public boolean checkObject(IModelObject obj, boolean findById) throws HandleBadCondition {
        if (obj == null) {
            log4jLogger.error("Season ticket is null.");
            throw new HandleBadCondition("Season ticket is null.");
        }
        if (!(obj instanceof SeasonTicket)) {
            log4jLogger.error("This is not SeasonTicket object.");
            throw new HandleBadCondition("You wont to use season ticket from another object.");
        }
        if (findById && getMySqlRepo().read(obj.getId()) == null) {
            log4jLogger.error("Season ticket with id: " + obj.getId() + " not found.");
            throw new HandleBadCondition("Season ticket with id: " + obj.getId() + " not found.");
        }

        if (!getServicePriceType().checkObject(((SeasonTicket) obj).getPriceType(), true) ||
                !getServiceUser().checkObject(((SeasonTicket) obj).getUser(), true)) {
            return false;
        }
        return true;
    }
}
