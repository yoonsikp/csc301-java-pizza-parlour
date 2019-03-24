package a2;

public class PizzaParlour {

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

  public static Menu getMenu() {
    return menu;
  }

  public static OrderHandler getOrderHandler() {
    return orderHandler;
  }

  public static DeliveryHandler getDeliveryHandler() {
    return deliveryHandler;
  }
}