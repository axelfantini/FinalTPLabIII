package requests;

import java.time.LocalDate;

public class CreateBookingRequest {
    private LocalDate startDate;
    private LocalDate expectedFinishDate;
    private LocalDate finishDate;
    private Boolean lateCheckout;
    private Boolean canceled;
    private Integer roomId;

    public CreateBookingRequest(LocalDate startDate, LocalDate expectedFinishDate, LocalDate finishDate, Boolean lateCheckout, Boolean canceled, Integer roomId) {
        this.startDate = startDate;
        this.expectedFinishDate = expectedFinishDate;
        this.finishDate = finishDate;
        this.lateCheckout = lateCheckout;
        this.canceled = canceled;
        this.roomId = roomId;
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
}
