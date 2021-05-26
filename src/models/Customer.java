package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer extends User {
    private List<Booking> bookings = new ArrayList<>();

    public Customer(String name, String dni, String country, String address, String password) {
        super(name, dni, country, address, password);
    }

    public void addBooking (Booking booking){
        this.bookings.add(booking);
    }

    public Booking getBookingByRoomId(Integer roomId)
    {
        return bookings.stream().filter(b -> b.getRoomId().equals(roomId)).findFirst().orElse(null);
    }
}
