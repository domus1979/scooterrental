package by.dvn.scooterrental.repository.rental;

import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.rental.Order;
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
public class MySqlRepoScooter extends AbstractMySqlRepo<Scooter> {
    private static final Logger log4jLogger = LogManager.getLogger(MySqlRepoScooter.class.getName());

    public MySqlRepoScooter(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public IModelObject read(Integer id) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Scooter WHERE id = :idParam");
            query.setParameter("idParam", id);
            List<Scooter> objectList = query.list();
            session.clear();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read records in BD for Scooter with id: " + id + ".");
                return objectList.get(0);
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read records in BD for Scooter with id: " + id + ". " +
                    e.getMessage());
        }
        return null;
    }

    @Override
    public List readAll() {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Scooter");
            List<Scooter> objectList = query.list();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read all records in BD for Scooter.");
                return objectList;
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read any records in BD for Scooter. " + e.getMessage());
        }
        return null;
    }

    public List getAllScootersAtOrder(Scooter scooter) {
        Session session = getSession();

        try {
            Query query = session.createQuery("FROM Order WHERE scooter = :scooterParam");
            query.setParameter("scooterParam", scooter);
            List<Order> objectList = query.list();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read all records in BD for Order with scooter id: " +
                        scooter.getId());
                return objectList;
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read records in BD for Order with scooter id: " +
                    scooter.getId() + ". " + e.getMessage());
        }
        return null;
    }

}
