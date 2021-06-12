package requests;

import enums.BedsEnum;

import java.time.LocalDate;

public class CreateBookingRequest {
    private LocalDate startDate;
    private LocalDate expectedFinishDate;
    private Boolean lateCheckout;
    private Integer roomId;
    private String dni;
    private BedsEnum bedTypes;
    private Double extraConsumption = 0.0;


    public CreateBookingRequest(LocalDate startDate, LocalDate expectedFinishDate, Boolean lateCheckout, Integer roomId, String dni, BedsEnum bedTypes) {
        this.startDate = startDate;
        this.expectedFinishDate = expectedFinishDate;
        this.lateCheckout = lateCheckout;
        this.roomId = roomId;
        this.dni = dni;
        this.bedTypes = bedTypes;
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

    public Integer getRoomId() {
        return roomId;
    }

    public String getDni() {
        return dni;
    }

    public BedsEnum getBedTypes() {
        return bedTypes;
    }
}
