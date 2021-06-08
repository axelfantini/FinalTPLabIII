package requests;

import models.Booking;

import java.util.List;

public class GetUserBookingRequest {
    private String search;
    private Boolean finished;

    public Boolean getFinished() {
        return finished;
    }

    public String getSearch() {
        return search;
    }
}
