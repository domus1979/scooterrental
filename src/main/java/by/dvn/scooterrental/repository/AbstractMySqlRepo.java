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

//    @Override
//    public IModelObject read(Integer id, String tableName) {
//        Session session = getSession();
//        try {
//            Query query = session.createQuery("FROM " + tableName + " WHERE id = :idParam");
//            query.setParameter("idParam", id);
//            List<T> objectList = query.list();
//            if (objectList.size() > 0) {
//                log4jLogger.info("Success read records in BD for table: " +
//                        tableName + ", for id: " + id + ".");
//                return objectList.get(0);
//            }
//        } catch (Exception e) {
//            log4jLogger.error("Can`t read records in BD for table: " + tableName +
//                    " with id: " + id + ". " + e.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public List<T> readAll() {
//        Session session = getSession();
//        try {
//            Query query = session.createQuery("FROM ");
//            List<T> objectList = query.list();
//            String s = objectList.toString();
//            if (objectList.size() > 0) {
//                log4jLogger.info("Success read all records in BD for table: " +
//                        tableName + ".");
//                return objectList;
//            }
//        } catch (Exception e) {
//            log4jLogger.error("Can`t read any records in BD for table: " +
//                    tableName + ". " + e.getMessage());
//        }
//        return null;
//    }

    @Override
    public boolean update(IModelObject obj) {
        Session session = getSession();
        try {
            session.update(obj);
            log4jLogger.info("Success update records in BD for class: " +
                    obj.getClass().getName() + ", for id: " + obj.getId() + ".");
            return true;
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
