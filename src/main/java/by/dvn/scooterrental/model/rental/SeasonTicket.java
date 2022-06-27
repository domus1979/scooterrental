package by.dvn.scooterrental.model.rental;

import by.dvn.scooterrental.model.IModelObject;
import by.dvn.scooterrental.model.account.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "season_tickets")
public class SeasonTicket implements IModelObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "price_type_id")
    private PriceType priceType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public SeasonTicket() {
    }

    public SeasonTicket(Integer id, LocalDate startDate, PriceType priceType, User user) {
        this.id = id;
        this.startDate = startDate;
        this.priceType = priceType;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
