package by.dvn.scooterrental.dto.viewreport;

import by.dvn.scooterrental.dto.IDtoObject;

import java.util.List;

public class ViewRentalPointInfo implements IDtoObject {
    private String rentalPointFullName;

    private List<ViewScooterInfo> scooterList;

    public ViewRentalPointInfo(String rentalPointFullName, List<ViewScooterInfo> scooterList) {
        this.rentalPointFullName = rentalPointFullName;
        this.scooterList = scooterList;
    }

    public String getRentalPointFullName() {
        return rentalPointFullName;
    }

    public List<ViewScooterInfo> getScooterList() {
        return scooterList;
    }

}
