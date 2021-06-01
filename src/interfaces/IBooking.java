package interfaces;

import models.Booking;
import models.ErrorResponse;

public interface IBooking {
    Booking addBooking(Booking booking);
    Booking editBooking(UUID id, Booking values);
    Booking getBooking(UUID id);
    Boolean deleteBooking(UUID id);

}
