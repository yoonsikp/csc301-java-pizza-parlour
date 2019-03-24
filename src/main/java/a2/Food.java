package a2;

/**
 * This is the abstract Food class. A Food object has a type and price, as well as abstract and
 * nonabstract methods that can be used/implemented by extending classes. A Food object will contain
 * all information about a particular item in an Order.
 */
abstract class Food {

  /**
   * Type of this Food
   */
  private final String type;
  /**
   * Price of this Food
   */
  private final Float price;

  /**
   * Constructor for a Food object, uses a Builder design pattern to avoid constructor tunneling.
   * Creates a Food with a provided type and price.
   *
   * @param builder builder that makes the Food object
   */
  Food(FoodBuilder builder) {
    this.type = builder.type;
    this.price = builder.price;
  }

  /**
   * Getter for the Food's type.
   *
   * @return the Food type
   */
  String getType() {
    return this.type;
  }

  /**
   * Getter for the price of the Food.
   *
   * @return price of Food
   */
  Float getPrice() {
    return this.price;
  }

  /**
   * This is an abstract static Builder class that is used to construct Food objects. It will be
   * extended by Builder classes for extending classes of Food.
   */
  public abstract static class FoodBuilder<T extends FoodBuilder<T>> {

    /**
     * The type of the builder, i.e. the type to set the Food to
     */
    private String type;
    /**
     * The price of the builder, i.e. the price to set the Food to
     */
    private Float price;

    /**
     * Abstract method that should return an instance of the Drink Builder class, will be
     * implemented in extending classes.
     *
     * @return return type will be declared in extending classes
     */
    protected abstract T getThis();

    /**
     * Setter for type in Builder.
     *
     * @param type type to set to
     * @return instance of Builder with type set
     */
    T type(String type) {
      this.type = type;
      return getThis();
    }

    /**
     * Setter for price in Builder.
     *
     * @param price price to set to
     * @return instance of Builder with price set
     */
    T price(Float price) {
      this.price = price;
      return getThis();
    }

    /**
     * Abstract method to use Builder to create a Food object, will be implemented in extending
     * classes.
     *
     * @return the desired Food object
     */
    public abstract Food build();

  }
}
