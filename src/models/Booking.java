package models;

import java.time.LocalDate;
import java.util.UUID;

public class Booking {
    private UUID id;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Boolean lateCheckout;
    private Boolean canceled;
}
