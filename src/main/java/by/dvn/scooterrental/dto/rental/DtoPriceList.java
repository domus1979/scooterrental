package by.dvn.scooterrental.dto.rental;

import by.dvn.scooterrental.dto.IDtoObject;

import java.math.BigDecimal;

public class DtoPriceList implements IDtoObject {
    private Integer id;

    private DtoScooterModel scooterModel;

    private DtoPriceType priceType;

    private BigDecimal price;

    public DtoPriceList() {
    }

    public DtoPriceList(Integer id, DtoScooterModel scooterModel, DtoPriceType priceType, BigDecimal price) {
        this.id = id;
        this.scooterModel = scooterModel;
        this.priceType = priceType;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public DtoScooterModel getScooterModel() {
        return scooterModel;
    }

    public DtoPriceType getPriceType() {
        return priceType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setScooterModel(DtoScooterModel scooterModel) {
        this.scooterModel = scooterModel;
    }

    public void setPriceType(DtoPriceType priceType) {
        this.priceType = priceType;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
