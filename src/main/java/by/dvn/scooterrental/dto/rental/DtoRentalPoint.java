package by.dvn.scooterrental.dto.rental;

import by.dvn.scooterrental.dto.IDtoObject;

public class DtoRentalPoint implements IDtoObject {
    private Integer id;

    private String name;

    private DtoRentalPoint parent;

    public DtoRentalPoint() {
    }

    public DtoRentalPoint(Integer id, String name, DtoRentalPoint parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DtoRentalPoint getParent() {
        return parent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(DtoRentalPoint parent) {
        this.parent = parent;
    }

}
