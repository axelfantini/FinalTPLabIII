package enums;

public enum ErrorEnum {
    USER_WITH_SAME_DNI,
    ROOM_WITH_SAME_NUM;

    public String getFancyError()
    {
        String response = "";
        switch(this)
        {
            case USER_WITH_SAME_DNI:
                response = "Ya existe un usuario con ese DNI";
                break;
            case ROOM_WITH_SAME_NUM:
                response = "Ya existe una habitacion con ese numero";
                break;
        }
        return response;
    }
}
