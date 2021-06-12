package models;

import requests.SetBookingRequest;
import enums.BedsEnum;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.UUID;

public class Booking extends BaseObject<UUID> {
    private LocalDate startDate;
    private LocalDate expectedFinishDate;
    private LocalDate finishDate;
    private Boolean lateCheckout;
    private Boolean canceled = false;
    private Boolean finished = false;
    private Integer roomId;
    private Double roomPrice;
    private BedsEnum bedTypes;
    private Double extraConsumption = 0.0;
    private Double totalPrice = 0.0;

    public Booking(LocalDate startDate, LocalDate expectedFinishDate, Boolean lateCheckout, Double roomPrice, BedsEnum bedTypes, Integer roomNum) {
        this.startDate = startDate;
        this.expectedFinishDate = expectedFinishDate;
        this.lateCheckout = lateCheckout;
        this.roomId = roomNum;
        this.id = UUID.randomUUID();
        this.roomPrice = roomPrice;
        this.bedTypes = bedTypes;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public Double getExtraConsumption() {
        return extraConsumption;
    }

    public Integer getRoomId() {
        return this.roomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpectedFinishDate() {
        return expectedFinishDate;
    }

    public Boolean getLateCheckout() {
        return lateCheckout;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public Boolean getFinished() {
        return finished;
    }

    public BedsEnum getBedTypes() {
        return bedTypes;
    }

    public void addExtraConsumption(Double extraConsumption) {
        this.extraConsumption += extraConsumption;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = roomPrice*DAYS.between(this.startDate,this.finishDate)+extraConsumption;
    }

    public void finish()
    {
        this.finishDate = LocalDate.now();
        if (this.finishDate.isBefore(this.startDate))
            this.canceled = true;
        else
            this.finished = true;
    }

    public void setValues(SetBookingRequest values)
    {
        this.startDate = values.getStartDate();
        this.expectedFinishDate = values.getExpectedFinishDate();
        this.lateCheckout = values.getLateCheckout();
        this.bedTypes = values.getBedTypes();
        this.roomId = values.getRoomId();
    }
}
