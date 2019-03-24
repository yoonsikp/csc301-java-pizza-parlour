package a2;

import java.util.HashMap;

public abstract class Food {

  public abstract static class FoodBuilder<T extends FoodBuilder<T>> {

    private String type;
    private Float price;

    protected abstract T getThis();

    T type(String type) {
      this.type = type;
      return getThis();
    }

    T price(Float price) {
      this.price = price;
      return getThis();
    }

    public abstract Food build();

  }

  private String type;
  private Float price;

  Food(FoodBuilder builder) {
    this.type = builder.type;
    this.price = builder.price;
  }

  String getType() {
    return this.type;
  }

  Float getPrice() {
    return this.price;
  }
}
