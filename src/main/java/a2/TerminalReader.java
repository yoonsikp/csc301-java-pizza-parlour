package a2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalReader {
    private java.io.InputStream is;

    public TerminalReader(){
        this.is = System.in;
    }

    public TerminalReader(java.io.InputStream is){
        this.is = is;
    }

    public void startReading() {
        // Input
        String genInput;
        Scanner in = new Scanner(this.is);


        OrderHandler currOrderHandler = PizzaParlour.getOrderHandler();
        DeliveryHandler currDeliveryHandler = PizzaParlour.getDeliveryHandler();

        Order currOrder = null;
        Food currFood = null;

        Menu currMenu = PizzaParlour.getMenu();
        System.out.println("Welcome to 301 Pizza!:  ");
        genInput = in.nextLine();

        while(!genInput.equals("exit")){

            String[] command = genInput.split(" ");
            if (command[0].equals("view_menu") && command.length == 1) {
                System.out.println("this is the menu");
                continue;
            }

            if (command[0].equals("view_item") && command.length == 2) {
                System.out.println("this is the menu item");
                continue;
            }

            if (currOrder == null && currFood == null) {
                if (command[0].equals("create_o") && command.length == 1) {
                    System.out.println("we created an order, now type create_food");
//                currOrder = currOrderHandler.createOrder();
                }
                else if (command[0].equals("select_o") && command.length == 2) {
                    System.out.println("we selected an order, now do something with it");
//                currOrder = currOrderHandler.getOrder(command[1]);
                }
                else if (command[0].equals("list_o") && command.length == 1) {
//                    System.out.println("we are printing all the orders");
//                List <Order> allOrders = currOrderHandler.getAllOrders();
//                    for (Order order : allOrders) {
//                        System.out.println(order.toString());
//                    }
                }
            }
            if (currOrder != null && currFood == null) {
                if (command[0].equals("cancel_o") && command.length == 1) {
                    System.out.println("we canceled the order, you can now create other orders");
//                currOrderHandler.removeOrder(currOrder);
                }
                else if (command[0].equals("create_deliv") && command.length == 1) {
                    System.out.println("Select you delivery type by number");

//                  List<String> delivMethods = currDeliveryHandler.getDeliveryMethods();

                    for (int i = 0; i < delivMethods.length; i++){
                        StringBuilder tempString = new StringBuilder("[");
                        tempString.append(Integer.toString(i));
                        tempString.append("] ");
                        tempString.append(delivMethods[i]);
                        System.out.println(tempString);
                    }

                    int delivTypeIndex = Integer.parseInt(in.nextLine());
                    System.out.println("Please tell us your address");
                    String address = in.nextLine();
                    System.out.println("we will deliver to the place for you!");
//                Delivery delivery = currDeliveryHandler.createDelivery(currOrder, address, delivTypeIndex);
                    //currOrder.setDelivery(delivery);
                }
                else if (command[0].equals("cancel_deliv") && command.length == 1) {
                    //currDeliveryHandler.removeDelivery(currOrder);
                }
                else if (command[0].equals("print_order") && command.length == 1) {
                    //List<Food> foods = currOrder.getFoods();
                    for (int i = 0; i < foods.length; i++){
                        StringBuilder tempString = new StringBuilder("[");
                        tempString.append(Integer.toString(i));
                        tempString.append("] ");
                        tempString.append(foods[i].toString());
                        System.out.println(tempString);
                    }
                    //Delivery deliv = currOrder.getDelivery();
                    if (deliv == null) {
                        System.out.println("Delivery Method: Pickup");
                    } else {
                        System.out.printf("Delivery Method: ");
                        System.out.println(deliv.toString());
                    }
                    System.out.printf("Final Price: ");
                    System.out.println(currOrder.getPrice());
                }
                else if (command[0].equals("create_food") && command.length == 2) {
                    int pizzaType;
                    int pizzaSize;
                    List<Integer> pizzaToppings = new ArrayList<Integer> ();
                    if (command[1].equals("pizza")){
                        System.out.println("Choose Pizza Type by number");
                        //List<String> ptypes = currMenu.getPizzaTypes();
                        for (int i = 0; i < ptypes.length; i++){
                            StringBuilder tempString = new StringBuilder("[");
                            tempString.append(Integer.toString(i));
                            tempString.append("] ");
                            tempString.append(ptypes[i].toString());
                            System.out.println(tempString);
                        }

                        System.out.println("Choose Toppings by comma separated numbers, multiple times for multiple toppings");
                        //List<String> ptypes = currMenu.getPizzaTypes();
                        for (int i = 0; i < ptypes.length; i++){
                            StringBuilder tempString = new StringBuilder("[");
                            tempString.append(Integer.toString(i));
                            tempString.append("] ");
                            tempString.append(ptypes[i].toString());
                            System.out.println(tempString);
                        }
                    } else if (command[1].equals("drink")) {

                    }


                }
            }


            if (genInput.equals("help")) {
                System.out.println("view_menu");
                System.out.println("view_item");
                System.out.println("create_o");
                System.out.println("select_o");
                System.out.println("list_o");
            }

            if (genInput.equals("help_in_order")) {
                System.out.println("cancel_o");
                System.out.println("create_deliv");
                System.out.println("cancel_deliv");
                System.out.println("print_order");
                System.out.println("create_food");
                System.out.println("select_food");
            }

            if (genInput.equals("help_in_order_food")) {
                System.out.println("change_food");
                System.out.println("delete_food");
                System.out.println("list_toppings");
            }

            genInput = in.nextLine();

        }
        in.close();
    }
}
