package by.dvn.scooterrental.repository.rental;

import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.rental.SeasonTicket;
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
public class MySqlRepoSeasonTicket extends AbstractMySqlRepo<SeasonTicket> {
    private static final Logger log4jLogger = LogManager.getLogger(MySqlRepoSeasonTicket.class.getName());

    public MySqlRepoSeasonTicket(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public IModelObject read(Integer id) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM SeasonTicket WHERE id = :idParam");
            query.setParameter("idParam", id);
            List<SeasonTicket> objectList = query.list();
            session.clear();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read records in BD for SeasonTicket with id: " + id + ".");
                return objectList.get(0);
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read records in BD for SeasonTicket with id: " + id + ". " +
                    e.getMessage());
        }
        return null;
    }

    @Override
    public List readAll() {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Order");
            List<SeasonTicket> objectList = query.list();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read all records in BD for SeasonTicket.");
                return objectList;
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read any records in BD for SeasonTicket. " + e.getMessage());
        }
        return null;
    }
}
