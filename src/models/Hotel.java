package models;

import enums.ErrorEnum;
import enums.RoleEnum;
import enums.RoomStatusEnum;
import requests.*;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Integer getStars() {
        return stars;
    }

    public String getName() {
        return name;
    }

    public ErrorResponse<RoomType> createRoomType(CreateRoomTypeRequest values)
    {
        ErrorResponse<RoomType> errorResponse = new ErrorResponse();
        RoomType roomType = new RoomType(
                values.getName(),
                values.getCapacity(),
                values.getPrice()
        );
        roomTypes.add(roomType);
        errorResponse.setSuccess(true);
        errorResponse.setBody(roomType);
        return errorResponse;
    }

    public ErrorResponse<RoomType> getRoomType(UUID roomTypeId)
    {
        ErrorResponse<RoomType> errorResponse = new ErrorResponse();
        RoomType roomType = roomTypes.stream().filter(r -> r.getId().equals(roomTypeId)).findFirst().orElse(null);
        if(roomType != null)
        {
            errorResponse.setSuccess(true);
            errorResponse.setBody(roomType);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.ROOMTYPE_NOT_FOUND);
        }
        return errorResponse;
    }

    public ErrorResponse<RoomType> editRoomType(SetRoomTypeRequest values)
    {
        ErrorResponse<RoomType> errorResponse = new ErrorResponse();
        RoomType roomType = roomTypes.stream().filter(r -> r.getId().equals(values.getId())).findFirst().orElse(null);
        if(roomType != null)
        {
            roomType.setValues(values);
            errorResponse.setSuccess(true);
            errorResponse.setBody(roomType);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.ROOMTYPE_NOT_FOUND);
        }
        return errorResponse;
    }

    public ErrorResponse<RoomType> deleteRoomType(UUID roomTypeId)
    {
        ErrorResponse<RoomType> errorResponse = new ErrorResponse();
        RoomType roomType = roomTypes.stream().filter(r -> r.getId().equals(roomTypeId)).findFirst().orElse(null);
        if(roomType != null)
        {
            roomType.setLogicalDelete(true);
            errorResponse.setSuccess(true);
            errorResponse.setBody(roomType);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.ROOMTYPE_NOT_FOUND);
        }
        return errorResponse;
    }

    public ErrorResponse<Room> createRoom(CreateRoomRequest values)
    {
        ErrorResponse<Room> errorResponse = new ErrorResponse<>();
        if(!rooms.stream().anyMatch(r -> r.getRoomNum().equals(values.getRoomNum())))
        {
            Room room = new Room(
                    values.getRoomNum(),
                    values.getStatus(),
                    values.getStatusReason(),
                    values.getRoomType()
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
        User user = users.stream().filter(c -> c.getDni().equals(values.getDni())).findFirst().orElse(null);
        Room room = rooms.stream().filter(r -> r.getRoomNum().equals(values.getRoomId())).findFirst().orElse(null);
        ErrorResponse<Booking> errorResponse = new ErrorResponse<>();
        if((!bookings.stream().anyMatch(b -> b.getRoomId().equals(values.getRoomId()))) &&
                ((!bookings.stream().anyMatch(b -> b.getStartDate().isAfter(values.getExpectedFinishDate())))
                        || (!bookings.stream().anyMatch(b -> b.getExpectedFinishDate().isBefore(values.getStartDate())))))
        {
            Booking booking = new Booking(
                    values.getStartDate(),
                    values.getExpectedFinishDate(),
                    values.getLateCheckout(),
                    values.getRoomPrice(),
                    values.getBedTypes(),
                    values.getRoomId()
            );
            bookings.add(booking);
            room.addBooking(booking);
            user.addBooking(booking);
            room.setStatus(RoomStatusEnum.OCCUPIED,"OCCUPIED");
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
                    values.getPassword(),
                    values.getRole()
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

    public ErrorResponse<User> setUserRole(String dni, RoleEnum role)
    {
        ErrorResponse<User> errorResponse = new ErrorResponse();
        User user = users.stream().filter(u -> u.getDni().equals(dni)).findFirst().orElse(null);
        if(user != null)
        {
            user.setRole(role);
            errorResponse.setSuccess(true);
            errorResponse.setBody(user);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.USER_NOT_FOUND);
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
            errorResponse.setError(ErrorEnum.USER_NOT_FOUND);
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
            errorResponse.setError(ErrorEnum.USER_NOT_FOUND);
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
            errorResponse.setError(ErrorEnum.USER_NOT_FOUND);
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
                booking.setTotalPrice(booking.getTotalPrice());
                Room room = rooms.stream().filter(c -> c.getRoomNum().equals(roomId)).findFirst().orElse(null);
                if(room != null)
                    room.setStatus(RoomStatusEnum.UNOCCUPIED, "UNOCCUPIED");
            }
        }
    }

    public ErrorResponse<Room> deleteRoom(Integer roomNum)
    {
        ErrorResponse<Room> errorResponse = new ErrorResponse<>();
        Room room = rooms.stream().filter(r -> r.getRoomNum().equals(roomNum)).findFirst().orElse(null);
        if (room != null) {
            room.setLogicalDelete(true);
            errorResponse.setSuccess(true);
            errorResponse.setBody(room);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.ROOM_NOT_FOUND);
        }
        return errorResponse;
    }

    public List<Booking> getBookings(GetBookingRequest request)
    {
        List<Booking> bookingList = bookings;
        if (request.getFinished() != null) {
            if (request.getFinished()) {
                bookingList = bookingList.stream().filter(b -> b.getFinished().equals(true)).collect(Collectors.toList());
            } else if (!request.getFinished()) {
                bookingList = bookingList.stream().filter(b -> b.getFinished().equals(true)).collect(Collectors.toList());
            }
        }
        if (request.getRoomNum()!=null) {
            bookingList = bookingList.stream().filter(b -> b.getRoomId().equals(request.getRoomNum())).collect(Collectors.toList());
        }
        return bookingList;
    }

    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        List<Room> availableRooms = null;
        rooms.forEach(r -> {
            List<Booking> roomBookings = r.getBookings();
            if (!(roomBookings.stream().anyMatch(b -> (startDate.isBefore(b.getExpectedFinishDate())&& startDate.isAfter(b.getStartDate())
                    && endDate.isBefore(b.getExpectedFinishDate())&& endDate.isAfter(b.getStartDate()))
                        || (b.getStartDate().isBefore(endDate)&& b.getStartDate().isAfter(startDate)
                            && b.getExpectedFinishDate().isBefore(endDate)&& b.getExpectedFinishDate().isAfter(startDate)))))
                        availableRooms.add(r);
        });
        return availableRooms;
    }

    public List<User> getUsers(GetUsersRequest request) {
        List<User> userList = users;
        if (request.getSearch() != null) {
            String search = request.getSearch().toUpperCase();
            userList = userList.stream().filter(u -> u.getDni().toUpperCase().contains(search) ||
                    u.getName().toUpperCase().contains(search)).collect(Collectors.toList());
        }
        if (request.getRole() != null) {
            userList = userList.stream().filter(u -> u.getRole().equals(request.getRole())).collect(Collectors.toList());
        }
        return userList;
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
