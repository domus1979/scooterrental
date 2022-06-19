package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoScooter;
import by.dvn.scooterrental.model.rental.Scooter;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceScooter extends AbstractService<Scooter> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceScooter.class.getName());

    public ServiceScooter(AbstractMySqlRepo<Scooter> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<Scooter> getMySqlRepo() {
        return super.getMySqlRepo();
    }

    @Override
    public IDtoObject read(Integer id) {
        if (id == null || id < 0) {
            log4jLogger.error("Not valid scooter`s id value.");
            return null;
        }
        Scooter obj = (Scooter) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoScooter.class);
        }
        log4jLogger.error("Not found scooter with id: " + id);
        return null;
    }

    @Override
    public List<IDtoObject> readAll() {
        List<Scooter> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoScooter.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any scooter was found.");
        return null;
    }

}
