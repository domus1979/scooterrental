package by.dvn.scooterrental.repository.rental;

import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.rental.Order;
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
public class MySqlRepoOrder extends AbstractMySqlRepo<Order> {
    private static final Logger log4jLogger = LogManager.getLogger(MySqlRepoOrder.class.getName());

    public MySqlRepoOrder(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public IModelObject read(Integer id) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Order WHERE id = :idParam");
            query.setParameter("idParam", id);
            List<Order> objectList = query.list();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read records in BD for Order with id: " + id + ".");
                return objectList.get(0);
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read records in BD for Order with id: " + id + ". " +
                    e.getMessage());
        }
        return null;
    }

    @Override
    public List readAll() {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM Order");
            List<Order> objectList = query.list();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read all records in BD for Order.");
                return objectList;
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read any records in BD for Order. " + e.getMessage());
        }
        return null;
    }

    public SeasonTicket findSeasonTicket(Order order) {
        Session session = getSession();

        try {
            String reqText = "FROM SeasonTicket st WHERE user = :userParam,  priceType = :priceTypeParam " +
                    "ORDER BY st.startDate DESC";
            Query query = session.createQuery(reqText);
            query.setParameter("userParam", order.getUser());
            query.setParameter("priceTypeParam", order.getPriceList().getPriceType());
            List<SeasonTicket> objList = query.list();

            if (objList.size() > 0) {
                log4jLogger.info("Success found SeasonTicket/.");
                return objList.get(0);
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t found any SeasonTicket. " + e.getMessage());
        }
        return null;
    }

}
