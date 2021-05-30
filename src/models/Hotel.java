package models;

import enums.ErrorEnum;
import enums.RoomStatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Hotel {
    private String name;
    private String address;
    private Integer stars;
    private List<Customer> customers = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();

    public Hotel(String name, String address, Integer stars) {
        this.name = name;
        this.address = address;
        this.stars = stars;
    }

    public ErrorResponse createRoom(Room room)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        if(!rooms.stream().anyMatch(r -> r.getRoomNum().equals(room.getRoomNum())))
        {
            rooms.add(room);
            errorResponse.setSuccess(true);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.ROOM_WITH_SAME_NUM);
        }
        return errorResponse;

    }

    public void createRoomsRange(List<Room> _rooms)
    {
        _rooms.forEach(r -> this.createRoom(r));
    }

    public ErrorResponse createUser(User user)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        if(!employees.stream().anyMatch(e -> e.getDni().equals(user.getDni()))
                && !customers.stream().anyMatch(c -> c.getDni().equals(user.getDni())))
        {
            if(user instanceof Employee)
                employees.add((Employee)user);
            else if(user instanceof Customer)
                customers.add((Customer)user);
            errorResponse.setSuccess(true);
        }
        else
        {
            errorResponse.setSuccess(false);
            errorResponse.setError(ErrorEnum.USER_WITH_SAME_DNI);
        }
        return errorResponse;
    }

    public void createUserRange(List<User> users)
    {
        users.forEach(u -> this.createUser(u));
    }

    public void createBooking(String dni, Integer roomNum, Booking booking)
    {
        Customer customer = customers.stream().filter(c -> c.getDni().equals(dni)).findFirst().orElse(null);
        Room room = rooms.stream().filter(r -> r.getRoomNum().equals(roomNum)).findFirst().orElse(null);
        if(customer != null && room != null && booking.getStartDate().isBefore(booking.getFinishDate()))
        {
            booking.setRoomId(room.getRoomNum());
            bookings.add(booking);
            room.addBooking(booking);
            customer.addBooking(booking);
            room.setStatus(RoomStatusEnum.OCCUPIED, "OCCUPIED");
        }
    }

    public Boolean employeeLogin(String dni, String password)
    {
        Boolean response = false;
        Employee employee = employees.stream().filter(e -> e.getDni().equals(dni)).findFirst().orElse(null);
        if(employee != null)
        {
            response = employee.checkPassword(password);
        }
        return response;
    }

    public Boolean customerLogin(String dni, String password)
    {
        Boolean response = false;
        Customer customer = customers.stream().filter(c -> c.getDni().equals(dni)).findFirst().orElse(null);
        if(customer != null)
        {
            response = customer.checkPassword(password);
        }
        return response;
    }

    public void finishBooking(String dni, Integer roomId)
    {
        Customer customer = customers.stream().filter(c -> c.getDni().equals(dni)).findFirst().orElse(null);
        if(customer != null)
        {
            Booking booking = customer.getBookingByRoomId(roomId);
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
