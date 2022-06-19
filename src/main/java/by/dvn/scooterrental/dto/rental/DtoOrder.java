package by.dvn.scooterrental.dto.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.account.DtoUser;
import by.dvn.scooterrental.model.rental.OrderStatus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public class DtoOrder implements IDtoObject {
    private Integer id;

    private DtoRentalPoint startRentalPoint;

    private DtoScooter scooter;

    private DtoUser user;

    private DtoPriceList priceList;

    private OrderStatus orderStatus;

    private DtoRentalPoint finishRentalPoint;

    private LocalDate beginTime;

    private BigInteger actualDuration;

    private LocalDate endTime;

    private BigDecimal price;

    private Double discount;

    private BigDecimal cost;

    public DtoOrder() {
    }

    public DtoOrder(Integer id,
                    DtoRentalPoint startRentalPoint,
                    DtoScooter scooter,
                    DtoUser user,
                    DtoPriceList priceList,
                    LocalDate beginTime,
                    BigInteger actualDuration,
                    LocalDate endTime,
                    BigDecimal price,
                    Double discount,
                    BigDecimal cost) {
        this.id = id;
        this.startRentalPoint = startRentalPoint;
        this.scooter = scooter;
        this.user = user;
        this.priceList = priceList;
        this.orderStatus = OrderStatus.OPEN;
        this.finishRentalPoint = startRentalPoint;
        this.beginTime = beginTime;
        this.actualDuration = actualDuration;
        this.endTime = endTime;
        this.price = price;
        this.discount = discount;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public DtoRentalPoint getStartRentalPoint() {
        return startRentalPoint;
    }

    public DtoScooter getScooter() {
        return scooter;
    }

    public DtoUser getUser() {
        return user;
    }

    public DtoPriceList getPriceList() {
        return priceList;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public DtoRentalPoint getFinishRentalPoint() {
        return finishRentalPoint;
    }

    public LocalDate getBeginTime() {
        return beginTime;
    }

    public BigInteger getActualDuration() {
        return actualDuration;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Double getDiscount() {
        return discount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStartRentalPoint(DtoRentalPoint startRentalPoint) {
        this.startRentalPoint = startRentalPoint;
    }

    public void setScooter(DtoScooter scooter) {
        this.scooter = scooter;
    }

    public void setUser(DtoUser user) {
        this.user = user;
    }

    public void setPriceList(DtoPriceList priceList) {
        this.priceList = priceList;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setFinishRentalPoint(DtoRentalPoint finishRentalPoint) {
        this.finishRentalPoint = finishRentalPoint;
    }

    public void setBeginTime(LocalDate beginTime) {
        this.beginTime = beginTime;
    }

    public void setActualDuration(BigInteger actualDuration) {
        this.actualDuration = actualDuration;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

}
