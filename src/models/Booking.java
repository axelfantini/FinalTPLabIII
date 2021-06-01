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

    public Booking() {}

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

    public void finish()
    {
        this.canceled = true;
        this.finishDate = LocalDate.now();
    }
}
