package by.dvn.scooterrental.service.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.rental.DtoOrder;
import by.dvn.scooterrental.dto.rental.DtoScooter;
import by.dvn.scooterrental.handlerexception.*;
import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.rental.*;
import by.dvn.scooterrental.repository.AbstractMySqlRepo;
import by.dvn.scooterrental.repository.rental.MySqlRepoOrder;
import by.dvn.scooterrental.service.AbstractService;
import by.dvn.scooterrental.service.account.ServiceUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceOrder extends AbstractService<Order> {
    private static final Logger log4jLogger = LogManager.getLogger(ServiceOrder.class.getName());

    private ServiceScooter serviceScooter;

    private ServiceRentalPoint serviceRentalPoint;

    private ServicePriceList servicePriceList;

    private ServiceUser serviceUser;

    public ServiceOrder(AbstractMySqlRepo<Order> mySqlRepo, ModelMapper modelMapper) {
        super(mySqlRepo, modelMapper);
    }

    @Override
    public MySqlRepoOrder getMySqlRepo() {
        return (MySqlRepoOrder) super.getMySqlRepo();
    }

    public ServiceScooter getServiceScooter() {
        return serviceScooter;
    }

    public ServiceRentalPoint getServiceRentalPoint() {
        return serviceRentalPoint;
    }

    public ServicePriceList getServicePriceList() {
        return servicePriceList;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    @Autowired
    public void setServiceScooter(ServiceScooter serviceScooter) {
        this.serviceScooter = serviceScooter;
    }

    @Autowired
    public void setServiceRentalPoint(ServiceRentalPoint serviceRentalPoint) {
        this.serviceRentalPoint = serviceRentalPoint;
    }

    @Autowired
    public void setServicePriceList(ServicePriceList servicePriceList) {
        this.servicePriceList = servicePriceList;
    }

    @Autowired
    public void setServiceUser(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Override
    public IDtoObject read(Integer id) throws HandleBadRequestPath, HandleNotFoundExeption {
        if (id == null || id <= 0) {
            log4jLogger.error("Not valid order`s id value.");
            throw new HandleBadRequestPath("Not valid id path.");
        }
        Order obj = (Order) getMySqlRepo().read(id);
        if (obj != null) {
            return getModelMapper().map(obj, DtoOrder.class);
        }
        log4jLogger.error("Not found order with id: " + id);
        throw new HandleNotFoundExeption("Not found order with id: " + id);
    }

    @Override
    public List<IDtoObject> readAll() throws HandleNotFoundExeption {
        List<Order> objList = getMySqlRepo().readAll();
        if (objList != null && objList.size() > 0) {
            return objList.stream()
                    .map(obj -> getModelMapper().map(obj, DtoOrder.class))
                    .collect(Collectors.toList());
        }
        log4jLogger.error("No any order was found.");
        throw new HandleNotFoundExeption("Not found any order.");
    }

    @Override
    public boolean create(IModelObject order)
            throws HandleBadCondition, HandleBadRequestPath, HandleNotFoundExeption, HandleNotModified {

        if (checkObject(order, false)) {

            order = setOrderDefaultParameters((Order) order);

            if (!ScooterStatus.AVAILABLE
                    .equals(((DtoScooter) getServiceScooter()
                            .read(((Order) order)
                                    .getScooter()
                                    .getId()))
                            .getScooterStatus())) {
                log4jLogger.error("Scooter for rental not avalible.");
                throw new HandleBadCondition("Scooter for rental not avalible..");
            }

            ((Order) order).getScooter().setScooterStatus(ScooterStatus.RENTAL);
            if (getServiceScooter().update(((Order) order).getScooter())) {
                log4jLogger.info("Scooter status for scooter from order with id " +
                        order.getId() + " has change to rental.");
            } else {
                log4jLogger.error("Scooter for rental not modified to RENTAL status.");
                throw new HandleNotModified("Scooter for rental not modified to RENTAL status.");
            }

            if (getMySqlRepo().create(order)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(IModelObject order) throws HandleBadCondition, HandleNotModified {
        if (checkObject(order, true)) {

            order = calculateOrder((Order) order);

            if (OrderStatus.CLOSE.equals(((Order) order).getOrderStatus())) {
                ((Order) order).getScooter().setScooterStatus(ScooterStatus.AVAILABLE);
                if (getServiceScooter().update(((Order) order).getScooter())) {
                    log4jLogger.info("Scooter status for scooter from order with id " +
                            order.getId() + " has change to avalible.");
                } else {
                    log4jLogger.error("Scooter for rental not modified to AVALIBLE status.");
                    throw new HandleNotModified("Scooter for rental not modified to RENTAL status.");
                }
            }
            if (getMySqlRepo().update(order)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkObject(IModelObject obj, boolean findById) throws HandleBadCondition {
        if (obj == null) {
            log4jLogger.error("Order is null.");
            throw new HandleBadCondition("Order is null");
        }
        if (!(obj instanceof Order)) {
            log4jLogger.error("This is not Order object.");
            throw new HandleBadCondition("You wont create order from another object.");
        }
        if (findById && getMySqlRepo().read(obj.getId()) == null) {
            log4jLogger.error("Order model with id: " + obj.getId() + " not found.");
            throw new HandleBadCondition("Order model with id: " + obj.getId() + " not found.");
        }

        if (!getServiceScooter().checkObject(((Order) obj).getScooter(), true) ||
                !getServiceRentalPoint().checkObject(((Order) obj).getStartRentalPoint(), true) ||
                !getServiceRentalPoint().checkObject(((Order) obj).getFinishRentalPoint(), true) ||
                !getServiceUser().checkObject(((Order) obj).getUser(),true)) {
            return false;
        }

        if (!((Order) obj).getStartRentalPoint().equals(((Order) obj).getScooter().getRentalPoint())) {
            log4jLogger.error("Scooter from order not contains in rental point from order.");
            throw new HandleBadCondition("Scooter from order not contains in rental point from order.");
        }

        PriceList priceList = ((Order) obj).getPriceList();
        if (priceList != null &&
                getServicePriceList().checkObject(priceList, true)) {

            if (!priceList.getPriceType().isSeasonTicket() &&
                    !priceList.getScooterModel()
                            .equals(((Order) obj).getScooter().getScooterModel())) {
                log4jLogger.error("The price list from the order is not intended for the scooter model.");
                throw new HandleBadCondition("The price list from the order is not intended for the scooter model.");
            }
            if (((Order) obj).getBeginTime().isAfter(((Order) obj).getEndTime())) {
                log4jLogger.error("The begin date of order is after end date.");
                throw new HandleBadCondition("The begin date of order is after end date.");
            }
            if (priceList.getPriceType().isSeasonTicket()) {
                //find season tickets
                SeasonTicket seasonTicket = getMySqlRepo().findSeasonTicket((Order) obj);

                LocalDateTime ticketTime = ((Order) obj).getBeginTime();
                LocalDateTime seasonTicketBeginTime = seasonTicket.getStartDate().atStartOfDay();
                LocalDateTime seasonTicketEndTime = seasonTicket
                        .getStartDate()
                        .atStartOfDay()
                        .plusMinutes(seasonTicket.getPriceType().getDurationInMinutes().longValue());
                if (ticketTime.isBefore(seasonTicketBeginTime) || ticketTime.isAfter(seasonTicketEndTime)) {
                    log4jLogger.error("Start time of the order does not fall within " +
                            "the season ticket period.");
                    throw new HandleBadCondition("Start time of the order does not fall within " +
                            "the season ticket period.");
                }
            }
        }
        return true;
    }

    private Order setOrderDefaultParameters(Order order) {
        if (order.getPriceList() != null) {
            if (!order.getPriceList().getPriceType().isSeasonTicket()) {
                order.setActualDuration(order.getPriceList().getPriceType().getDurationInMinutes());
                order.setEndTime(order.getBeginTime().plusMinutes(order
                        .getPriceList()
                        .getPriceType()
                        .getDurationInMinutes().longValue()));
                order.setPrice(order.getPriceList().getPrice());
            } else {
                order.setActualDuration(new BigInteger("0"));
                order.setEndTime(order.getBeginTime());
                order.setPrice(new BigDecimal("0"));
            }
            log4jLogger.info("Parameters: duration, end time and price  was change to default for order with id: "
                    + order.getId() + ".");
        }
        return order;
    }

    private Order calculateOrder(Order order) {
        if (order.getPriceList() != null) {
            if (!order.getPriceList().getPriceType().isSeasonTicket()) {
                order.setEndTime(order
                        .getBeginTime()
                        .plusMinutes(order
                                .getActualDuration()
                                .longValue()));
                order.setPrice(order.getPriceList().getPrice());

                BigInteger priceDuration = order
                        .getPriceList()
                        .getPriceType()
                        .getDurationInMinutes();
                BigDecimal koef = BigDecimal.valueOf(order.getActualDuration().doubleValue() /
                        priceDuration.doubleValue());
                BigDecimal cost = new BigDecimal(0);
                if (koef.compareTo(new BigDecimal(1)) == 1) {
                    //If rental duration more than price duration, than calculate proportionately
                    cost = BigDecimal.valueOf(order.getPriceList().getPrice().longValue()).multiply(koef);
                } else {
                    cost = order.getPriceList().getPrice();
                }
                if (order.getDiscount() > 0) {
                    cost = cost.subtract(cost.multiply(BigDecimal.valueOf(order.getDiscount())));
                }
                order.setCost(cost);
            } else {
                order.setPrice(new BigDecimal(0));
                order.setCost(new BigDecimal(0));
            }
        } else {
            if (order.getDiscount() > 0) {
                BigDecimal cost = order.getPrice();
                cost = cost.subtract(cost.multiply(BigDecimal.valueOf(order.getDiscount())));
            }
        }
        return order;
    }

}
