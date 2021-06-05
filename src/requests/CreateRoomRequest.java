package requests;

import enums.RoomStatusEnum;

public class CreateRoomRequest {

    private Integer roomNum;
    private RoomStatusEnum status;
    private String statusReason;

    public CreateRoomRequest(Integer roomNum, RoomStatusEnum status, String statusReason) {
        this.roomNum = roomNum;
        this.status = status;
        this.statusReason = statusReason;
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
}
