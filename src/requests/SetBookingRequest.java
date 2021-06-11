package requests;

import enums.BedsEnum;

import java.time.LocalDate;
import java.util.UUID;

public class SetBookingRequest {
    private UUID id;
    private LocalDate startDate;
    private LocalDate expectedFinishDate;
    private Boolean lateCheckout;
    private Integer roomId;
    private BedsEnum bedTypes;

    public SetBookingRequest(LocalDate startDate, LocalDate expectedFinishDate, Boolean lateCheckout,
                             Integer roomId, BedsEnum bedTypes) {
        this.startDate = startDate;
        this.expectedFinishDate = expectedFinishDate;
        this.lateCheckout = lateCheckout;
        this.bedTypes = bedTypes;
        this.roomId = roomId;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public LocalDate getExpectedFinishDate()
    {
        return expectedFinishDate;
    }

    public Boolean getLateCheckout()
    {
        return lateCheckout;
    }

    public Integer getRoomId()
    {
        return roomId;
    }

    public BedsEnum getBedTypes()
    {
        return bedTypes;
    }

}
