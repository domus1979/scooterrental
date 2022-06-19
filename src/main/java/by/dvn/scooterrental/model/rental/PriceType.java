package by.dvn.scooterrental.model.rental;

import by.dvn.scooterrental.model.IModelObject;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "price_types")
public class PriceType implements IModelObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration_in_minutes")
    private BigInteger durationInMinutes;

    @Column(name = "season_ticket")
    private boolean seasonTicket;

    public PriceType() {
    }

    public PriceType(Integer id, String name, BigInteger durationInMinutes, boolean seasonTicket) {
        this.id = id;
        this.name = name;
        this.durationInMinutes = durationInMinutes;
        this.seasonTicket = seasonTicket;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigInteger getDurationInMinutes() {
        return durationInMinutes;
    }

    public boolean isSeasonTicket() {
        return seasonTicket;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDurationInMinutes(BigInteger durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setSeasonTicket(boolean seasonTicket) {
        this.seasonTicket = seasonTicket;
    }

}
