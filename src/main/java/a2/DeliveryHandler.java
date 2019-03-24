package a2;

import java.util.ArrayList;
import java.util.List;


class DeliveryHandler {

  private final ArrayList<String> deliveryMethods = new ArrayList<>();
    /**
     * Construct a DeliveryHandler and populate its delivery methods.
     */
  DeliveryHandler() {
    this.deliveryMethods.add("ubereats");
    this.deliveryMethods.add("foodora");
    this.deliveryMethods.add("in-store");
  }
    /**
     * Returns a List of delivery methods that the pizza parlour uses
     *
     * @return List of delivery methods
     */
  List<String> getDeliveryMethods() {
    return this.deliveryMethods;
  }
    /**
     * Creates a Delivery object or type <delivType> for the order <currOrder> using the provided address
     *
     * @param currOrder the order to create a delivery for
     * @param address the address to be delivered to
     * @param delivType the type of delivery method
     * @return a new Delivery object with fields according to the provided parameters
     */
  Delivery createDelivery(Order currOrder, String address, String delivType) {
    return new Delivery.Builder().address(address)
        .type(delivType).orderID(currOrder.getOrderID()).build();
  }
    /**
     * Removes a delivery from an order and setting the delivery field of the order to null
     *
     * @param currOrder the order from which to remove the delivery
     */
  void removeDelivery(Order currOrder) {
    currOrder.setDelivery(null);
  }
/**
 * Returns formatted delivery details for the delivery belonging to order depending on the delivery type.
 * If the delivery type is Ubereats, the returned is a JSON object, if the delivery is Foodora, the returned
 * is a CSV object, and if the delivery type is House, the returned is a text representation of the delivery.
 * @param order the order whose delivery's details are being returned
 * @return the delivery details of the order as JSON, CSV or text
 */
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
