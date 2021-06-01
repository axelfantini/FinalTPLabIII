package models;

import java.time.LocalDate;
import java.util.UUID;

public class Booking {
    private UUID id;
    private LocalDate startDate;
    private LocalDate expectedFinishDate;
    private LocalDate finishDate;
    private Boolean lateCheckout;
    private Boolean canceled;
    private Integer roomId;

    public Booking(LocalDate startDate, LocalDate expectedFinishDate, Boolean lateCheckout) {
        this.startDate = startDate;
        this.expectedFinishDate = expectedFinishDate;
        this.lateCheckout = lateCheckout;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

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

    public void finish()
    {
        this.canceled = true;
        this.finishDate = LocalDate.now();
    }

}
