package models;

import enums.RoomStatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
    private UUID id;
    private RoomStatusEnum status;
    private String statusReason;
    private List<Booking> bookings = new ArrayList<>();

    public Room(RoomStatusEnum status, String statusReason) {
        this.id = UUID.randomUUID();
        this.status = status;
        this.statusReason = statusReason;
    }

}
