package interfaces;

import models.Booking;
import models.ErrorResponse;

public interface IBooking {
    Booking addBooking();
    Booking editBooking();
    Booking getBooking();
    Booking deleteBooking();

}
