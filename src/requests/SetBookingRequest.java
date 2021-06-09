package requests;

import enums.BedsEnum;

import java.time.LocalDate;

public class SetBookingRequest {
    private LocalDate startDate;
    private LocalDate expectedFinishDate;
    private LocalDate finishDate;
    private Boolean lateCheckout;
    private Boolean canceled;
    private Integer roomId;
    private Double roomPrice;
    private BedsEnum bedTypes;
    private Double extraConsumption;
    private Double totalPrice;

    public SetBookingRequest(LocalDate startDate, LocalDate expectedFinishDate, LocalDate finishDate, Boolean lateCheckout,
                             Boolean canceled, Integer roomId, Double roomPrice, BedsEnum bedTypes, Double extraConsumption) {
        this.startDate = startDate;
        this.expectedFinishDate = expectedFinishDate;
        this.finishDate = finishDate;
        this.lateCheckout = lateCheckout;
        this.canceled = canceled;
        this.roomId = roomId;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public LocalDate getExpectedFinishDate()
    {
        return expectedFinishDate;
    }

    public LocalDate getFinishDate()
    {
        return finishDate;
    }

    public Boolean getLateCheckout()
    {
        return lateCheckout;
    }

    public Boolean getCanceled()
    {
        return canceled;
    }

    public Integer getRoomId()
    {
        return roomId;
    }

    public Double getRoomPrice()
    {
        return roomPrice;
    }

    public BedsEnum getBedTypes()
    {
        return bedTypes;
    }

    public Double getExtraConsumption()
    {
        return extraConsumption;
    }

    public Double getTotalPrice()
    {
        return totalPrice;
    }
}
