package by.dvn.scooterrental.dto.rental;

import by.dvn.scooterrental.dto.IDtoObject;

public class DtoRentalPoint implements IDtoObject {
    private Integer id;

    private String name;

    private Integer parentRentalPointId;

    public DtoRentalPoint() {
    }

    public DtoRentalPoint(Integer id, String name, Integer parentRentalPointId) {
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
