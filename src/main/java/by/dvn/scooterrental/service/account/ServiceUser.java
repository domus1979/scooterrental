package by.dvn.scooterrental.service.account;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.account.DtoUser;
import by.dvn.scooterrental.dto.viewreport.ViewOrderInfo;
import by.dvn.scooterrental.handlerexception.*;
import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.account.Role;
import by.dvn.scooterrental.model.account.User;
import by.dvn.scooterrental.model.rental.Order;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.repository.account.MySqlRepoUser;
import by.dvn.scooterrental.service.AbstractService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceUser extends AbstractService<User> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceUser.class.getName());

    private ServiceRole serviceRole;

    private PasswordEncoder passwordEncoder;

    public ServiceUser(AbstractMySqlRepo<User> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    public List<Role> getDefaultRoleList() {
        List<Role> roles = new ArrayList<>();
        Role role = serviceRole.findByName("ROLE_USER");
        if (role != null) {
            roles.add(role);
        }
        return roles;
    }

    @Override
    public MySqlRepoUser getMySqlRepo() {
        return (MySqlRepoUser) super.getMySqlRepo();
    }

    public ServiceRole getServiceRole() {
        return serviceRole;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    public void setServiceRole(ServiceRole serviceRole) {
        this.serviceRole = serviceRole;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid user`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
        }
        User obj = (User) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoUser.class);
        }
        log4jLogger.error("Not found user with id: " + id);
        throw new HandleNotFoundExeption("Not found user with id: " + id);
    }

    @Override
    public List<IDtoObject> readAll() throws HandleNotFoundExeption {
        List<User> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoUser.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any user was found.");
        throw new HandleNotFoundExeption("Not found any user.");
    }

    @Override
    public boolean create(IModelObject obj)
            throws HandleBadCondition, HandleBadRequestPath, HandleNotFoundExeption, HandleNotModified {

        if (checkObject(obj, false)) {
            Role role = getServiceRole().findByName("ROLE_USER");
            if (role != null) {
                List<Role> roles = new ArrayList<>();
                roles.add(role);
                ((User) obj).setRoleList(roles);
                ((User) obj).setPassword(getPasswordEncoder().encode(((User) obj).getPassword()));
                return getMySqlRepo().create(obj);
            }
            log4jLogger.error("Not found user role in repository.");
            throw new HandleNotModified("Not found user role in repository. User not created.");
        } else {
            log4jLogger.error("No user to create.");
            throw new HandleNotModified("No user to create.");
        }

    }

    @Override
    public boolean update(IModelObject obj) throws HandleBadCondition, HandleNotModified {
        if (checkObject(obj, true)) {
            ((User) obj).setPassword(getPasswordEncoder().encode(((User) obj).getPassword()));
            return getMySqlRepo().update(obj);
        } else {
            log4jLogger.error("No object to update.");
            throw new HandleNotModified("No object to update.");
        }
    }

    public User findByName(String name) {
        if (name != null) {
            return getMySqlRepo().findByName(name);
        }
        return null;
    }

    public List<ViewOrderInfo> getInfoUserRental(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid user`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
        }

        User user = (User) getMySqlRepo().read(id);
        if (user == null) {
            log4jLogger.error("Not found user with id: " + id);
            throw new HandleNotFoundExeption("Not found user with id: " + id);
        }

        List<ViewOrderInfo> viewOrderList = new ArrayList<>();

        List<Order> orderList = getMySqlRepo().getAllUsersAtOrder(user);
        if (orderList != null && orderList.size() > 0) {
            for (Order order : orderList) {
                viewOrderList.add(new ViewOrderInfo(order.getId(),
                        order.getScooter().getScooterModel().getName() + " " + order.getScooter().getName(),
                        order.getUser().getFirstName() + " " + order.getUser().getLastName(),
                        order.getStartRentalPoint().getFullName("", order.getStartRentalPoint()),
                        order.getBeginTime(),
                        order.getActualDuration(),
                        order.getEndTime(),
                        order.getFinishRentalPoint().getFullName("", order.getFinishRentalPoint()),
                        order.getCost()));
            }
        }
        return viewOrderList;
    }

    public boolean checkObject(IModelObject obj, boolean findById) throws HandleBadCondition {
        if (obj == null) {
            log4jLogger.error("User is null.");
            throw new HandleBadCondition("User is null.");
        }
        if (!(obj instanceof User)) {
            log4jLogger.error("This is not User object.");
            throw new HandleBadCondition("You wont to use user from another object.");
        }
        if (findById && getMySqlRepo().read(obj.getId()) == null) {
            log4jLogger.error("User with id: " + obj.getId() + " not found.");
            throw new HandleBadCondition("User with id: " + obj.getId() + " not found.");
        }

        if (!findById && findByName(((User) obj).getLogin()) != null) {
            log4jLogger.error("User with id: " + obj.getId() + " not found.");
            throw new HandleBadCondition("User with id: " + obj.getId() + " not found.");
        }

        boolean check = true;
        for (Role role : ((User) obj).getRoleList()) {
            if (!getServiceRole().checkObject(role, true)) {
                check = false;
            }
        }
        return check;
    }

}
