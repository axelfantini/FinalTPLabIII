package models;

import enums.RoleEnum;
import interfaces.IBooking;
import requests.SetBookingRequest;
import requests.SetUserRequest;

import java.util.List;
import java.util.UUID;

public class User extends BaseObject<String> implements IBooking {
    private String name;
    private String country;
    private String address;
    private String password;
    private RoleEnum role = RoleEnum.USER;
    private List<Booking> bookings;

    public User(String name, String dni, String country, String address, String password) {
        this.name = name;
        this.id = dni;
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
        return this.id;
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
                ", dni='" + id + '\'' +
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
    public Booking editBooking(UUID id, SetBookingRequest values) {
        Booking booking = bookings.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
        if(booking != null)
            booking.setValues(values);
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
            booking.setLogicalDelete(true);
            response = true;
        }
        return response;
    }



    public void setValues(SetUserRequest request)
    {
        this.name = request.getName();
        this.address = request.getAddress();
        this.country = request.getCountry();
    }
}
