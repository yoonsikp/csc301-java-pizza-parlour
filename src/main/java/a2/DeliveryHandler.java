package a2;

import java.util.ArrayList;
import java.util.List;

public class DeliveryHandler {
    private ArrayList<String> deliveryMethods;

    public List<String> getDeliveryMethods() {
        return this.deliveryMethods;
    }

    public void setDeliveryMethods(){

    }

    public Delivery createDelivery(Order currOrder, String address, int delivTypeIndex) {
        return new Delivery();
    }

    public void removeDelivery(Order currOrder) {
    }
}
