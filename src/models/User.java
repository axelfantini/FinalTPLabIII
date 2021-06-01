package models;

import enums.RoleEnum;
import interfaces.IBooking;

import java.util.List;
import java.util.UUID;

public class User implements IBooking {
    private String name;
    private String dni;
    private String country;
    private String address;
    private String password;
    private RoleEnum role = RoleEnum.USER;
    private List<Booking> bookings;

    public User(String name, String dni, String country, String address, String password) {
        this.name = name;
        this.dni = dni;
        this.country = country;
        this.address = address;
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getDni() {
        return this.dni;
    }

    public Boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }

    public Booking getBookingByRoomId(Integer roomId)
    {
        return bookings.stream().filter(b -> b.getRoomId().equals(roomId)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Booking addBooking(Booking booking) {
        this.bookings.add(booking);
        return booking;
    }

    @Override
    public Booking editBooking(UUID id, Booking values) {
        Booking booking = bookings.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
        if(booking != null)
        {
            booking.setStartDate(values.getStartDate());
            booking.setExpectedFinishDate(values.getExpectedFinishDate());
            booking.setLateCheckout(values.getLateCheckout());
        }
        return booking;
    }

    @Override
    public Booking getBooking(UUID id) {
        return bookings.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Boolean deleteBooking(UUID id) {
        Boolean response = false;
        Booking booking = bookings.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
        if(booking != null)
        {
            response = bookings.remove(booking);
        }
        return response;
    }
}
