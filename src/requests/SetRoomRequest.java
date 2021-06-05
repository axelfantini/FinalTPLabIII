package requests;

import enums.RoomStatusEnum;

public class SetRoomRequest {
    private RoomStatusEnum status;
    private String statusReason;

    public SetRoomRequest(RoomStatusEnum status, String statusReason) {
        this.status = status;
        this.statusReason = statusReason;
    }

    public RoomStatusEnum getStatus() {
        return status;
    }

    public String getStatusReason() {
        return statusReason;
    }
}
