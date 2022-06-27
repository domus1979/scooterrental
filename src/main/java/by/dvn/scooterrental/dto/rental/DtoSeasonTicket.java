package by.dvn.scooterrental.dto.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.dto.account.DtoUser;

import java.time.LocalDate;

public class DtoSeasonTicket implements IDtoObject {
    private Integer id;

    private LocalDate startDate;

    private DtoPriceType priceType;

    private DtoUser user;

    public DtoSeasonTicket() {
    }

    public DtoSeasonTicket(Integer id, LocalDate startDate, DtoPriceType priceType, DtoUser user) {
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

    public DtoPriceType getPriceType() {
        return priceType;
    }

    public DtoUser getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setPriceType(DtoPriceType priceType) {
        this.priceType = priceType;
    }

    public void setUser(DtoUser user) {
        this.user = user;
    }

}
