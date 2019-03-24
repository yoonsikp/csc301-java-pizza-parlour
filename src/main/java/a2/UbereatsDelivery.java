package a2;

import java.util.HashMap;

public class UbereatsDelivery extends Delivery {

    public UbereatsDelivery(Delivery.Builder builder) {
        super(builder);
    }

    public String outputDeliveryDetails(Order order){
        //as JSON
        HashMap<String, String> deliveryDetailsJSON = new HashMap<>();
        deliveryDetailsJSON.put("Address", order.getDelivery().getAddress());
        deliveryDetailsJSON.put("Order Details", order.toString());
        deliveryDetailsJSON.put("Order Number", order.getOrderID());
        return deliveryDetailsJSON.toString();

    }
}