package models;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<Booking> bookings = new ArrayList<>();

    public Customer(String name, String dni, String country, String address, String password) {
        super(name, dni, country, address, password);
    }

    public void addBooking (Booking booking){
        this.bookings.add(booking);
    }
}
