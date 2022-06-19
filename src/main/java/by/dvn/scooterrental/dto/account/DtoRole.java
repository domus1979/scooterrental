package by.dvn.scooterrental.dto.account;

import by.dvn.scooterrental.dto.IDtoObject;

public class DtoRole implements IDtoObject {
    private Integer id;

    private String name;

    public DtoRole() {
    }

    public DtoRole(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
