package requests;

import models.Booking;

import java.util.List;

public class GetBookingRequest {
    private String search;
    private Boolean finished;
    private Integer roomNum;

    public Boolean getFinished() {
        return finished;
    }

    public String getSearch() {
        return search;
    }

    public Integer getRoomNum() {
        return roomNum;
    }
}
