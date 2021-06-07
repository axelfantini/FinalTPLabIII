package requests;

import enums.RoomStatusEnum;
import models.RoomType;

public class SetRoomRequest {
    private RoomStatusEnum status;
    private String statusReason;
    private RoomType roomType;

    public SetRoomRequest(RoomStatusEnum status, String statusReason,RoomType roomType) {
        this.status = status;
        this.statusReason = statusReason;
        this.roomType=roomType;
    }

    public RoomStatusEnum getStatus() { return status; }

    public String getStatusReason() {
        return statusReason;
    }

    public RoomType getRoomType() { return roomType; }
}
