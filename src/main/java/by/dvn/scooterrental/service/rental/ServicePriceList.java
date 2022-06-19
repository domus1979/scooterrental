package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoPriceList;
import by.dvn.scooterrental.model.rental.PriceList;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicePriceList extends AbstractService<PriceList> {
    private static final Logger log4jLogger = LogManager.getLogger(ServicePriceList.class.getName());

    public ServicePriceList(AbstractMySqlRepo<PriceList> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<PriceList> getMySqlRepo() {
        return super.getMySqlRepo();
    }

    @Override
    public IDtoObject read(Integer id) {
        if (id == null || id < 0) {
            log4jLogger.error("Not valid price list`s id value.");
            return null;
        }
        PriceList obj = (PriceList) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoPriceList.class);
        }
        log4jLogger.error("Not found price list with id: " + id);
        return null;
    }

    @Override
    public List<IDtoObject> readAll() {
        List<PriceList> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoPriceList.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any price list was found.");
        return null;
    }

}
