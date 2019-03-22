package a2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class PizzaParlour {

    private static Menu myMenu;
    public static void main(String[] args) {

        myMenu = MenuLoader.getPopulatedMenu("menu.json");
        TerminalReader myTerminalReader = new TerminalReader();
        myTerminalReader.startReading();

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
        return myMenu;
    }

    public static OrderHandler getOrderHandler() {
        return new OrderHandler();
    }

    public static DeliveryHandler getDeliveryHandler() {
        return new DeliveryHandler();
    }
}