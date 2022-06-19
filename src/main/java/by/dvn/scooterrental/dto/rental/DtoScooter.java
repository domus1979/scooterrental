package by.dvn.scooterrental.dto.rental;

import by.dvn.scooterrental.dto.IDtoObject;
import by.dvn.scooterrental.model.rental.ScooterStatus;

public class DtoScooter implements IDtoObject {
    private Integer id;

    private String name;

    private DtoScooterModel scooterModel;

    private Integer manufactureYear;

    private ScooterStatus scooterStatus;

    private DtoRentalPoint rentalPoint;

    public DtoScooter() {
    }

    public DtoScooter(Integer id,
                      String name,
                      DtoScooterModel scooterModel,
                      Integer manufactureYear,
                      DtoRentalPoint rentalPoint) {
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

    public DtoScooterModel getScooterModel() {
        return scooterModel;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public ScooterStatus getScooterStatus() {
        return scooterStatus;
    }

    public DtoRentalPoint getRentalPoint() {
        return rentalPoint;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScooterModel(DtoScooterModel scooterModel) {
        this.scooterModel = scooterModel;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public void setScooterStatus(ScooterStatus scooterStatus) {
        this.scooterStatus = scooterStatus;
    }

    public void setRentalPoint(DtoRentalPoint rentalPoint) {
        this.rentalPoint = rentalPoint;
    }

}
