package a2;

/**
 * This is the HouseDelivery class. The HouseDelivery class extends the Delivery class and inherits
 * all of its characteristics. The HouseDelivery class can output its delivery details in text
 * format.
 */
public class HouseDelivery extends Delivery {

  /**
   * Constructor for a HouseDelivery object, will create a HouseDelivery object by use of a Builder
   * class that prevents constructor tunneling.
   *
   * @param builder the builder that constructs a HouseDelivery
   */
  public HouseDelivery(Delivery.Builder builder) {
    super(builder);
  }

  /**
   * Returns a string representation of the delivery details of a given HouseDelivery.
   *
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
