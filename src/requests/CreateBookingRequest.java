package requests;

import enums.BedsEnum;

import java.time.LocalDate;

public class CreateBookingRequest {
    private LocalDate startDate;
    private LocalDate expectedFinishDate;
    private LocalDate finishDate;
    private Boolean lateCheckout;
    private Boolean canceled;
    private Integer roomId;
    private String dni;
    private Double roomPrice;
    private BedsEnum bedTypes;
    private Double extraConsumption = 0.0;


    public CreateBookingRequest(LocalDate startDate, LocalDate expectedFinishDate, LocalDate finishDate, Boolean lateCheckout, Boolean canceled, Integer roomId, String dni, Double roomPrice, BedsEnum bedTypes) {
        this.startDate = startDate;
        this.expectedFinishDate = expectedFinishDate;
        this.finishDate = finishDate;
        this.lateCheckout = lateCheckout;
        this.canceled = canceled;
        this.roomId = roomId;
        this.dni = dni;
        this.roomPrice = roomPrice;
        this.bedTypes = bedTypes;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpectedFinishDate() {
        return expectedFinishDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public Boolean getLateCheckout() {
        return lateCheckout;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public String getDni() {
        return dni;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public BedsEnum getBedTypes() {
        return bedTypes;
    }

    public Double getExtraConsumption() {
        return extraConsumption;
    }
}
