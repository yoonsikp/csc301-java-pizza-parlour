package a2;

import java.util.HashMap;

public abstract class Delivery {

    private String address;
    private String type;
    private int status;


    public Delivery(DeliveryFactory.Builder builder) {
        this.address = builder.getAddress();
        this.type = builder.getType();
        this.status = builder.getStatus();

    }

    public String getAddress(){
        return this.address;
    }

    public String getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public abstract String outputDeliveryDetails(Order order);

    public String toString(){
        String deliveryString = this.getType() + " to " + this.getAddress();
        return deliveryString;
    }
}
