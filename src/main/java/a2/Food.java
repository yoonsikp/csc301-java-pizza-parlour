package a2;

abstract class Food {

  private final String type;
  private final Float price;

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
}
