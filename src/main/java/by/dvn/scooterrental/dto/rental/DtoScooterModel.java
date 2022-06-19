package by.dvn.scooterrental.dto.rental;

import by.dvn.scooterrental.dto.IDtoObject;

public class DtoScooterModel implements IDtoObject {
    private Integer id;

    private String name;

    private Integer maxSpeed;

    private Integer workTime;

    private Integer maxWeight;

    public DtoScooterModel() {
    }

    public DtoScooterModel(Integer id, String name, Integer maxSpeed, Integer workTime, Integer maxWeight) {
        this.id = id;
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.workTime = workTime;
        this.maxWeight = maxWeight;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

}
