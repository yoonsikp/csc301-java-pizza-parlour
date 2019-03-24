package a2;

import java.util.HashMap;

public class HouseDelivery extends Delivery {

    public HouseDelivery(Delivery.Builder builder) {
        super(builder);
    }

    public String outputDeliveryDetails(Order order){
        //as text
        StringBuilder delivDetails = new StringBuilder();
        delivDetails.append("Address: ").append(order.getDelivery().getAddress()).append("\n");
        delivDetails.append("Order Details: ").append("\n");
        delivDetails.append(order.toString()).append("\n");
        delivDetails.append("Order Number: ").append(order.getOrderID());

        return delivDetails.toString();
    }
}
