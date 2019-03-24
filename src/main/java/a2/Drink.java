package a2;

/**
 * This is the Drink class. A Drink is a Food object that has a specific type and price.
 */
public class Drink extends Food {

  /**
   * Constructor for a Drink object, which extends a Food object, uses a Builder design pattern to
   * avoid constructor tunneling. Creates a Drink with a provided type and price.
   *
   * @param builder builder that makes the Drink object
   */
  private Drink(Builder builder) {
    super(builder);
  }

  /**
   * Returns a string representation of a Drink object.
   *
   * @return the Drink string representation
   */
  public String toString() {
    return this.getType().toUpperCase() + " Drink" +
        " ($" + this.getPrice() + ")";
  }

  /**
   * This is a static Builder class that is used to construct Drink objects.
   */
  public static class Builder extends Food.FoodBuilder<Builder> {

    /**
     * Returns an instance of the Drink Builder class.
     *
     * @return a Drink Builder
     */
    @Override
    protected Builder getThis() {
      return this;
    }

    /**
     * Builds a new Drink obkect by calling Drink's constructor, and providing an instance of the
     * Builder class that has the desired type and price.
     *
     * @return new desired Drink object
     */
    public Food build() {
      return new Drink(this);
    }
  }

}
