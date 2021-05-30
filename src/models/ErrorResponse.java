package models;

import enums.ErrorEnum;

public class ErrorResponse {
    private Boolean success;
    private ErrorEnum error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ErrorEnum getError() {
        return error;
    }

    public void setError(ErrorEnum error) {
        this.error = error;
    }
}
