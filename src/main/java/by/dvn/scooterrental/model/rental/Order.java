package by.dvn.scooterrental.model.rental;

import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.account.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order implements IModelObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "start_rental_point_id")
    private RentalPoint startRentalPoint;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scooter_id")
    private Scooter scooter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "price_list_id")
    private PriceList priceList;

    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "finish_rental_point_id")
    private RentalPoint finishRentalPoint;

    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @Column(name = "actual_duration")
    private BigInteger actualDuration;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "cost")
    private BigDecimal cost;


    public Order() {
    }

    public Order(Integer id,
                 RentalPoint startRentalPoint,
                 Scooter scooter,
                 User user,
                 PriceList priceList,
                 LocalDateTime beginTime,
                 BigInteger actualDuration,
                 LocalDateTime endTime,
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

    public RentalPoint getStartRentalPoint() {
        return startRentalPoint;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public User getUser() {
        return user;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public RentalPoint getFinishRentalPoint() {
        return finishRentalPoint;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public BigInteger getActualDuration() {
        return actualDuration;
    }

    public LocalDateTime getEndTime() {
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

    public void setStartRentalPoint(RentalPoint startRentalPoint) {
        this.startRentalPoint = startRentalPoint;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setFinishRentalPoint(RentalPoint finishRentalPoint) {
        this.finishRentalPoint = finishRentalPoint;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setActualDuration(BigInteger actualDuration) {
        this.actualDuration = actualDuration;
    }

    public void setEndTime(LocalDateTime endTime) {
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
