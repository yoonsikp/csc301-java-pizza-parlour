package a2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class PizzaParlour {

    private static Menu menu;
    private static OrderHandler orderHandler;
    private static DeliveryHandler deliveryHandler;

    public static void main(String[] args) {

        menu = MenuLoader.getPopulatedMenu("menu.json");
        orderHandler = new OrderHandler();
        deliveryHandler = new DeliveryHandler();
        TerminalReader terminalReader = new TerminalReader();
        terminalReader.startReading();

//        BufferedReader reader;
//        try {
//            reader = new BufferedReader(new FileReader("hi"));
//            String line = reader.readLine();
//            reader.close();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//
//

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