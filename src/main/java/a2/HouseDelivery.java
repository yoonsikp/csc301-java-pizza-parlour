package a2;

public class HouseDelivery extends Delivery {

  public HouseDelivery(Delivery.Builder builder) {
    super(builder);
  }

  public String outputDeliveryDetails(Order order) {
    //as text

    return "Address: " + order.getDelivery().getAddress() + "\n"
        + "Order Details: " + "\n"
        + order.toString() + "\n"
        + "Order Number: " + order.getOrderID();
  }
}
