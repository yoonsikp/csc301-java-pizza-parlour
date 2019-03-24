package a2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeliveryHandlerTest {
    private final DeliveryHandler deliveryHandler = new DeliveryHandler();

    @Test
    public void fakeTest() {
        assert(true);
    }

    @Test
    public void printEmptyDelivTest() {
        Order emptyOrder = new Order("1");
        assertEquals(this.deliveryHandler.printDeliveryDetails(emptyOrder),
                "You must order at least one item first.");
        Order nonEmptyOrder = createSingleDrinkOrder();
        // no delivery added
        assertEquals(this.deliveryHandler.printDeliveryDetails(nonEmptyOrder), "Your order is for pick-up.");

    }

    @Test
    public void printUbereatsDeliv() {
        // test ubereats JSON
        Order nonEmptyOrder = createSingleDrinkOrder();
        Delivery delivUbereats = this.deliveryHandler.createDelivery(nonEmptyOrder, "unhappy", "ubereats");
        nonEmptyOrder.setDelivery(delivUbereats);
        assertTrue(this.deliveryHandler.printDeliveryDetails(nonEmptyOrder).startsWith("{\"Address\":\"unhappy\",\"Order Details\":\"COLA"));


    }

    @Test
    public void printFoodoraDeliv() {
        // test foodora CSV
        Order nonEmptyOrder = createSingleDrinkOrder();
        Delivery delivFoodora = this.deliveryHandler.createDelivery(nonEmptyOrder, "unhappy", "foodora");
        nonEmptyOrder.setDelivery(delivFoodora);
        assertEquals("Address,Order Details,Order Number\nunhappy,COLA Drink ($3.3),2",
                this.deliveryHandler.printDeliveryDetails(nonEmptyOrder));

    }

    @Test
    public void printHouseDeliv(){
        //test house text
        Order nonEmptyOrder = createSingleDrinkOrder();
        Delivery delivHouse = this.deliveryHandler.createDelivery(nonEmptyOrder, "unhappy", "in-store");
        nonEmptyOrder.setDelivery(delivHouse);
        assertTrue(this.deliveryHandler.printDeliveryDetails(nonEmptyOrder).startsWith("Address: unhappy"));

    }

    @Test
    public void removeDelivery(){
        List<String> methodsList = new ArrayList<>();
        methodsList.add("ubereats");
        methodsList.add("foodora");
        methodsList.add("in-store");

        DeliveryHandler deliveryHandler = new DeliveryHandler();
        assertEquals(deliveryHandler.getDeliveryMethods(), methodsList);
    }

    @Test
    public void ubereatsDeliveryTest(){
        Order nonEmptyOrder = createSingleDrinkOrder();
        Delivery delivUbereats = this.deliveryHandler.createDelivery(nonEmptyOrder, "unhappy", "ubereats");
        nonEmptyOrder.setDelivery(delivUbereats);
        assertTrue(delivUbereats.outputDeliveryDetails(nonEmptyOrder).startsWith("{\"Address\":\"unhappy\",\"Order Details\":\"COLA"));

    }

    @Test
    public void foodoraDeliveryTest(){
        Order nonEmptyOrder = createSingleDrinkOrder();
        Delivery delivFoodora = this.deliveryHandler.createDelivery(nonEmptyOrder, "unhappy", "foodora");
        nonEmptyOrder.setDelivery(delivFoodora);
        assertEquals("Address,Order Details,Order Number\nunhappy,COLA Drink ($3.3),2",
                delivFoodora.outputDeliveryDetails(nonEmptyOrder));

    }

    @Test
    public void houseDeliveryTest(){
        Order nonEmptyOrder = createSingleDrinkOrder();
        Delivery delivHouse = this.deliveryHandler.createDelivery(nonEmptyOrder, "unhappy", "in-store");
        nonEmptyOrder.setDelivery(delivHouse);
        assertTrue(delivHouse.outputDeliveryDetails(nonEmptyOrder).startsWith("Address: unhappy"));

    }

   private Order createSingleDrinkOrder(){
       Order order = new Order("2");
       Food currFood = new Drink.Builder()
               .type("cola")
               .price((float) 3.3)
               .build();
       order.addFood(currFood);
       return order;

   }
}
