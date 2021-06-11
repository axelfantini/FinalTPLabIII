package requests;

import enums.RoomStatusEnum;
import models.RoomType;

public class SetRoomRequest {
    private RoomStatusEnum status;
    private String statusReason;
    private RoomType roomType;
    private Integer roomNum;

    public SetRoomRequest(Integer roomNum, RoomStatusEnum status, String statusReason,RoomType roomType) {
        this.status = status;
        this.statusReason = statusReason;
        this.roomType=roomType;
        this.roomNum = roomNum;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public RoomStatusEnum getStatus() { return status; }

    public String getStatusReason() {
        return statusReason;
    }

    public RoomType getRoomType() { return roomType; }
}
