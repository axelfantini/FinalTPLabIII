package models;

import enums.RoomStatusEnum;
import interfaces.IBooking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room extends BaseObject<Integer> implements IBooking {
    private RoomStatusEnum status;
    private String statusReason;
    private List<Booking> bookings = new ArrayList<>();
    private RoomType roomType;

    public Room(Integer roomNum, RoomStatusEnum status, String statusReason) {
        this.id = roomNum;
        this.status = status;
        this.statusReason = statusReason;
    }

    public RoomStatusEnum getStatus() {
        return status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public Integer getRoomNum() {
        return this.id;
    }

    public void setStatus(RoomStatusEnum status, String statusReason) {
        this.status = status;
        this.statusReason = statusReason;
    }

    @Override
    public Booking addBooking(Booking booking) {
        this.bookings.add(booking);
        return booking;
    }

    @Override
    public Booking editBooking(UUID id, Booking values) {
        Booking booking = bookings.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
        if (booking != null)
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
        Booking booking = bookings.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
        if (booking != null) {
            response = true;
            booking.setLogicalDelete(true);
        }
        return response;
    }
}
