package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoScooterModel;
import by.dvn.scooterrental.model.rental.ScooterModel;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceScooterModel extends AbstractService<ScooterModel> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceScooterModel.class.getName());

    public ServiceScooterModel(AbstractMySqlRepo<ScooterModel> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<ScooterModel> getMySqlRepo() {
        return super.getMySqlRepo();
    }

    @Override
    public IDtoObject read(Integer id) {
        if (id == null || id < 0) {
            log4jLogger.error("Not valid scooter model`s id value.");
            return null;
        }
        ScooterModel obj = (ScooterModel) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoScooterModel.class);
        }
        log4jLogger.error("Not found scooter model with id: " + id);
        return null;
    }

    @Override
    public List<IDtoObject> readAll() {
        List<ScooterModel> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoScooterModel.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any scooter model was found.");
        return null;
    }

}
