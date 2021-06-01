package models;

import enums.RoomStatusEnum;
import interfaces.IBooking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room implements IBooking {
    private Integer roomNum;
    private RoomStatusEnum status;
    private String statusReason;
    private List<Booking> bookings = new ArrayList<>();

    public Room(Integer roomNum, RoomStatusEnum status, String statusReason) {
        this.roomNum = roomNum;
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
        return this.roomNum;
    }

    public void setStatus(RoomStatusEnum status, String statusReason) {
        this.status = status;
        this.statusReason = statusReason;
    }

    public void addBooking (Booking booking){

        this.bookings.add(booking);
    }
}
