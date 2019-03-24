package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeliveryHandler {
    private ArrayList<String> deliveryMethods;
    private DeliveryFactory deliveryFactory;

    public DeliveryHandler(){
        this.deliveryMethods = DeliveryLoader.getDeliveryMethods("delivery.json");
        this.deliveryFactory = new DeliveryFactory();
    }

    public List<String> getDeliveryMethods() {
        return this.deliveryMethods;
    }

    public void setDeliveryMethods(){

    }

    public Delivery createDelivery(Order order, String address, int delivTypeIndex){
        String delivType = this.getDeliveryMethodAt(delivTypeIndex);
        return this.deliveryFactory.createDelivery(order, address, delivType);
    }

    public String getDeliveryMethodAt(int delivTypeIndex) {
        return this.deliveryMethods.get(delivTypeIndex);
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
