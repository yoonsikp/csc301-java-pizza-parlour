package a2;

import java.util.ArrayList;
import java.util.List;


class DeliveryHandler {

  private final ArrayList<String> deliveryMethods = new ArrayList<>();

  DeliveryHandler() {
    this.deliveryMethods.add("ubereats");
    this.deliveryMethods.add("foodora");
    this.deliveryMethods.add("in-store");
  }

  List<String> getDeliveryMethods() {
    return this.deliveryMethods;
  }

  Delivery createDelivery(Order currOrder, String address, String delivType) {
    return new Delivery.Builder().address(address)
        .type(delivType).orderID(currOrder.getOrderID()).build();
  }

  void removeDelivery(Order currOrder) {
    currOrder.setDelivery(null);
  }

  String printDeliveryDetails(Order order) {
    if (order.getFoods().size() == 0) {
      return "order an item first";
    } else if (order.getDelivery() == null) {
      return "order is for pick-up";
    } else {
      return order.getDelivery().outputDeliveryDetails(order);
    }
  }


}
