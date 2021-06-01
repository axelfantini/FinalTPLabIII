package models;

import enums.RoleEnum;
import interfaces.IBooking;

import java.util.List;

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

    public void addBooking (Booking booking){
        this.bookings.add(booking);
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
}
