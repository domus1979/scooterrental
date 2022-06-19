package by.dvn.scooterrental.dto.rental;

import by.dvn.scooterrental.dto.IDtoObject;

import java.math.BigInteger;

public class DtoPriceType implements IDtoObject {
    private Integer id;

    private String name;

    private BigInteger durationInMinutes;

    private boolean seasonTicket;

    public DtoPriceType() {
    }

    public DtoPriceType(Integer id, String name, BigInteger durationInMinutes, boolean seasonTicket) {
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
