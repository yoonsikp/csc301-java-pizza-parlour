package a2;

/**
 * This is the Delivery class. It is an abstract class and contains both abstract and non-abstract
 * attributes and methods that can be used and/or implemented in extending classes. A Delivery has
 * an address, a type and an orderID that represents the Order the Delivery is for.
 */
abstract class Delivery {

  /**
   * Address to deliver to
   */
  private final String address;
  /**
   * Delivery type
   */
  private final String type;
  /**
   * OrderID representing the Order the Delivery is for
   */
  private final String orderID;

  /**
   * Constructor for a Delivery, uses a Builder design pattern to avoid constructor tunneling.
   * Creates a delivery with a provided address, type and orderID.
   *
   * @param builder the delivery builder
   */
  Delivery(Delivery.Builder builder) {
    this.address = builder.address;
    this.type = builder.type;
    this.orderID = builder.orderID;
  }

  /**
   * Getter for this delivery's address
   *
   * @return this delivery's address
   */
  String getAddress() {
    return this.address;
  }

  /**
   * Abstract method for returning a representation of the delivery details of a given Delivery.
   * This method will be implemented in extending classes.
   *
   * @param order the order whose delivery details we want
   * @return delivery details for the delivery of order
   */
  abstract String outputDeliveryDetails(Order order);

  /**
   * Getter for this delivery's type
   *
   * @return this delivery's type
   */
  public String toString() {
    return this.type + " to " + this.getAddress() + " for Order: " + orderID;
  }

  /**
   * This is a static Builder class that is used to construct Delivery objects.
   */
  static class Builder {

    /**
     * The address of the builder, i.e. the address to set the Delivery to
     */
    private String address;
    /**
     * The type of the builder, i.e. the type to set the Delivery to
     */
    private String type;
    /**
     * The orderID of the builder, i.e. the orderID to set the Delivery to
     */
    private String orderID;

    /**
     * Sets builder's address.
     *
     * @param address address to set to
     * @return current builder instance
     */
    Builder address(String address) {
      this.address = address;
      return this;
    }

    /**
     * Sets builder's type.
     *
     * @param type type to set to
     * @return current builder instance
     */
    Builder type(String type) {
      this.type = type;
      return this;
    }

    /**
     * Sets builder's orderID.
     *
     * @param orderID orderID to set to
     * @return current builder instance
     */
    Builder orderID(String orderID) {
      this.orderID = orderID;
      return this;
    }

    /**
     * Build the desired Delivery type depending on the builder's type. Returns an UbereatsDelivery
     * if the type is ubereats, a FoodoraDelivery if the type is foodora, and a HouseDelivery if the
     * type is house.
     *
     * @return new Delivery object
     */
    Delivery build() {
      switch (this.type) {
        case "ubereats":
          return new UbereatsDelivery(this);
        case "foodora":
          return new FoodoraDelivery(this);
        default:
          return new HouseDelivery(this);
      }
    }

  }
}
