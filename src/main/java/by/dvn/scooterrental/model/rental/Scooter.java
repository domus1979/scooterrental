package by.dvn.scooterrental.model.rental;

import by.dvn.scooterrental.model.IModelObject;

import javax.persistence.*;

@Entity
@Table(name = "scooters")
public class Scooter implements IModelObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "scooter_model_id")
    private ScooterModel scooterModel;

    @Column(name = "manufacture_year")
    private Integer manufactureYear;

    @Column(name = "scooter_status")
    private ScooterStatus scooterStatus;

    @ManyToOne
    @JoinColumn(name = "rental_point_id")
    private RentalPoint rentalPoint;

    public Scooter() {
    }

    public Scooter(Integer id,
                   String name,
                   ScooterModel scooterModel,
                   Integer manufactureYear,
                   RentalPoint rentalPoint) {
        this.id = id;
        this.name = name;
        this.scooterModel = scooterModel;
        this.manufactureYear = manufactureYear;
        this.rentalPoint = rentalPoint;
        this.scooterStatus = ScooterStatus.AVAILABLE;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ScooterModel getScooterModel() {
        return scooterModel;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public ScooterStatus getScooterStatus() {
        return scooterStatus;
    }

    public RentalPoint getRentalPoint() {
        return rentalPoint;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScooterModel(ScooterModel scooterModel) {
        this.scooterModel = scooterModel;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public void setScooterStatus(ScooterStatus scooterStatus) {
        this.scooterStatus = scooterStatus;
    }

    public void setRentalPoint(RentalPoint rentalPoint) {
        this.rentalPoint = rentalPoint;
    }

}
