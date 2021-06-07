package models;

import requests.SetBookingRequest;
import enums.BedsEnum;

import java.time.LocalDate;
import java.util.UUID;

public class Booking extends BaseObject<UUID> {
    private LocalDate startDate;
    private LocalDate expectedFinishDate;
    private LocalDate finishDate;
    private Boolean lateCheckout;
    private Boolean canceled;
    private Integer roomId;
    private Double roomPrice;
    private BedsEnum bedTypes;
    private Double extraConsumption = 0.0;
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

    public void setExtraConsumption(Double extraConsumption) {
        this.extraConsumption = extraConsumption;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void finish()
    {
        this.canceled = true;
        this.finishDate = LocalDate.now();
    }

    public void setValues(SetBookingRequest values)
    {
        this.startDate = values.getStartDate();
        this.expectedFinishDate = values.getExpectedFinishDate();
        this.lateCheckout = values.getLateCheckout();
        this.roomPrice = values.getRoomPrice();
        this.bedTypes = values.getBedTypes();
    }
}
