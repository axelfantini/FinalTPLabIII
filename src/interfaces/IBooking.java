package interfaces;

import models.Booking;
import models.ErrorResponse;
import requests.SetBookingRequest;

import java.util.UUID;

public interface IBooking {
    Booking addBooking(Booking booking);
    Booking editBooking(UUID id, SetBookingRequest values);
    Booking getBooking(UUID id);
    Boolean deleteBooking(UUID id);

}
