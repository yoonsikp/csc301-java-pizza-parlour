package a2;

abstract class Delivery {

  private final String address;
  private final String type;
  private final String orderID;

  Delivery(Delivery.Builder builder) {
    this.address = builder.address;
    this.type = builder.type;
    this.orderID = builder.orderID;
  }

  String getAddress() {
    return this.address;
  }

  abstract String outputDeliveryDetails(Order order);

  public String toString() {
    return this.type + " to " + this.getAddress() + " for Order: " + orderID;
  }

  static class Builder {

    private String address;
    private String type;
    private String orderID;

    Builder address(String address) {
      this.address = address;
      return this;
    }

    Builder type(String type) {
      this.type = type;
      return this;
    }

    Builder orderID(String orderID) {
      this.orderID = orderID;
      return this;
    }

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
