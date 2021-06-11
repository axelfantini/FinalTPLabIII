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
    private Double extraConsumption;
    private Double totalPrice;

    public Booking(LocalDate startDate, LocalDate expectedFinishDate, Boolean lateCheckout, Double roomPrice, BedsEnum bedTypes, Integer roomNum) {
        this.startDate = startDate;
        this.expectedFinishDate = expectedFinishDate;
        this.lateCheckout = lateCheckout;
        this.roomId = roomNum;
        this.id = UUID.randomUUID();
        this.roomPrice = roomPrice;
        this.bedTypes = bedTypes;
    }

    public Booking() {}

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomId() {
        return this.roomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
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

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setExpectedFinishDate(LocalDate expectedFinishDate) {
        this.expectedFinishDate = expectedFinishDate;
    }

    public void setLateCheckout(Boolean lateCheckout) {
        this.lateCheckout = lateCheckout;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public BedsEnum getBedTypes() {
        return bedTypes;
    }

    public void setBedTypes(BedsEnum bedTypes) {
        this.bedTypes = bedTypes;
    }

    public Double getExtraConsumption() {
        return extraConsumption;
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
        else if (this.finishDate.equals(this.expectedFinishDate))
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
