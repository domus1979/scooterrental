package by.dvn.scooterrental.repository;

import by.dvn.scooterrental.model.IModelObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public abstract class AbstractMySqlRepo<T extends IModelObject> implements IRepository {
    private SessionFactory sessionFactory;
    private static final Logger log4jLogger = LogManager.getLogger(AbstractMySqlRepo.class.getName());

    @Autowired
    public AbstractMySqlRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession().getSession();
    }

    @Override
    public boolean create(IModelObject obj) {
        Session session = getSession();
        try {
            session.save(obj);
            log4jLogger.info("Successful create record in BD for class: " +
                    obj.getClass().getName() + " with id: " + obj.getId());
            return true;
        } catch (HibernateException e) {
            log4jLogger.error("Hibernate error. Can`t create record in BD for class: " +
                    obj.getClass().getName() + ". " + e.getMessage());
        } catch (Exception e) {
            log4jLogger.error("Can`t create record in BD for class: " +
                    obj.getClass().getName() + ". " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(IModelObject obj) {
        Session session = getSession();
        try {
            session.update(obj);
            log4jLogger.info("Success update records in BD for class: " +
                    obj.getClass().getName() + ", for id: " + obj.getId() + ".");
            return true;
        } catch (Error error) {
            log4jLogger.error("Can`t update records in BD for class: " + obj.getClass().getName() +
                    " with id: " + obj.getId() + ". " + error.getMessage());
        } catch (Exception e) {
            log4jLogger.error("Can`t update records in BD for class: " + obj.getClass().getName() +
                    " with id: " + obj.getId() + ". " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        IModelObject obj = read(id);
        if (obj == null) {
            log4jLogger.error("Can`t found records in BD for id: " + id + ".");
            return false;
        }
        try {
            Session session = getSession();
            session.delete(obj);
            log4jLogger.error("Success delete records in BD for class: " +
                    obj.getClass().getName() + ", for id: " + id + ".");
            return true;
        } catch (Exception e) {
            log4jLogger.error("Can`t delete records in BD for class: " +
                    obj.getClass().getName() + " with id: " + id + ". " + e.getMessage());
        }
        return false;
    }

}
