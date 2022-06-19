package by.dvn.scooterrental.model.rental;

public enum ScooterStatus {
    AVAILABLE(0),
    RENTAL(1),
    REPAIR(2);

    private final Integer value;

    private ScooterStatus(Integer value) {
        this.value = value;
    }

    public static ScooterStatus valueOf(Integer value) {
        for (ScooterStatus scooterStatus : values()) {
            if (scooterStatus.equals(value)) {
                return scooterStatus;
            }
        }
        return null;
    }

    public Integer getValue() {
        return this.value;
    }

}
