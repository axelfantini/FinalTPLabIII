package requests;

import java.util.UUID;

public class SetRoomTypeRequest {
    private UUID id;
    private String name;
    private int capacity;
    private Double price;

    public SetRoomTypeRequest(UUID id, String name, int capacity, Double price) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
    }

    public UUID getId() {
        return id;
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
