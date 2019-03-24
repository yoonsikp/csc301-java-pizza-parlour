package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeliveryHandler {
    private ArrayList<String> deliveryMethods;

    public DeliveryHandler(){
        this.deliveryMethods = DeliveryLoader.getDeliveryMethods("delivery.json");

    }

    public List<String> getDeliveryMethods() {
        return this.deliveryMethods;
    }

    public void setDeliveryMethods(){

    }

    public Delivery createDelivery(Order currOrder, String address, int delivTypeIndex) {
        Delivery currDelivery = new DeliveryFactory.Builder().address(address)
                .type(this.deliveryMethods.get(delivTypeIndex)).status().build();
        return currDelivery;

    }

    public void removeDelivery(Order currOrder) {
        currOrder.delivery = null;
    }

    public String printDeliveryDetails(Order order){
        if(order.getDelivery() == null){
            return "order is for pick-up";
        }else {
            return order.getDelivery().outputDeliveryDetails(order);
        }
    }


}
