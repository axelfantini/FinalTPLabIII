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
    private UUID roomId;

    public Booking(LocalDate startDate, LocalDate expectedFinishDate, Boolean lateCheckout) {
        this.startDate = startDate;
        this.expectedFinishDate = expectedFinishDate;
        this.lateCheckout = lateCheckout;
        this.id = UUID.randomUUID();
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public UUID getRoomId() {
        return this.roomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void finish()
    {
        this.canceled = true;
        this.finishDate = LocalDate.now();
    }

}
