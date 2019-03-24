package a2;

/**
 * This is the PizzaParlour class. A PizzaParlour represents the entire restaurant, and has a Menu,
 * and an OrderHandler and DeliveryHandler, which are used to transparently interact with Order
 * functionalities and Delivery functionalities, respectively.
 */
class PizzaParlour {

  /**
   * Pzza parlour's menu
   */
  private static Menu menu;
  /**
   * OrderHandler for pizza parlour
   */
  private static OrderHandler orderHandler;
  /**
   * DeliveryHandler for pizza parlour
   */
  private static DeliveryHandler deliveryHandler;

  /**
   * Getter for the pizza parlour's Menu
   *
   * @return Menu of pizza parlour
   */
  static Menu getMenu() {
    return menu;
  }

  /**
   * Getter for the pizza parlour's OrderHandler
   *
   * @return OrderHandler for pizza parlour
   */
  static OrderHandler getOrderHandler() {
    return orderHandler;
  }

  /**
   * Getter for the pizza parlour's DeliveryHandler
   *
   * @return DeliveryHandler for pizza parlour
   */
  static DeliveryHandler getDeliveryHandler() {
    return deliveryHandler;
  }

  /**
   * Main method that starts the entire pizza parlous process. This method calls the Menu parser in
   * MenuLoader, instantiates the handlers, instantiates terminal interface by calling its first
   * constructor, and then starts reading from the terminal.
   *
   * @param args arguments to the main method
   */
  public static void main(String[] args) {
    menu = MenuLoader.getPopulatedMenu("menu.json");
    orderHandler = new OrderHandler();
    deliveryHandler = new DeliveryHandler();
    // call constructor 1 with no parameters
    TerminalInterface terminalInterface = new TerminalInterface();
    try {
      terminalInterface.startReading();
    } catch (java.util.NoSuchElementException e) {
      //Time to exit
    }
  }
}