package a2;

import java.text.ParseException;
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

    private void printOptions(List<String> allOptions) {
        for (int i = 0; i < allOptions.size(); i++){
            StringBuilder tempString = new StringBuilder("[");
            tempString.append(Integer.toString(i));
            tempString.append("] ");
            tempString.append(allOptions.get(i).substring(0, 1).toUpperCase() + allOptions.get(i).substring(1));
            System.out.println(tempString);
        }
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
        System.out.println("Welcome to 301 Pizza Parlour!:  ");
        System.out.println("Type 'help' at any time to get commands");
        System.out.printf("/$ ");
        genInput = in.nextLine();

        while(!genInput.equals("exit")){

            String[] command = genInput.split(" ");
            if (command[0].equals("menu") && command.length == 1) {
                System.out.println("this is the menu");
            } else if (command[0].equals("menuitem") && command.length == 2) {
                System.out.println("this is the menu item");
            } else if (currOrder == null && currFood == null) {
                if (command[0].equals("help")) {
                    System.out.println("\tmenu                 \tPrints out the Current Menu");
                    System.out.println("\tmenuitem [item name] \tPrints out the Price of a Menu Item");
                    System.out.println("\taddord               \tAdds an Order to the Pizza Parlour");
                    System.out.println("\tselord [order id]    \tSelects an Order by ID in the Pizza Parlour");
                    System.out.println("\tlsord                \tLists all Current Orders and their IDs");
                }
                else if (command[0].equals("addord") && command.length == 1) {
                    System.out.println("Order Created: Try 'addfood pizza' or 'addfood drink'");
                currOrder = currOrderHandler.createOrder();
                }
                else if (command[0].equals("selord") && command.length == 2) {
                    System.out.println("Order Selected: now do something with it");
                currOrder = currOrderHandler.getOrder(command[1]);
                }
                else if (command[0].equals("lsord") && command.length == 1) {
                    System.out.println("we are printing all the orders");
                    List <Order> allOrders = currOrderHandler.getAllOrders();
                    for (Order order : allOrders) {
                        System.out.println(order.toString());
                    }
                } else {
                    System.out.println("Invalid Command");
                }

            }
            else if (currOrder != null && currFood == null) {
                if (command[0].equals("help")) {
                    System.out.println("\tmenu                 \tPrints out the Current Menu");
                    System.out.println("\tmenuitem [item name] \tPrints out the Price of a Menu Item");
                    System.out.println("\trmord                \tCancels the Currently Selected Order");
                    System.out.println("\tdeliver              \tRequest for Delivery Service");
                    System.out.println("\trmdeliver            \tCancel Delivery Service");
                    System.out.println("\tprintord             \tDetails about the Currently Selected Order");
                    System.out.println("\taddfood [pizza, drink]\tAdd a Dish to the Currently Selected Order");
                    System.out.println("\tselfood              \tSelect a Dish in the Currently Selected Order");
                    System.out.println("\tback                 \tDeselect Currently Selected Order");
                } else if (command[0].equals("back") && command.length == 1) {
                    currOrder = null;
                }
                else if (command[0].equals("rmord") && command.length == 1) {
                    System.out.println("we canceled the order, you can now create other orders");
                    currOrderHandler.removeOrder(currOrder);
                }
                else if (command[0].equals("deliver") && command.length == 1) {
                    System.out.println("Select you delivery type by number");

                    List<String> delivMethods = currDeliveryHandler.getDeliveryMethods();

                    for (int i = 0; i < delivMethods.size(); i++){
                        StringBuilder tempString = new StringBuilder("[");
                        tempString.append(Integer.toString(i));
                        tempString.append("] ");
                        tempString.append(delivMethods.get(i));
                        System.out.println(tempString);
                    }

                    int delivTypeIndex = Integer.parseInt(in.nextLine());
                    System.out.println("Please tell us your address");
                    String address = in.nextLine();
                    System.out.println("we will deliver to the place for you!");
                    Delivery delivery = currDeliveryHandler.createDelivery(currOrder, address, delivTypeIndex);
                    currOrder.setDelivery(delivery);
                }
                else if (command[0].equals("rmdeliver") && command.length == 1) {
                    currDeliveryHandler.removeDelivery(currOrder);
                }
                else if (command[0].equals("printord") && command.length == 1) {
                    List<Food> foods = currOrder.getFoods();
                    for (int i = 0; i < foods.size(); i++){
                        StringBuilder tempString = new StringBuilder("[");
                        tempString.append(Integer.toString(i));
                        tempString.append("] ");
                        tempString.append(foods.get(i).toString());
                        System.out.println(tempString);
                    }
                    Delivery deliv = currOrder.getDelivery();
                    if (deliv == null) {
                        System.out.println("Delivery Method: Pickup");
                    } else {
                        System.out.printf("Delivery Method: ");
                        System.out.println(deliv.toString());
                    }
                    System.out.printf("Final Price: ");
                    System.out.println(currOrder.getPrice());
                }
                else if (command[0].equals("addfood") && command.length == 2) {

                    if (command[1].toLowerCase().equals("pizza")){
                        String pizzaType;
                        String pizzaSize;

                        System.out.println("Choose Pizza Type:");
                        List<String> pizzaTypes = currMenu.getPizzaTypes();
                        printOptions(pizzaTypes);

                        pizzaType = pizzaTypes.get(Integer.parseInt(in.nextLine()));

                        System.out.println("Choose Pizza Size:");
                        List<String> pizzaSizes = currMenu.getPizzaSizes(pizzaType);
                        printOptions(pizzaSizes);

                        pizzaSize = pizzaSizes.get(Integer.parseInt(in.nextLine()));

                        PizzaFactory pf = new PizzaFactory();
                        Pizza currPizza = pf.makeFood();
                        currPizza.setType(pizzaSize);
                        currPizza.setSize(pizzaSize);

                        List<String> pizzaToppings = currMenu.getPizzaToppings();
                        printOptions(pizzaToppings);
                        if (pizzaToppings.size() > 0) {
                            System.out.println("Choose Toppings Separated by Commas (minus sign to remove a topping):");
                            String currLine = in.nextLine();
                            String[] splitToppings = currLine.split(",");
                            for (String topping : splitToppings){
                                int index;
                                try {
                                    index = Integer.parseInt(topping.trim());
                                    if (topping.trim().equals("-0")) {
                                        currPizza.removeTopping(pizzaToppings.get(0));
                                        System.out.println("Pizza fasdfasdf Added to Order");
                                    } else if (index < 0 && (-index < pizzaToppings.size()) ){
                                        currPizza.removeTopping(pizzaToppings.get(-index));
                                    } else if ( index < pizzaToppings.size() ){
                                        currPizza.addTopping(pizzaToppings.get(index));
                                    } else {
                                        System.out.printf("Skipping unknown topping:");
                                        System.out.println(topping);
                                    }
                                } catch (NumberFormatException e){
                                    System.out.printf("Skipping unknown topping:");
                                    System.out.println(topping);
                                }

                            }



                        }
                        currFood = currPizza;
                        currOrder.addFood(currFood);
                        System.out.println("Pizza Successfully Added to Order");
                    } else if (command[1].toLowerCase().equals("drink")) {
                        System.out.println("Choose a Drink:");
                        List<String> drinks = currMenu.getDrinks();
                        printOptions(drinks);
                        String drink = drinks.get(Integer.parseInt(in.nextLine()));
                        DrinkFactory df = new DrinkFactory();
                        Drink currDrink = df.makeFood();
                        currDrink.setType(drink);
                        currFood = currDrink;
                        currOrder.addFood(currFood);
                        System.out.println("Drink Successfully Added to Order");
                    } else {
                        System.out.println("Invalid Command");
                    }


                } else {
                    System.out.println("Invalid Command");
                }
            }
            else if (currOrder != null && currFood != null) {
                if (command[0].equals("help")) {
                    System.out.println("change_food");
                    System.out.println("delete_food");
                    System.out.println("list_toppings");
                    System.out.println("back");
                } else if (command[0].equals("back") && command.length == 1) {
                    currFood = null;
                } else {
                    System.out.println("Invalid Command");
                }
            }
            StringBuilder tempSB = new StringBuilder("/");
            if (currOrder != null) {
                tempSB.append("Order_");
                tempSB.append(currOrder.getID());
            }
            if (currFood != null) {
                tempSB.append("/");
                tempSB.append("Food_");
                tempSB.append(currFood.toString());
            }
            tempSB.append("$ ");
            System.out.printf(tempSB.toString());
            genInput = in.nextLine();

        }
        in.close();
    }
}
