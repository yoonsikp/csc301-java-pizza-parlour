package a2;

import java.util.ArrayList;
import java.util.List;

public class DeliveryHandler {
    public List<String> getDeliveryMethods() {
        return new ArrayList<String>();
    }

    public Delivery createDelivery(Order currOrder, String address, int delivTypeIndex) {
        return new Delivery();
    }

    public void removeDelivery(Order currOrder) {
    }
}
