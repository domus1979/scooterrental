package by.dvn.scooterrental.model.rental;

public enum OrderStatus {
    OPEN(0),
    CLOSE(1);

    private final Integer value;

    private OrderStatus(Integer value) {
        this.value = value;
    }

    public static OrderStatus valueOf(Integer value) {
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.equals(value)) {
                return orderStatus;
            }
        }
        return null;
    }

    public Integer getValue() {
        return this.value;
    }

}
