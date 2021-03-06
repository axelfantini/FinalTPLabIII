package models;

import enums.ErrorEnum;
import enums.RoleEnum;
import enums.RoomStatusEnum;
import requests.*;

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

    public List<Room> getRooms() {
        return rooms;
    }

    public List<RoomType> getRoomTypes() {
        return roomTypes;
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
        RoomType roomType = roomTypes.stream().filter(r -> r.getId().equals(roomTypeId) && !r.getLogicalDelete()).findFirst().orElse(null);
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
        RoomType roomType = roomTypes.stream().filter(r -> r.getId().equals(values.getId()) && !r.getLogicalDelete()).findFirst().orElse(null);
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
        RoomType roomType = roomTypes.stream().filter(r -> r.getId().equals(roomTypeId)  && !r.getLogicalDelete()).findFirst().orElse(null);
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

    public ErrorResponse<Room> editRoom(SetRoomRequest roomRequest)
    {
        ErrorResponse<Room> errorResponse = new ErrorResponse<>();
        Room room = rooms.stream().filter(r -> r.getRoomNum().equals(roomRequest.getRoomNum())  && !r.getLogicalDelete()).findFirst().orElse(null);
        if(room != null)
        {
            room.setValues(roomRequest);
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

    public ErrorResponse<Room> getRoom(Integer roomNum)
    {
        ErrorResponse<Room> errorResponse = new ErrorResponse<>();
        Room room = rooms.stream().filter(r -> r.getRoomNum().equals(roomNum)  && !r.getLogicalDelete()).findFirst().orElse(null);
        if(room != null)
        {
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



    public ErrorResponse<Booking> createBooking (CreateBookingRequest values)
    {
        User user = users.stream().filter(c -> c.getDni().equals(values.getDni()) && !c.getLogicalDelete()).findFirst().orElse(null);
        Room room = rooms.stream().filter(r -> r.getRoomNum().equals(values.getRoomId()) && !r.getLogicalDelete()).findFirst().orElse(null);
        ErrorResponse<Booking> errorResponse = new ErrorResponse<>();
            if (isValidDate(room.getBookings(), values.getStartDate(), values.getExpectedFinishDate()))
            {
                Booking booking = new Booking(
                        values.getStartDate(),
                        values.getExpectedFinishDate(),
                        values.getLateCheckout(),
                        room.getRoomType().getPrice(),
                        values.getBedTypes(),
                        values.getRoomId()
                );
                bookings.add(booking);
                room.addBooking(booking);
                user.addBooking(booking);
                room.setStatus(RoomStatusEnum.OCCUPIED,"OCCUPIED");
                errorResponse.setSuccess(true);
                errorResponse.setBody(booking);
            }
            else
            {
                errorResponse.setSuccess(false);
                errorResponse.setError(ErrorEnum.OCCUPIED_ROOM);
            }
        return errorResponse;
    }

    public ErrorResponse addConsumption(UUID bookingId, Double consumption)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        Booking booking = bookings.stream().filter(b -> b.getId().equals(bookingId) && !b.logicalDelete).findFirst().orElse(null);
        if(booking != null && LocalDate.now().isAfter(booking.getStartDate()) && booking.getFinished().equals(false))
        {
            booking.addExtraConsumption(consumption);
            errorResponse.setSuccess(true);
        }
        else
        {
            errorResponse.setSuccess(false);
            if(booking == null)
                errorResponse.setError(ErrorEnum.BOOKING_NOT_FOUND);
            else
                errorResponse.setError(ErrorEnum.BOOKING_DATE_INVALID);
        }
        return errorResponse;
    }

    public ErrorResponse<Booking> setBooking(SetBookingRequest request)
    {
        ErrorResponse<Booking> errorResponse = new ErrorResponse<>();
        Room room = rooms.stream().filter(r -> r.getRoomNum() == request.getRoomId() && !r.logicalDelete).findFirst().orElse(null);
        Booking booking = bookings.stream().filter(b -> b.getId().equals(request.getId()) && !b.getLogicalDelete()).findFirst().orElse(null);
        if(booking!=null && room != null) {
            if (isValidDate(room.getBookings(), request.getStartDate(), request.getExpectedFinishDate()))
            {
                booking.setValues(request);
                errorResponse.setSuccess(true);
                errorResponse.setBody(booking);
            }
            else
            {
                errorResponse.setSuccess(false);
                errorResponse.setError(ErrorEnum.BOOKING_DATE_INVALID);
            }
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.BOOKING_NOT_FOUND);
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
        User user = users.stream().filter(u -> u.getDni().equals(dni) && !u.getLogicalDelete()).findFirst().orElse(null);
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
        User user = users.stream().filter(u -> u.getDni().equals(values.getDni()) && !u.getLogicalDelete()).findFirst().orElse(null);
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
        User user = users.stream().filter(u -> u.getDni().equals(dni) && !u.getLogicalDelete()).findFirst().orElse(null);
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
        User user = users.stream().filter(u -> u.getDni().equals(dni) && !u.getLogicalDelete()).findFirst().orElse(null);
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

    public ErrorResponse<Booking> getBooking(UUID bookingId)
    {
        ErrorResponse<Booking> errorResponse = new ErrorResponse<>();
        Booking booking = bookings.stream().filter(b -> b.getId().equals(bookingId) && !b.getLogicalDelete()).findFirst().orElse(null);
        if(booking != null)
        {
            errorResponse.setSuccess(true);
            errorResponse.setBody(booking);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.ROOM_NOT_FOUND);
        }
        return errorResponse;
    }

    public Boolean userLogin(String dni, String password)
    {
        Boolean response = false;
        User user = users.stream().filter(e -> e.getDni().equals(dni) && !e.getLogicalDelete()).findFirst().orElse(null);
        if(user != null)
        {
            response = user.checkPassword(password);
        }
        return response;
    }

    public ErrorResponse<Booking> checkout(UUID bookingId)
    {
        ErrorResponse<Booking> errorResponse = new ErrorResponse<>();
        Booking booking = bookings.stream().filter(b -> b.getId().equals(bookingId) && !b.getLogicalDelete()).findFirst().orElse(null);
        if(booking != null)
        {
            booking.finish();
            booking.setTotalPrice(booking.getTotalPrice());
            Room room = rooms.stream().filter(c -> c.getRoomNum().equals(booking.getRoomId()) && !c.getLogicalDelete()).findFirst().orElse(null);
            if(room != null)
                room.setStatus(RoomStatusEnum.UNOCCUPIED, "UNOCCUPIED");
            errorResponse.setSuccess(true);
            errorResponse.setBody(booking);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.BOOKING_NOT_FOUND);
        }
        return errorResponse;
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
                bookingList = bookingList.stream().filter(b -> b.getFinished().equals(true) && !b.getLogicalDelete()).collect(Collectors.toList());
            } else if (!request.getFinished()) {
                bookingList = bookingList.stream().filter(b -> b.getFinished().equals(false) && !b.getLogicalDelete()).collect(Collectors.toList());
            }
        }
        if (request.getRoomNum()!=null) {
            bookingList = bookingList.stream().filter(b -> b.getRoomId().equals(request.getRoomNum()) && !b.getLogicalDelete()).collect(Collectors.toList());
        }
        return bookingList;
    }

    private Boolean isValidDate(List<Booking> bookings, LocalDate startDate, LocalDate endDate)
    {
        Boolean response = true;
        for (Booking b : bookings) {
            List<LocalDate> needDays = new ArrayList<>();
            List<LocalDate> occupiedDays = new ArrayList<>();
            for (LocalDate i = b.getStartDate(); i.isBefore(b.getExpectedFinishDate()) || i.equals(b.getExpectedFinishDate()); i = i.plusDays(1))
                occupiedDays.add(i);
            for (LocalDate i = startDate; i.isBefore(endDate) || i.equals(endDate); i = i.plusDays(1))
                needDays.add(i);
            if (occupiedDays.stream().anyMatch(d -> d.equals(startDate) || d.equals(endDate)))
                response = false;
            if(needDays.stream().anyMatch(d -> d.equals(b.getStartDate()) || d.equals(b.getExpectedFinishDate())))
                response = false;
        }

        return response;
    }

    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
        List<Room> availableRooms = new ArrayList<>();
        rooms.forEach(r -> {
            List<Booking> roomBookings = r.getBookings().stream().filter(b -> !b.getFinished() && !b.getCanceled() && !b.logicalDelete).collect(Collectors.toList());
            if (isValidDate(roomBookings, startDate, endDate))
                availableRooms.add(r);
        });
        return availableRooms;
    }

    public List<User> getUsers(GetUsersRequest request) {
        List<User> userList = users.stream().filter(u -> !u.getLogicalDelete()).collect(Collectors.toList());
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
