package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoSeasonTicket;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.rental.SeasonTicket;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceSeasonTicket extends AbstractService<SeasonTicket> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceSeasonTicket.class.getName());

    public ServiceSeasonTicket(AbstractMySqlRepo<SeasonTicket> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public AbstractMySqlRepo<SeasonTicket> getMySqlRepo() {
        return super.getMySqlRepo();
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

}
