package a2;

import java.util.HashMap;

public abstract class Delivery {

  public static class Builder {

    private String address;
    private String type;
    private String orderID;

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder orderID(String orderID) {
      this.orderID = orderID;
      return this;
    }

    public Delivery build() {
      if (this.type.equals("ubereats")) {
        return new UbereatsDelivery(this);
      } else if (this.type.equals("foodora")) {
        return new FoodoraDelivery(this);
      } else {
        return new HouseDelivery(this);
      }
    }

  }

  Delivery(Delivery.Builder builder) {
    this.address = builder.address;
    this.type = builder.type;
    this.orderID = builder.orderID;
  }

  private String address;
  private String type;
  private String orderID;

  public String getAddress() {
    return this.address;
  }

  public String getType() {
    return type;
  }

  public abstract String outputDeliveryDetails(Order order);

  public String toString() {
    String deliveryString = this.getType() + " to " + this.getAddress() + " for Order: " + orderID;
    return deliveryString;
  }
}
