package a2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * This is the TerminalInterface class.
 * The TerminalInterface is used to simulate/facilitate a User Interface through the command line, and to
 * facilitate the interactions between the command line UI and the back-end restaurant process.
 * The TerminalInterface class has multiple constructors in order to maintain low constructor tunneling.
 */
class TerminalInterface {

    /**
     * Scanner for the input stream
     */
    private final Scanner streamScanner;
    /**
     * OrderHandler for the pizza parlour
     */
    private final OrderHandler orderHandler;
    /**
     * DeliveryHandler for the pizza parlour
     */
    private final DeliveryHandler deliveryHandler;
    /**
     * Menu for the pizza parlour
     */
    private final Menu currentMenu;
    /**
     * Current order that is in use, if no order has been selected/created yet, this is null
     */
    private Order currentOrder;
    /**
     * Current food that is in use, if no food has been selected/created yet, this is null
     */
    private Food currentFood;

    /**
     * Constructor 1 calls constructor 2 with desired input stream.
     */
    TerminalInterface() {
        this(System.in);
    }

    /**
     * Constructor 2 calls constructor 3 with the desired input stream and pizza parlour's menu.
     *
     * @param inputStream input stream to read from
     */
    TerminalInterface(java.io.InputStream inputStream) {
        this(inputStream, PizzaParlour.getMenu());
    }

    /**
     * Constructor 3 calls constructor 4 with the desired input stream, pizza parlour's menu, and handlers.
     *
     * @param inputStream input stream to read from
     * @param menu        menu for pizza parlour
     */
    TerminalInterface(java.io.InputStream inputStream, Menu menu) {
        this(inputStream, menu, PizzaParlour.getOrderHandler(), PizzaParlour.getDeliveryHandler());
    }

    /**
     * Constructor 4 sets the desired input stream, pizza parlour's menu, and handlers for the TerminalInterface.
     *
     * @param inputStream     input stream to read from
     * @param menu            menu for pizza parlour
     * @param orderHandler    OrderHandler for pizza parlour
     * @param deliveryHandler DeliveryHandler for pizza parlour
     */
    TerminalInterface(java.io.InputStream inputStream, Menu menu, OrderHandler orderHandler,
                      DeliveryHandler deliveryHandler) {
        this.streamScanner = new Scanner(inputStream);
        this.orderHandler = orderHandler;
        this.deliveryHandler = deliveryHandler;
        this.currentOrder = null;
        this.currentFood = null;
        this.currentMenu = menu;
    }

    /**
     * Starts reading from the command line. Available commands are determined by the state of the TerminalReader.
     * State is determined by attributes currentOrder and currentFood, which are set in terms of whether or not an
     * order has been created and is currently in use and whether or not any food items have been added to the order.
     * It first welcomes user to program, and then is able to print a list of all possible commands at each state.
     * Depending on the commands chosen, it redirects user to other methods that enact functionalities of the commands.
     */
    void startReading() {
        // Input
        String genInput;

        System.out.println("Welcome to 301 Pizza Parlour");
        System.out.println("Type '?' at any time for help. End program by typing 'exit'.");
        System.out.print("/$ ");

        genInput = this.streamScanner.nextLine();

        while (!genInput.equals("exit")) {

            String[] command = genInput.split(" ");
            if (command[0].equals("menu") && command.length == 1) {
                // command to print entire menu
                System.out.println(this.currentMenu.toString());
            } else if (command[0].equals("menuitem") && command.length == 2) {
                // command to print a specific item on the menu
                System.out.println(this.currentMenu.getMenuItem(command[1]));
            } else if (this.currentOrder == null && this.currentFood == null) {
                if (command[0].equals("?") || command[0].equals("help")) {
                    // available commands when there is no current order or food
                    System.out.println("\tmenu               \tPrint out the Current Menu");
                    System.out.println("\tmenuitem ITEM      \tPrint out the Price of a Menu Item");
                    System.out.println("\tneworder           \tCreate an Order at the Pizza Parlour");
                    System.out.println("\tselorder [ORDER_ID]\tSelect an Order, can optionally specify ID");
                    System.out.println("\tlsorder            \tList all Current Orders and their IDs");
                } else {
                    // a command has been called, handle it
                    handleOrderState(command);
                }
            } else if (this.currentOrder != null && this.currentFood == null) {
                if (command[0].equals("?") || command[0].equals("help")) {
                    // available commands when there is a current order but no current food
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
                    //command to deselect current order
                    this.currentOrder = null;
                } else if (command[0].equals("rmorder") && command.length == 1) {
                    //command to remove current order
                    System.out.println("Order has been Canceled");
                    this.orderHandler.removeOrder(this.currentOrder);
                    this.currentOrder = null;
                } else if (command[0].equals("deliver") && command.length == 1) {
                    //command to request delivery service
                    getDeliveryDetails();
                } else if (command[0].equals("rmdeliver") && command.length == 1) {
                    //command to remove delivery service
                    this.deliveryHandler.removeDelivery(this.currentOrder);
                    System.out.println("Delivery cancelled, order will be for pick-up");
                } else if (command[0].equals("printdeliver") && command.length == 1) {
                    // command to get delivery information as JSON, CSV or text depending on delivery type
                    System.out.println(this.deliveryHandler.printDeliveryDetails(currentOrder));
                } else if (command[0].equals("printorder") && command.length == 1) {
                    // command to view order
                    prettyPrintCurrentOrder();
                } else {
                    // command regarding food has been made, handle it
                    handleFoodState(command);
                }
            } else if (this.currentOrder != null && this.currentFood != null) {
                if (command[0].equals("?") || command[0].equals("help")) {
                    // available commands when there is a current order and at least one current food in the order
                    System.out.println("\tmenu              \tPrint out the Current Menu");
                    System.out.println("\tmenuitem ITEM     \tPrint out the Price of a Menu Item");
                    System.out.println("\tchdish            \tModify the Current Dish");
                    System.out.println("\tprintdish         \tPrint Info about the Current Dish");
                    System.out.println("\trmdish            \tRemove the Current Dish from the Order");
                    System.out.println("\t..                \tDeselect Currently Selected Dish");
                } else if (command[0].equals("..") && command.length == 1) {
                    //command to deselect current dish
                    this.currentFood = null;
                } else if (command[0].equals("rmdish") && command.length == 1) {
                    //command to remove current dish
                    System.out.println("Dish has been removed");
                    this.currentOrder.removeFood(this.currentFood);
                    this.currentFood = null;
                } else if (command[0].equals("printdish") && command.length == 1) {
                    //command to display info about current dish
                    System.out.println(this.currentFood.toString());
                } else if (command[0].equals("chdish") && command.length == 1) {
                    //command to change current dish
                    if (this.currentFood instanceof Drink) {
                        addDrink((Drink) this.currentFood);
                    } else if (this.currentFood instanceof Pizza) {
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
                tempSB.append(this.currentFood.getClass().getSimpleName()).append("-")
                        .append(this.currentFood.getType()
                                .toUpperCase());
            }
            tempSB.append("$ ");
            System.out.print(tempSB.toString());
            genInput = this.streamScanner.nextLine();

        }
        this.streamScanner.close();
    }

    /**
     * This method handles the provided command depending on the state of the TerminalInterface. The states are
     * determined by whether or not there is a current order.
     *
     * @param command the command provided in the UI
     */
    private void handleOrderState(String[] command) {
        if (command[0].equals("neworder") && command.length == 1) {
            // command to create a new order
            System.out.println("Order Created: Try 'newpizza'");
            this.currentOrder = this.orderHandler.createOrder();
            return;
        } else if (command[0].equals("selorder") && command.length == 2) {
            // command to select an order with orderID provided
            this.currentOrder = this.orderHandler.getOrder(command[1]);
            if (currentOrder == null) {
                System.out.println("Order ID not found");
            } else {
                System.out.println("Order Selected:");
            }
            return;
        } else if (command[0].equals("selorder") && command.length == 1) {
            // command to select an order with no orderID provided, so print all orders
            List<Order> allOrders = this.orderHandler.getAllOrders();
            if (allOrders.size() == 0) {
                System.out.println("No Current Orders");
                return;
            }
            System.out.println("Choose Order ID:");
            printAllOrders();

            this.currentOrder = this.orderHandler.getOrder(this.streamScanner.nextLine().trim());
            if (currentOrder == null) {
                System.out.println("Order ID not found");
            }
            return;
        } else if (command[0].equals("lsorder") && command.length == 1) {
            // command to print all orders
            List<Order> allOrders = this.orderHandler.getAllOrders();
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

    /**
     * This method handles the provided command depending on the state of the TerminalInterface. The states are
     * determined by whether or not there is a current food in the current order.
     *
     * @param command the command provided in the UI
     */
    private void handleFoodState(String[] command) {
        if (command[0].equals("newdrink") && command.length == 1) {
            // command to create a new drink
            addDrink(null);
            return;
        } else if (command[0].equals("newpizza") && command.length == 1) {
            // command to create a new pizza
            addPizza(null);
            return;
        } else if (command[0].equals("seldish") && command.length == 1) {
            //command to select a new dish, print a list of all dishes in order
            List<Food> allFoods = this.currentOrder.getFoods();
            if (allFoods.size() > 0) {
                System.out.println("Choose Dish from Order:");
                List<String> allFoodStrings = new ArrayList<>();
                Food food = allFoods.get(0);
                for (Food iterFood : allFoods) {
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
            // command to print a list of all dishes in order
            List<Food> allFoods = this.currentOrder.getFoods();
            if (allFoods.size() > 0) {
                for (Food food : allFoods) {
                    System.out.println("- " + food.toString());
                }
            } else {
                System.out.println("No Dishes in Order");
            }
            return;
        }
        System.out.println("Invalid Command");
    }

    /**
     * This method handles adding a new Drink to the order. It asks the user for the drink type and then facilitates
     * creation of a new Drink.
     *
     * @param template Drink item that can be passed in as a current drink
     */
    private void addDrink(Drink template) {
        if (template == null) {
            System.out.println("Choose a Drink:");
        } else {
            System.out.print("Current Drink Type: ");
            System.out.println(template.getType());
            System.out.println("Change to ... (Press Enter to Skip)");
        }
        List<String> drinks = this.currentMenu.getDrinks();
        String drink;
        if (template == null) {
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
        if (template == null) {
            System.out.println("Drink Successfully Added to Order");
        } else {
            this.currentOrder.removeFood(template);
            this.currentFood = currFood;
            System.out.println("Drink Has Been Modified");
        }
    }

    /**
     * This method handles adding a new Pizza to the order. It asks the user for the Pizza type, size and toppings and
     * then facilitates creation of a new Pizza.
     *
     * @param template Pizza item that can be passed in as a current Pizza
     */
    private void addPizza(Pizza template) {
        if (template == null) {
            System.out.println("Choose Pizza Type:");
        } else {
            System.out.print("Current Pizza Type: ");
            System.out.println(template.getType());
            System.out.println("Change to ... (Press Enter to Skip)");
        }

        List<String> pizzaTypes = this.currentMenu.getPizzaTypes();
        String pizzaType;
        if (template == null) {
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
            System.out.print("Current Pizza Size: ");
            System.out.println(template.getSize());
            System.out.println("Change to ... (Press Enter to Skip)");
        }
        List<String> pizzaSizes = this.currentMenu.getPizzaSizes(pizzaType);
        String pizzaSize;
        if (template == null) {
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
            System.out.print("Current Toppings: ");
            System.out.println(template.getToppings());
            System.out.println("Increase or Decrease (minus sign) Toppings ... (Press Enter to Skip)");
        }
        HashMap<String, Integer> pizzaToppings;
        if (template == null) {
            pizzaToppings = new HashMap<>();
            for (String topping : this.currentMenu.getPizzaToppings()) {
                pizzaToppings.put(topping, 0);
            }
        } else {
            pizzaToppings = template.getToppings();
        }
        changeToppingsForPizza(pizzaToppings);

        int numExtraToppings = 0;
        for (Integer toppingNum : pizzaToppings.values()) {
            if (toppingNum > 0) {
                numExtraToppings += toppingNum;
            }
        }
        Food newPizza = new Pizza.Builder()
                .type(pizzaType)
                .toppings(pizzaToppings)
                .size(pizzaSize)
                .price(this.currentMenu.getPizzaPrice(pizzaType, pizzaSize, numExtraToppings))
                .build();
        this.currentOrder.addFood(newPizza);
        if (template == null) {
            System.out.println("Pizza Successfully Added to Order");
        } else {
            this.currentOrder.removeFood(template);
            this.currentFood = newPizza;
            System.out.println("Pizza Has Been Modified");
        }
    }

    /**
     * This method facilitates print to the screen of the current order.
     */
    private void prettyPrintCurrentOrder() {
        System.out.println("List of Dishes:");
        List<Food> foods = this.currentOrder.getFoods();
        for (Food food : foods) {
            System.out.println("- " + food.toString());
        }
        Delivery delivery = this.currentOrder.getDelivery();
        if (delivery == null) {
            System.out.println("Delivery Method: Pickup");
        } else {
            System.out.print("Delivery Method: ");
            System.out.println(delivery.toString());
        }

        System.out.println("Final Price: ($" + this.currentOrder.getPrice() + ")");
    }

    /**
     * This method facilitates printing to the screen details of the delivery associated with the current order
     * as JSON, CSV or text depending on the delivery type.
     */
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
        Delivery delivery = this.deliveryHandler
                .createDelivery(this.currentOrder, address, deliverType);
        this.currentOrder.setDelivery(delivery);
    }

    /**
     * This method prints all options in a given list with associated numbers so that the user can choose which option
     * they want.
     *
     * @param allOptions list of options to display
     */
    private void printOptions(List<String> allOptions) {
        for (int i = 0; i < allOptions.size(); i++) {
            StringBuilder tempString = new StringBuilder("[");
            tempString.append(Integer.toString(i));
            tempString.append("] ");
            tempString.append(allOptions.get(i).toUpperCase());
            System.out.println(tempString);
        }
    }

    /**
     * This method prints all orders in the restaurant.
     */
    private void printAllOrders() {
        List<Order> allOrders = this.orderHandler.getAllOrders();

        for (Order order : allOrders) {
            StringBuilder tempString = new StringBuilder("[");
            tempString.append(order.getOrderID());
            tempString.append("] ");
            tempString.append(order.toString());
            System.out.println(tempString);
        }
    }

    /**
     * This method handles changing toppings for a pizza.
     *
     * @param toppings toppings to add to the pizza
     */
    private void changeToppingsForPizza(HashMap<String, Integer> toppings) {
        List<String> pizzaToppings = this.currentMenu.getPizzaToppings();
        if (pizzaToppings.size() > 0) {
            printOptions(pizzaToppings);
            String currLine = this.streamScanner.nextLine();
            String[] splitToppings = currLine.split(",");
            for (String topping : splitToppings) {
                int index;
                if (topping.trim().equals("")) {
                    break;
                }
                try {
                    index = Integer.parseInt(topping.trim());
                    if (topping.trim().equals("-0")) {
                        Integer initialValue = toppings.get(pizzaToppings.get(0));
                        toppings.put(pizzaToppings.get(0), initialValue - 1);
                    } else if (index < 0 && (-index < pizzaToppings.size())) {
                        Integer initialValue = toppings.get(pizzaToppings.get(-index));
                        toppings.put(pizzaToppings.get(-index), initialValue - 1);
                    } else if (index >= 0 && index < pizzaToppings.size()) {
                        Integer initialValue = toppings.get(pizzaToppings.get(index));
                        toppings.put(pizzaToppings.get(index), initialValue + 1);
                    } else {
                        System.out.print("Skipping unknown topping:");
                        System.out.println(topping);
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Skipping unknown topping:");
                    System.out.println(topping);
                }
            }
        }
    }

    /**
     * This method handles receiving an index response to a list displayed by method printOptions.
     *
     * @param genericList List from which user chooses
     * @return the int reresenting the index in the list the user chose
     */
    private int getIndexResponse(List<String> genericList) {
        String response = this.streamScanner.nextLine();
        if (response.trim().equals("")) {
            return -2;
        }
        int index;
        try {
            index = Integer.parseInt(response.trim());
            if (index >= 0 && (index < genericList.size())) {
                return (index);
            } else {
                return -1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}