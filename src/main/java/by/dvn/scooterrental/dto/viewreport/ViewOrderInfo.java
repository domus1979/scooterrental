package by.dvn.scooterrental.dto.viewreport;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class ViewOrderInfo {
    private Integer id;

    private String scooterName;

    private String userName;

    private String startPointFullName;

    private LocalDateTime beginTime;

    private BigInteger duration;

    private LocalDateTime endTime;

    private String finishPointFullName;

    private BigDecimal cost;

    public ViewOrderInfo(Integer id,
                         String scooterName,
                         String userName,
                         String startPointFullName,
                         LocalDateTime beginTime,
                         BigInteger duration,
                         LocalDateTime endTime,
                         String finishPointFullName,
                         BigDecimal cost) {
        this.id = id;
        this.scooterName = scooterName;
        this.userName = userName;
        this.startPointFullName = startPointFullName;
        this.beginTime = beginTime;
        this.duration = duration;
        this.endTime = endTime;
        this.finishPointFullName = finishPointFullName;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public String getScooterName() {
        return scooterName;
    }

    public String getUserName() {
        return userName;
    }

    public String getStartPointFullName() {
        return startPointFullName;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public BigInteger getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getFinishPointFullName() {
        return finishPointFullName;
    }

    public BigDecimal getCost() {
        return cost;
    }

}
