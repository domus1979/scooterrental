package by.dvn.scooterrental.model.rental;

import by.dvn.scooterrental.model.IModelObject;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "price_list")
public class PriceList implements IModelObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "scooter_model_id")
    private ScooterModel scooterModel;

    @ManyToOne
    @JoinColumn(name = "price_type_id")
    private PriceType priceType;

    @Column(name = "price")
    private BigDecimal price;

    public PriceList() {
    }

    public PriceList(Integer id, ScooterModel scooterModel, PriceType priceType, BigDecimal price) {
        this.id = id;
        this.scooterModel = scooterModel;
        this.priceType = priceType;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public ScooterModel getScooterModel() {
        return scooterModel;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setScooterModel(ScooterModel scooterModel) {
        this.scooterModel = scooterModel;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
