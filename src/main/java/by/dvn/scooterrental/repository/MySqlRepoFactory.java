package by.dvn.scooterrental.repository;

import by.dvn.scooterrental.repository.account.MySqlRepoRole;
import by.dvn.scooterrental.repository.account.MySqlRepoUser;
import by.dvn.scooterrental.repository.rental.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MySqlRepoFactory {
    private SessionFactory sessionFactory;

    @Autowired
    public MySqlRepoFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AbstractMySqlRepo createMySqlRepo(ModelType modelType) {
        AbstractMySqlRepo repo = null;
        switch (modelType) {
            case ORDER:
                repo = new MySqlRepoOrder(sessionFactory);
                break;
            case PRICE_LIST:
                repo = new MySqlRepoPriceList(sessionFactory);
                break;
            case PRICE_TYPE:
                repo = new MySqlRepoPriceType(sessionFactory);
                break;
            case RENTAL_POINT:
                repo = new MySqlRepoRentalPoint(sessionFactory);
                break;
            case ROLE:
                repo = new MySqlRepoRole(sessionFactory);
                break;
            case SCOOTER:
                repo = new MySqlRepoScooter(sessionFactory);
                break;
            case SCOOTER_MODEL:
                repo = new MySqlRepoScooterModel(sessionFactory);
                break;
            case USER:
                repo = new MySqlRepoUser(sessionFactory);
                break;
        }
        return repo;
    }
}
