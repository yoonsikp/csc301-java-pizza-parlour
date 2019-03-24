package a2;

class PizzaParlour {

  private static Menu menu;
  private static OrderHandler orderHandler;
  private static DeliveryHandler deliveryHandler;

  public static void main(String[] args) {
    menu = MenuLoader.getPopulatedMenu("menu.json");
    orderHandler = new OrderHandler();
    deliveryHandler = new DeliveryHandler();
    TerminalInterface terminalInterface = new TerminalInterface();
    try {
      terminalInterface.startReading();
    } catch (java.util.NoSuchElementException e) {
      //Time to exit
    }
  }

  static Menu getMenu() {
    return menu;
  }

  static OrderHandler getOrderHandler() {
    return orderHandler;
  }

  static DeliveryHandler getDeliveryHandler() {
    return deliveryHandler;
  }
}