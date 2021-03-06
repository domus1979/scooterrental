package by.dvn.scooterrental.repository.rental;

import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.rental.PriceList;
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
public class MySqlRepoPriceList extends AbstractMySqlRepo<PriceList> {
    private static final Logger log4jLogger = LogManager.getLogger(MySqlRepoPriceList.class.getName());

    public MySqlRepoPriceList(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public IModelObject read(Integer id) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM PriceList WHERE id = :idParam");
            query.setParameter("idParam", id);
            List<PriceList> objectList = query.list();
            session.clear();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read records in BD for PriceList with id: " + id + ".");
                return objectList.get(0);
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read records in BD for PriceList with id: " + id + ". " +
                    e.getMessage());
        }
        return null;
    }

    @Override
    public List readAll() {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM PriceList");
            List<PriceList> objectList = query.list();
//            String s = objectList.toString();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read all records in BD for PriceList.");
                return objectList;
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read any records in BD for PriceList. " +
                    e.getMessage());
        }
        return null;
    }

}
