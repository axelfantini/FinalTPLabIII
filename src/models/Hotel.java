package models;

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

    public void createRoom(Room room)
    {
        rooms.add(room);
    }

    public void createRoomsRange(List<Room> _rooms)
    {
        _rooms.forEach(r -> this.createRoom(r));
    }

    public void createEmployee(Employee employee)
    {
        if(!employees.stream().anyMatch(e -> e.getDni().equals(employee.getDni())))
        {
            employees.add(employee);
        }
    }

    public void createEmployeeRange(List<Employee> _employees)
    {
        _employees.forEach(e -> this.createEmployee(e));
    }

    public void createCustomer (Customer customer){
        if(!customers.stream().anyMatch(e -> e.getDni().equals(customer.getDni())))
        {
            customers.add(customer);
        }
    }

    public void createBooking(String dni, UUID roomId, Booking booking)
    {
        Customer customer = customers.stream().filter(c -> c.getDni().equals(dni)).findFirst().orElse(null);
        Room room = rooms.stream().filter(r -> r.getId().equals(roomId)).findFirst().orElse(null);
        if(customer != null && room != null && booking.getStartDate().isBefore(booking.getFinishDate()))
        {
            booking.setRoomId(room.getId());
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

    public void finishBooking(String dni, UUID roomId)
    {
        Customer customer = customers.stream().filter(c -> c.getDni().equals(dni)).findFirst().orElse(null);
        if(customer != null)
        {
            Booking booking = customer.getBookingByRoomId(roomId);
            if(booking != null)
            {
                booking.finish();
                Room room = rooms.stream().filter(c -> c.getId().equals(roomId)).findFirst().orElse(null);
                if(room != null)
                    room.setStatus(RoomStatusEnum.UNOCCUPIED, "UNOCCUPIED");
            }
        }
    }
}
