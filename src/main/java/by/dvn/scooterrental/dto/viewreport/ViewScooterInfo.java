package by.dvn.scooterrental.dto.viewreport;

import by.dvn.scooterrental.model.rental.ScooterStatus;

public class ViewScooterInfo {
    private String name;

    private String modelName;

    private Integer maxSpeed;

    private Integer workTime;

    private Integer maxWeight;

    private Integer manufactureYear;

    private ScooterStatus scooterStatus;

    public ViewScooterInfo(String name,
                           String modelName,
                           Integer maxSpeed,
                           Integer workTime,
                           Integer maxWeight,
                           Integer manufactureYear,
                           ScooterStatus scooterStatus) {
        this.name = name;
        this.modelName = modelName;
        this.maxSpeed = maxSpeed;
        this.workTime = workTime;
        this.maxWeight = maxWeight;
        this.manufactureYear = manufactureYear;
        this.scooterStatus = scooterStatus;
    }

    public String getName() {
        return name;
    }

    public String getModelName() {
        return modelName;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public Integer getWorkTime() {
        return workTime;
    }

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public ScooterStatus getScooterStatus() {
        return scooterStatus;
    }

}
