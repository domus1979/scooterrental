package by.dvn.scooterrental.repository.account;

import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.account.User;
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
public class MySqlRepoUser extends AbstractMySqlRepo<User> {
    private static final Logger log4jLogger = LogManager.getLogger(MySqlRepoUser.class.getName());

    public MySqlRepoUser(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public IModelObject read(Integer id) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM User WHERE id = :idParam");
            query.setParameter("idParam", id);
            List<User> objectList = query.list();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read records in BD for User with id: " + id + ".");
                return objectList.get(0);
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read records in BD for User with id: " + id + ". " +
                    e.getMessage());
        }
        return null;
    }

    @Override
    public List readAll() {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM User");
            List<User> objectList = query.list();
//            String s = objectList.toString();
            if (objectList.size() > 0) {
                log4jLogger.info("Success read all records in BD for User.");
                return objectList;
            }
        } catch (Exception e) {
            log4jLogger.error("Can`t read any records in BD for User. " + e.getMessage());
        }
        return null;
    }

}
