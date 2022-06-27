package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoRentalPoint;
import by.dvn.scooterrental.dto.viewreport.ViewRentalPointInfo;
import by.dvn.scooterrental.dto.viewreport.ViewScooterInfo;
import by.dvn.scooterrental.handlerexception.HandleBadRequestPath;
import by.dvn.scooterrental.handlerexception.HandleNotFoundExeption;
import by.dvn.scooterrental.model.rental.RentalPoint;
import by.dvn.scooterrental.model.rental.Scooter;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.repository.rental.MySqlRepoRentalPoint;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRentalPoint extends AbstractService<RentalPoint> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceRentalPoint.class.getName());

    public ServiceRentalPoint(AbstractMySqlRepo<RentalPoint> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public MySqlRepoRentalPoint getMySqlRepo() {
        return (MySqlRepoRentalPoint) super.getMySqlRepo();
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid rental point`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
        }
        RentalPoint obj = (RentalPoint) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoRentalPoint.class);
        }
        log4jLogger.error("Not found rental point with id: " + id);
        throw new HandleNotFoundExeption("Not found rental point with id: " + id);
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
    }

    public ViewRentalPointInfo getInfo(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid rental point`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
        }

        RentalPoint rentalPoint = (RentalPoint) getMySqlRepo().read(id);

        if (rentalPoint == null) {
            log4jLogger.error("Not found rental point with id: " + id);
            throw new HandleNotFoundExeption("Not found rental point with id: " + id);
        }

        String fullName = rentalPoint.getFullName("", rentalPoint);
        List<ViewScooterInfo> viewScooterList = new ArrayList<>();

        List<Scooter> scooterList = getMySqlRepo().getAllScootersAtRentalPoint(rentalPoint);
        if (scooterList != null && scooterList.size() > 0) {
            for (Scooter scooter : scooterList) {
                viewScooterList.add(new ViewScooterInfo(scooter.getName(),
                        scooter.getScooterModel().getName(),
                        scooter.getScooterModel().getMaxSpeed(),
                        scooter.getScooterModel().getWorkTime(),
                        scooter.getScooterModel().getMaxWeight(),
                        scooter.getManufactureYear(),
                        scooter.getScooterStatus()));
            }
        }
        return new ViewRentalPointInfo(fullName, viewScooterList);
    }


}
