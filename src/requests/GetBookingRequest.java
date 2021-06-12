package requests;

public class GetBookingRequest {
    private Boolean finished;
    private Integer roomNum;

    public GetBookingRequest(Boolean finished, Integer roomNum) {
        this.finished = finished;
        this.roomNum = roomNum;
    }

    public GetBookingRequest() {
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public Boolean getFinished() {
        return finished;
    }
}
