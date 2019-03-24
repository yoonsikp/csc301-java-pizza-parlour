package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TerminalInterface {
    private java.io.InputStream inputStream;
    private Scanner streamScanner;
    private OrderHandler orderHandler;
    private DeliveryHandler deliveryHandler;
    private Order currentOrder;
    private Food currentFood;
    private Menu currentMenu;

    public TerminalInterface(){
        this(System.in);
    }

    public TerminalInterface(java.io.InputStream inputStream){
        this(inputStream, PizzaParlour.getMenu());
    }

    public TerminalInterface(java.io.InputStream inputStream, Menu menu){
        this(inputStream, menu, PizzaParlour.getOrderHandler(), PizzaParlour.getDeliveryHandler());
    }

    public TerminalInterface(java.io.InputStream inputStream, Menu menu, OrderHandler orderHandler,
                             DeliveryHandler deliveryHandler){
        this.inputStream = inputStream;
        this.streamScanner = new Scanner(this.inputStream);
        this.orderHandler = orderHandler;
        this.deliveryHandler = deliveryHandler;
        this.currentOrder = null;
        this.currentFood = null;
        this.currentMenu = menu;
    }

    public void startReading() {
        // Input
        String genInput;

        System.out.println("Welcome to 301 Pizza Parlour");
        System.out.println("Type '?' at any time for help. End program by typing 'exit'.");
        System.out.printf("/$ ");


        genInput = this.streamScanner.nextLine();

        while(!genInput.equals("exit")){

            String[] command = genInput.split(" ");
            if (command[0].equals("menu") && command.length == 1) {
                System.out.println(this.currentMenu.toString());
            } else if (command[0].equals("menuitem") && command.length == 2) {
                System.out.println(this.currentMenu.getMenuItem(command[1]));
            } else if (this.currentOrder == null && this.currentFood == null) {
                if (command[0].equals("?") || command[0].equals("help")) {
                    System.out.println("\tmenu               \tPrint out the Current Menu");
                    System.out.println("\tmenuitem ITEM      \tPrint out the Price of a Menu Item");
                    System.out.println("\tneworder           \tCreate an Order at the Pizza Parlour");
                    System.out.println("\tselorder [ORDER_ID]\tSelect an Order, can optionally specify ID");
                    System.out.println("\tlsorder            \tList all Current Orders and their IDs");
                }
                else {
                    handleOrderState(command);
                }
            }
            else if (this.currentOrder != null && this.currentFood == null) {
                if (command[0].equals("?") || command[0].equals("help")) {
                    System.out.println("\tmenu              \tPrint out the Current Menu");
                    System.out.println("\tmenuitem ITEM     \tPrint out the Price of a Menu Item");
                    System.out.println("\trmorder           \tCancel the Currently Selected Order");
                    System.out.println("\tdeliver           \tRequest for Delivery Service");
                    System.out.println("\trmdeliver         \tCancel Delivery Service");
                    System.out.println("\tprintdeliver      \tView Delivery Details");
                    System.out.println("\tprintorder        \tDetails about the Current Order");
                    System.out.println("\tnewpizza          \tAdd a Pizza to the Current Order");
                    System.out.println("\tnewdrink          \tAdd a Drink to the Current Order");
                    System.out.println("\tseldish           \tSelect a Dish in the Current Order");
                    System.out.println("\tlsdish            \tList all Dishes in the Current Order");
                    System.out.println("\t..                \tDeselect Currently Selected Order");
                } else if (command[0].equals("..") && command.length == 1) {
                    this.currentOrder = null;
                }
                else if (command[0].equals("rmorder") && command.length == 1) {
                    System.out.println("Order has been Canceled");
                    this.orderHandler.removeOrder(this.currentOrder);
                    this.currentOrder = null;
                }
                else if (command[0].equals("deliver") && command.length == 1) {
                    getDeliveryDetails();
                }
                else if (command[0].equals("rmdeliver") && command.length == 1) {
                    this.deliveryHandler.removeDelivery(this.currentOrder);
                    System.out.println("Delivery cancelled, order will be for pick-up");
                }
                else if (command[0].equals("printdeliver") && command.length ==1){
                    System.out.println(this.deliveryHandler.printDeliveryDetails(currentOrder));
                }
                else if (command[0].equals("printorder") && command.length == 1) {
                    prettyPrintCurrentOrder();
                } else {
                    handleFoodState(command);
                }
            }
            else if (this.currentOrder != null && this.currentFood != null) {
                if (command[0].equals("?") || command[0].equals("help")) {
                    System.out.println("\tmenu              \tPrint out the Current Menu");
                    System.out.println("\tmenuitem ITEM     \tPrint out the Price of a Menu Item");
                    System.out.println("\tchdish            \tModify the Current Dish");
                    System.out.println("\tprintdish         \tPrint Info about the Current Dish");
                    System.out.println("\trmdish            \tRemove the Current Dish from the Order");
                    System.out.println("\t..                \tDeselect Currently Selected Dish");
                } else if (command[0].equals("..") && command.length == 1) {
                    this.currentFood = null;
                } else if (command[0].equals("rmdish") && command.length == 1) {
                    System.out.println("Dish has been removed");
                    this.currentOrder.removeFood(this.currentFood);
                    this.currentFood = null;
                } else if (command[0].equals("printdish") && command.length == 1) {
                    System.out.println(this.currentFood.toString());
                } else if (command[0].equals("chdish") && command.length == 1) {
                    if (this.currentFood instanceof Drink) {
                        addDrink((Drink) this.currentFood);
                    } else if (this.currentFood instanceof Pizza){
                        addPizza((Pizza) this.currentFood);
                    } else {
                        System.out.println("Dish Type can't be modified");
                    }
                } else {
                    System.out.println("Invalid Command");
                }
            }
            StringBuilder tempSB = new StringBuilder("/");
            if (this.currentOrder != null) {
                tempSB.append("Order_");
                tempSB.append(this.currentOrder.getOrderID());
            }
            if (this.currentFood != null) {
                tempSB.append("/");
                tempSB.append("Food_");
                tempSB.append(this.currentFood.getClass().getSimpleName()+"-"+ this.currentFood.getType().toUpperCase());
            }
            tempSB.append("$ ");
            System.out.printf(tempSB.toString());
            genInput = this.streamScanner.nextLine();

        }
        this.streamScanner.close();
    }


    private void handleOrderState(String[] command){
        if (command[0].equals("neworder") && command.length == 1) {
            System.out.println("Order Created: Try 'newpizza'");
            this.currentOrder = this.orderHandler.createOrder();
            return;
        }
        else if (command[0].equals("selorder") && command.length == 2) {

            this.currentOrder = this.orderHandler.getOrder(command[1]);
            if (currentOrder == null){
                System.out.println("Order ID not found");
            } else {
                System.out.println("Order Selected:");
            }
            return;
        } else if (command[0].equals("selorder") && command.length == 1) {
            List <Order> allOrders = this.orderHandler.getAllOrders();
            if (allOrders.size() == 0) {
                System.out.println("No Current Orders");
                return;
            }
            System.out.println("Choose Order ID:");
            printAllOrders();

            this.currentOrder = this.orderHandler.getOrder(this.streamScanner.nextLine().trim());
            if (currentOrder == null){
                System.out.println("Order ID not found");
            }
            return;
        }
        else if (command[0].equals("lsorder") && command.length == 1) {
            List <Order> allOrders = this.orderHandler.getAllOrders();
            if (allOrders.size() == 0) {
                System.out.println("No Current Orders");
                return;
            }
            System.out.println("List of all Orders:");
            printAllOrders();
            return;
        }
        System.out.println("Invalid Command");
    }

    private void handleFoodState(String[] command){
        if (command[0].equals("newdrink") && command.length == 1) {
                addDrink(null);
                return;
        } else if (command[0].equals("newpizza") && command.length == 1) {
                addPizza(null);
                return;
        } else if (command[0].equals("seldish") && command.length == 1) {
            List<Food> allFoods = this.currentOrder.getFoods();
            if (allFoods.size() >0) {
                System.out.println("Choose Dish from Order:");
                List<String> allFoodStrings = new ArrayList<>();
                Food food = allFoods.get(0);
                for (Food iterFood: allFoods){
                    allFoodStrings.add(iterFood.toString());
                }
                printOptions(allFoodStrings);
                int index = getIndexResponse(allFoodStrings);
                if (index == -1) {
                    System.out.println("Invalid Option");
                    return;
                }
                if (index == -2) {
                    System.out.println("Defaulting to: " + food.toString());
                } else {
                    food = allFoods.get(index);
                }
                this.currentFood = food;
            } else {
                System.out.println("No Dishes in Order");
            }
            return;
        } else if (command[0].equals("lsdish") && command.length == 1) {
            List<Food> allFoods = this.currentOrder.getFoods();
            if (allFoods.size() >0) {
                for (Food food: allFoods){
                    System.out.println("- " + food.toString());
                }
            } else {
                System.out.println("No Dishes in Order");
            }
            return;
        }
        System.out.println("Invalid Command");
    }

    private void addDrink(Drink template){
        if (template == null) {
            System.out.println("Choose a Drink:");
        } else {
            System.out.printf("Current Drink Type: ");
            System.out.println(template.getType());
            System.out.println("Change to ... (Press Enter to Skip)");
        }
        List<String> drinks = this.currentMenu.getDrinks();
        String drink;
        if (template == null){
            drink = drinks.get(0);
        } else {
            drink = template.getType();
        }
        printOptions(drinks);
        int index = getIndexResponse(drinks);
        if (index == -1) {
            System.out.println("Invalid Option");
            return;
        }
        if (index == -2) {
            System.out.println("Defaulting to: " + drink);
        } else {
            drink = drinks.get(index);
        }
        Food currFood = new Drink.Builder()
                .type(drink)
                .price(this.currentMenu.getDrinkPrice(drink))
                .build();
        this.currentOrder.addFood(currFood);
        if (template == null){
            System.out.println("Drink Successfully Added to Order");
        } else {
            this.currentOrder.removeFood(template);
            this.currentFood = currFood;
            System.out.println("Drink Has Been Modified");
        }
        return;
    }
    private void addPizza(Pizza template){
        if (template == null) {
            System.out.println("Choose Pizza Type:");
        } else {
            System.out.printf("Current Pizza Type: ");
            System.out.println(template.getType());
            System.out.println("Change to ... (Press Enter to Skip)");
        }

        List<String> pizzaTypes = this.currentMenu.getPizzaTypes();
        String pizzaType;
        if (template == null){
            pizzaType = pizzaTypes.get(0);
        } else {
            pizzaType = template.getType();
        }
        printOptions(pizzaTypes);

        int index = getIndexResponse(pizzaTypes);
        if (index == -1) {
            System.out.println("Invalid Option");
            return;
        }
        if (index == -2) {
            System.out.println("Defaulting to: " + pizzaType);
        } else {
            pizzaType = pizzaTypes.get(index);
        }

        if (template == null) {
            System.out.println("Choose Pizza Size:");
        } else {
            System.out.printf("Current Pizza Size: ");
            System.out.println(template.getSize());
            System.out.println("Change to ... (Press Enter to Skip)");
        }
        List<String> pizzaSizes = this.currentMenu.getPizzaSizes(pizzaType);
        String pizzaSize;
        if (template == null){
            pizzaSize = pizzaSizes.get(0);
        } else {
            pizzaSize = template.getSize();
        }
        printOptions(pizzaSizes);
        index = getIndexResponse(pizzaSizes);
        if (index == -1) {
            System.out.println("Invalid Option");
            return;
        }
        if (index == -2) {
            System.out.println("Defaulting to: " + pizzaSize);
        } else {
            pizzaSize = pizzaSizes.get(index);
        }


        if (template == null) {
            System.out.println("Choose Toppings Separated by Commas (minus sign to remove a topping):");
        } else {
            System.out.printf("Current Toppings: ");
            System.out.println(template.getToppings());
            System.out.println("Increase or Decrease (minus sign) Toppings ... (Press Enter to Skip)");
        }
        HashMap<String, Integer> pizzaToppings;
        if(template == null) {
            pizzaToppings = new HashMap<>();
            for (String topping: this.currentMenu.getPizzaToppings()){
                pizzaToppings.put(topping, 0);
            }
        } else {
            pizzaToppings = template.getToppings();
        }
        changeToppingsForPizza(pizzaToppings);

        int numExtraToppings = 0;
        for (Integer toppingNum: pizzaToppings.values()) {
            if (toppingNum > 0) numExtraToppings += toppingNum;
        }
        Food newPizza = new Pizza.Builder()
                .type(pizzaType)
                .toppings(pizzaToppings)
                .size(pizzaSize)
                .price(this.currentMenu.getPizzaPrice(pizzaType, pizzaSize, numExtraToppings))
                .build();
        this.currentOrder.addFood(newPizza);
        if (template == null){
            System.out.println("Pizza Successfully Added to Order");
        } else {
            this.currentOrder.removeFood(template);
            this.currentFood = newPizza;
            System.out.println("Pizza Has Been Modified");
        }
    }

    private void prettyPrintCurrentOrder(){
        System.out.println("List of Dishes:");
        List<Food> foods = this.currentOrder.getFoods();
        for (Food food: foods){
            System.out.println("- " + food.toString());
        }
        Delivery delivery = this.currentOrder.getDelivery();
        if (delivery == null) {
            System.out.println("Delivery Method: Pickup");
        } else {
            System.out.printf("Delivery Method: ");
            System.out.println(delivery.toString());
        }

        System.out.println("Final Price: ($" + this.currentOrder.getPrice() + ")");
    }

    private void getDeliveryDetails() {
        System.out.println("Select your Delivery Type");
        List<String> deliveryMethods = this.deliveryHandler.getDeliveryMethods();
        printOptions(deliveryMethods);
        int index = getIndexResponse(deliveryMethods);
        if (index < 0) {
            System.out.println("Invalid Option");
            return;
        }
        String deliverType = deliveryMethods.get(index);
        System.out.println("Please Enter your Address");
        String address = this.streamScanner.nextLine();
        System.out.println("Delivery Request has been Created");
        Delivery delivery = this.deliveryHandler.createDelivery(this.currentOrder, address, deliverType);
        this.currentOrder.setDelivery(delivery);
    }
    private void printOptions(List<String> allOptions) {
        for (int i = 0; i < allOptions.size(); i++){
            StringBuilder tempString = new StringBuilder("[");
            tempString.append(Integer.toString(i));
            tempString.append("] ");
            tempString.append(allOptions.get(i).toUpperCase());
            System.out.println(tempString);
        }
    }
    private void printAllOrders() {
        List <Order> allOrders = this.orderHandler.getAllOrders();

        for (Order order : allOrders) {
            StringBuilder tempString = new StringBuilder("[");
            tempString.append(order.getOrderID());
            tempString.append("] ");
            tempString.append(order.toString());
            System.out.println(tempString);
        }
    }
    private int changeToppingsForPizza(HashMap<String, Integer> toppings){
        List<String> pizzaToppings = this.currentMenu.getPizzaToppings();
        int numToppings = 0;
        if (pizzaToppings.size() > 0) {
            printOptions(pizzaToppings);
            String currLine = this.streamScanner.nextLine();
            String[] splitToppings = currLine.split(",");
            for (String topping : splitToppings){
                int index;
                if (topping.trim().equals("")) break;
                try {
                    index = Integer.parseInt(topping.trim());
                    if (topping.trim().equals("-0")) {
                        Integer initialValue = toppings.get(pizzaToppings.get(0));
                        toppings.put(pizzaToppings.get(0), initialValue - 1);
                        numToppings++;
                    } else if (index < 0 && (-index < pizzaToppings.size()) ){
                        Integer initialValue = toppings.get(pizzaToppings.get(-index));
                        toppings.put(pizzaToppings.get(-index), initialValue - 1);
                        numToppings++;
                    } else if ( index >= 0 && index < pizzaToppings.size() ){
                        Integer initialValue = toppings.get(pizzaToppings.get(index));
                        toppings.put(pizzaToppings.get(index), initialValue + 1);
                        numToppings++;
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
        return numToppings;
    }
    private int getIndexResponse (List<String> genericList) {
        String response = this.streamScanner.nextLine();
        if (response.trim().equals("")) return -2;
        int index;
        try {
            index = Integer.parseInt(response.trim());
            if (index >= 0 && (index < genericList.size()) ){
                return (index);
            } else {
                return -1;
            }
        } catch (NumberFormatException e){
            return -1;
        }
    }
}