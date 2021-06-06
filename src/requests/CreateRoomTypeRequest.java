package requests;

public class CreateRoomTypeRequest {
    private String name;
    private int capacity;
    private Double price;

    public CreateRoomTypeRequest(String name, int capacity, Double price) {
        this.name = name;
        this.capacity = capacity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Double getPrice() {
        return price;
    }
}
