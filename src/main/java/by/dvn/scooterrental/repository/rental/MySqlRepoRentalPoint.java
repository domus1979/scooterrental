package by.dvn.scooterrental.repository.rental;

import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.rental.RentalPoint;
import by.dvn.scooterrental.model.rental.Scooter;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class MySqlRepoRentalPoint extends AbstractMySqlRepo<RentalPoint> {
    private static final Logger log4jLogger = LogManager.getLogger(MySqlRepoRentalPoint.class.getName());

    public MySqlRepoRentalPoint(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public IModelObject read(Integer id) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM RentalPoint WHERE id = :idParam");
            query.setParameter("idParam", id);
            List<RentalPoint> objectList = query.list();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read records in BD for RentalPoint with id: " +
                        id + ".");
                return objectList.get(0);
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read records in BD for RentalPoint with id: " + id + ". " +
                    e.getMessage());
        }
        return null;
    }

    @Override
    public List readAll() {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM RentalPoint");
            List<RentalPoint> objectList = query.list();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read all records in BD for RentalPoint.");
                return objectList;
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read any records in BD for RentalPoint. " +
                    e.getMessage());
        }
        return null;
    }

    public List getAllScootersAtRentalPoint(RentalPoint rentalPoint) {
        Session session = getSession();

        try {
            Query query = session.createQuery("FROM Scooter WHERE rentalPoint = :rentalPointParam");
            query.setParameter("rentalPointParam", rentalPoint);
            List<Scooter> objectList = query.list();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read all records in BD for Scooter with rentalPoint id: " +
                        rentalPoint.getId());
                return objectList;
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read records in BD for Scooter with rentalPoint id: " +
                    rentalPoint.getId() + ". " + e.getMessage());
        }
        return null;
    }

}
