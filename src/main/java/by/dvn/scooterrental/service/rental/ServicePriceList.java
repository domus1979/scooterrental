package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoPriceList;
import by.dvn.scooterrental.handlerexception.*;
import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.rental.PriceList;
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
public class ServicePriceList extends AbstractService<PriceList> {
    private static final Logger log4jLogger = LogManager.getLogger(ServicePriceList.class.getName());

    private ServicePriceType servicePriceType;

    private ServiceScooterModel serviceScooterModel;

    public ServicePriceList(AbstractMySqlRepo<PriceList> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<PriceList> getMySqlRepo() {
        return super.getMySqlRepo();
    }

    public ServicePriceType getServicePriceType() {
        return servicePriceType;
    }

    public ServiceScooterModel getServiceScooterModel() {
        return serviceScooterModel;
    }

    @Autowired
    public void setServicePriceType(ServicePriceType servicePriceType) {
        this.servicePriceType = servicePriceType;
    }

    @Autowired
    public void setServiceScooterModel(ServiceScooterModel serviceScooterModel) {
        this.serviceScooterModel = serviceScooterModel;
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid price list`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
        }
        PriceList obj = (PriceList) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoPriceList.class);
        }
        log4jLogger.error("Not found price list with id: " + id);
        throw new HandleNotFoundExeption("Not found price list with id: " + id);
    }

    @Override
    public List<IDtoObject> readAll() throws HandleNotFoundExeption {
        List<PriceList> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoPriceList.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any price list was found.");
        throw new HandleNotFoundExeption("Not found any price list.");
    }

    public boolean checkObject(IModelObject obj, boolean findById) throws HandleBadCondition {
        if (obj == null) {
            log4jLogger.error("Price list is null.");
            throw new HandleBadCondition("Price list is null.");
        }
        if (!(obj instanceof PriceList)) {
            log4jLogger.error("This is not PriceList object.");
            throw new HandleBadCondition("You wont to use price type from another object.");
        }
        if (findById && getMySqlRepo().read(obj.getId()) == null) {
            log4jLogger.error("Price list with id: " + obj.getId() + " not found.");
            throw new HandleBadCondition("Price list with id: " + obj.getId() + " not found.");
        }
        if (!getServicePriceType().checkObject(((PriceList) obj).getPriceType(), true) ||
                (((PriceList) obj).getScooterModel() != null &&
                        !getServiceScooterModel().checkObject(((PriceList) obj).getScooterModel(), true))) {
            return false;
        }
        return true;
    }

}
