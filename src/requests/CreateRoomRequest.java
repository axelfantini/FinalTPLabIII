package requests;

import enums.RoomStatusEnum;
import models.RoomType;

public class CreateRoomRequest {
    private Integer roomNum;
    private RoomStatusEnum status;
    private String statusReason;
    private RoomType roomType;

    public CreateRoomRequest(Integer roomNum, RoomStatusEnum status, String statusReason, RoomType roomType) {
        this.roomNum = roomNum;
        this.status = status;
        this.statusReason = statusReason;
        this.roomType = roomType;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public RoomStatusEnum getStatus() {
        return status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public  RoomType getRoomType() {
        return roomType;
    }
}
