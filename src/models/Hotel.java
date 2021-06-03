package models;

import enums.ErrorEnum;
import enums.RoomStatusEnum;
import requests.CreateBookingRequest;
import requests.CreateRoomRequest;
import requests.CreateUserRequest;
import requests.SetUserRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Hotel {
    private String name;
    private String address;
    private Integer stars;
    private List<User> users = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<RoomType> roomTypes = new ArrayList<>();

    public Hotel(String name, String address, Integer stars) {
        this.name = name;
        this.address = address;
        this.stars = stars;
    }

    public ErrorResponse<Room> createRoom(CreateRoomRequest values)
    {
        ErrorResponse<Room> errorResponse = new ErrorResponse<>();
        if(!rooms.stream().anyMatch(r -> r.getRoomNum().equals(values.getRoomNum())))
        {
            Room room = new Room(
                    values.getRoomNum(),
                    values.getStatus(),
                    values.getStatusReason()
            );
            rooms.add(room);
            errorResponse.setSuccess(true);
            errorResponse.setBody(room);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.ROOM_WITH_SAME_NUM);
        }
        return errorResponse;

    }

    public ErrorResponse<Booking> createBooking (CreateBookingRequest values)
    {
        ErrorResponse<Booking> errorResponse = new ErrorResponse<>();
        if((!bookings.stream().anyMatch(b -> b.getRoomId().equals(values.getRoomId()))) &&
                ((!bookings.stream().anyMatch(b -> b.getStartDate().isAfter(values.getExpectedFinishDate())))
                        || (!bookings.stream().anyMatch(b -> b.getExpectedFinishDate().isBefore(values.getStartDate()))))){
            Booking booking = new Booking(
                    values.getStartDate(),
                    values.getFinishDate(),
                    
            )
        }
        return errorResponse;
    }

    public void createRoomsRange(List<CreateRoomRequest> _rooms)
    {
        _rooms.forEach(r -> this.createRoom(r));
    }

    public ErrorResponse<User> createUser(CreateUserRequest values)
    {
        ErrorResponse<User> errorResponse = new ErrorResponse();
        if(!users.stream().anyMatch(e -> e.getDni().equals(values.getDni())))
        {
            User user = new User(
                    values.getName(),
                    values.getDni(),
                    values.getCountry(),
                    values.getAddress(),
                    values.getPassword()
            );
            users.add(user);
            errorResponse.setSuccess(true);
            errorResponse.setBody(user);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.USER_WITH_SAME_DNI);
        }
        return errorResponse;
    }

    public ErrorResponse<User> editUser(SetUserRequest values)
    {
        ErrorResponse<User> errorResponse = new ErrorResponse();
        User user = users.stream().filter(u -> u.getDni().equals(values.getDni())).findFirst().orElse(null);
        if(user != null)
        {
            user.setValues(values);
            errorResponse.setSuccess(true);
            errorResponse.setBody(user);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.USER_WITH_SAME_DNI);
        }
        return errorResponse;
    }

    public ErrorResponse<User> getUser(String dni)
    {
        ErrorResponse<User> errorResponse = new ErrorResponse();
        User user = users.stream().filter(u -> u.getDni().equals(dni)).findFirst().orElse(null);
        if(user != null)
        {
            errorResponse.setSuccess(true);
            errorResponse.setBody(user);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.USER_WITH_SAME_DNI);
        }
        return errorResponse;
    }

    public ErrorResponse<User> deleteUser(String dni)
    {
        ErrorResponse<User> errorResponse = new ErrorResponse();
        User user = users.stream().filter(u -> u.getDni().equals(dni)).findFirst().orElse(null);
        if(user != null)
        {
            user.setLogicalDelete(true);
            errorResponse.setSuccess(true);
            errorResponse.setBody(user);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.USER_WITH_SAME_DNI);
        }
        return errorResponse;
    }

    public void createUserRange(List<CreateUserRequest> users)
    {
        users.forEach(u -> this.createUser(u));
    }

    public void createBooking(String dni, Integer roomNum, Booking booking)
    {
        User user = users.stream().filter(c -> c.getDni().equals(dni)).findFirst().orElse(null);
        Room room = rooms.stream().filter(r -> r.getRoomNum().equals(roomNum)).findFirst().orElse(null);
        if(user != null && room != null && booking.getStartDate().isBefore(booking.getFinishDate()))
        {
            booking.setRoomId(room.getRoomNum());
            bookings.add(booking);
            room.addBooking(booking);
            user.addBooking(booking);
            room.setStatus(RoomStatusEnum.OCCUPIED, "OCCUPIED");
        }
    }

    public Boolean userLogin(String dni, String password)
    {
        Boolean response = false;
        User user = users.stream().filter(e -> e.getDni().equals(dni)).findFirst().orElse(null);
        if(user != null)
        {
            response = user.checkPassword(password);
        }
        return response;
    }

    public void checkout(String dni, Integer roomId)
    {
        User user = users.stream().filter(c -> c.getDni().equals(dni)).findFirst().orElse(null);
        if(user != null)
        {
            Booking booking = user.getBookingByRoomId(roomId);
            if(booking != null)
            {
                booking.finish();
                Room room = rooms.stream().filter(c -> c.getRoomNum().equals(roomId)).findFirst().orElse(null);
                if(room != null)
                    room.setStatus(RoomStatusEnum.UNOCCUPIED, "UNOCCUPIED");
            }
        }
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", stars=" + stars +
                '}';
    }
}
