package by.dvn.scooterrental.model.rental;

import by.dvn.scooterrental.model.IModelObject;

import javax.persistence.*;

@Entity
@Table(name = "rental_points")
public class RentalPoint implements IModelObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Integer parentRentalPointId;

    public RentalPoint() {
    }

    public RentalPoint(Integer id, String name, Integer parentRentalPointId) {
        this.id = id;
        this.name = name;
        this.parentRentalPointId = parentRentalPointId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getParentRentalPointId() {
        return parentRentalPointId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentRentalPointId(Integer parentRentalPointId) {
        this.parentRentalPointId = parentRentalPointId;
    }

}
