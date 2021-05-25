package models;

import java.time.LocalDate;
import java.util.UUID;

public class Booking {
    private UUID id;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Boolean lateCheckout;
    private Boolean canceled;

    public Booking(LocalDate startDate, LocalDate finishDate, Boolean lateCheckout) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.lateCheckout = lateCheckout;
        this.id = UUID.randomUUID();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }
}
