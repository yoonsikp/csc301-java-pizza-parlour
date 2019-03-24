package a2;


public class HouseDelivery extends Delivery {

  public HouseDelivery(Delivery.Builder builder) {
    super(builder);
  }
    /**
     * Returns a string representation of the delivery details of a given HouseDelivery.
     * @param order the order whose delivery details we want
     * @return text representation of order's delivery
     */
  public String outputDeliveryDetails(Order order) {
    //as text

    return "Address: " + order.getDelivery().getAddress() + "\n"
        + "Order Details: " + "\n"
        + order.toString() + "\n"
        + "Order Number: " + order.getOrderID();
  }
}
