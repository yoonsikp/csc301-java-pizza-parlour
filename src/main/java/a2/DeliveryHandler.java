package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DeliveryHandler {

  private ArrayList<String> deliveryMethods = new ArrayList<>();

  public DeliveryHandler() {
    this.deliveryMethods.add("ubereats");
    this.deliveryMethods.add("foodora");
    this.deliveryMethods.add("in-store");
  }

  public List<String> getDeliveryMethods() {
    return this.deliveryMethods;
  }

  public Delivery createDelivery(Order currOrder, String address, String delivType) {
    Delivery currDelivery = new Delivery.Builder().address(address)
        .type(delivType).orderID(currOrder.getOrderID()).build();
    return currDelivery;
  }

  public void removeDelivery(Order currOrder) {
    currOrder.setDelivery(null);
  }

  public String printDeliveryDetails(Order order) {
    if (order.getFoods().size() == 0) {
      return "order an item first";
    } else if (order.getDelivery() == null) {
      return "order is for pick-up";
    } else {
      return order.getDelivery().outputDeliveryDetails(order);
    }
  }


}
