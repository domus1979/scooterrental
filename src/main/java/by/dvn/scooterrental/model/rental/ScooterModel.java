package by.dvn.scooterrental.model.rental;

import by.dvn.scooterrental.model.IModelObject;

import javax.persistence.*;

@Entity
@Table(name = "scooter_models")
public class ScooterModel implements IModelObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_speed")
    private Integer maxSpeed;

    @Column(name = "work_time")
    private Integer workTime;

    @Column(name = "max_weight")
    private Integer maxWeight;

    public ScooterModel() {
    }

    public ScooterModel(Integer id, String name, Integer maxSpeed, Integer workTime, Integer maxWeight) {
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
