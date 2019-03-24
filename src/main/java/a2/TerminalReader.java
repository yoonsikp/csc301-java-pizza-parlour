package a2;

import java.util.List;
import java.util.Scanner;



public class TerminalReader {
    private java.io.InputStream inputStream;
    private Scanner streamScanner;
    private OrderHandler orderHandler;
    private DeliveryHandler deliveryHandler;
    private Order currentOrder;
    private Food currentFood;
    private Menu currentMenu;

    public TerminalReader(){
        this(System.in);
    }

    public TerminalReader(java.io.InputStream inputStream){
        this(inputStream, PizzaParlour.getMenu());
    }

    public TerminalReader(java.io.InputStream inputStream, Menu menu){
        this(inputStream, menu, PizzaParlour.getOrderHandler(), PizzaParlour.getDeliveryHandler());
    }

    public TerminalReader(java.io.InputStream inputStream, Menu menu, OrderHandler orderHandler,
                          DeliveryHandler deliveryHandler){
        this.inputStream = inputStream;
        this.streamScanner = new Scanner(this.inputStream);
        this.orderHandler = orderHandler;
        this.deliveryHandler = deliveryHandler;
        this.currentOrder = null;
        this.currentFood = null;
        this.currentMenu = menu;
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

    private void handleOrderState(String[] command){
        if (command[0].equals("neworder") && command.length == 1) {
            System.out.println("Order Created: Try 'newdish pizza'");
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

    private int addToppingsToPizza(Pizza currPizza){
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
                        currPizza.removeTopping(pizzaToppings.get(0));
                        numToppings++;
                    } else if (index < 0 && (-index < pizzaToppings.size()) ){
                        currPizza.removeTopping(pizzaToppings.get(-index));
                        numToppings++;
                    } else if ( index < pizzaToppings.size() ){
                        currPizza.addTopping(pizzaToppings.get(index));
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
        if (response.trim().equals("")) return -1;
        int index;
        try {
            index = Integer.parseInt(response.trim());
            if (index >= 0 && (index < genericList.size()) ){
                return (index);
            } else {
                System.out.println("Invalid Option");
                return -1;
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid Option");
            return -1;
        }
    }

    private void handleFoodState(String[] command){
        if (command[0].equals("newdish") && command.length == 2) {
            if (command[1].toLowerCase().equals("drink")) {
                System.out.println("Choose a Drink:");
                List<String> drinks = this.currentMenu.getDrinks();
                printOptions(drinks);
                int index = getIndexResponse(drinks);
                if (index == -1) {
                    return;
                }

                String drink = drinks.get(index);
                DrinkFactory df = new DrinkFactory();
                Drink currDrink = df.makeFood();
                currDrink.setType(drink);
                currDrink.setPrice(this.currentMenu.getDrinkPrice(drink));
                this.currentOrder.addFood(currDrink);
                System.out.println("Drink Successfully Added to Order");
                return;
            }
            if (command[1].toLowerCase().equals("pizza")){
                System.out.println("Choose Pizza Type:");
                List<String> pizzaTypes = this.currentMenu.getPizzaTypes();
                printOptions(pizzaTypes);

                int index = getIndexResponse(pizzaTypes);
                if (index == -1) {
                    return;
                }
                String pizzaType = pizzaTypes.get(index);

                System.out.println("Choose Pizza Size:");
                List<String> pizzaSizes = this.currentMenu.getPizzaSizes(pizzaType);
                printOptions(pizzaSizes);
                index = getIndexResponse(pizzaSizes);
                if (index == -1) {
                    return;
                }

                String pizzaSize = pizzaSizes.get(index);
                PizzaFactory pf = new PizzaFactory();
                Pizza currPizza = pf.makeFood();
                currPizza.setType(pizzaType);
                currPizza.setSize(pizzaSize);
                currPizza.setPrice(this.currentMenu.getPizzaPrice(pizzaType, pizzaSize));
                List<String> pizzaToppings = this.currentMenu.getPizzaToppings();
                if (pizzaToppings.size() > 0) {
                    System.out.println("Choose Toppings Separated by Commas (minus sign to remove a topping):");
                    addToppingsToPizza(currPizza);
                }
                this.currentOrder.addFood(currPizza);
                System.out.println("Pizza Successfully Added to Order");
                return;
            }
        } else if (command[0].equals("seldish") && command.length == 1) {
            List<Food> allFoods = this.currentOrder.getFoods();
            if (allFoods.size() >0) {
                System.out.println("Choose Dish from Order:");
                for (int i = 0; i < allFoods.size(); i++){
                    StringBuilder tempString = new StringBuilder("[");
                    tempString.append(Integer.toString(i));
                    tempString.append("] ");
                    tempString.append(allFoods.get(i).toString());
                    System.out.println(tempString);
                }
                //TODO add more checks on line
                this.currentFood = allFoods.get(Integer.parseInt(this.streamScanner.nextLine()));
            } else {
                System.out.println("No Dishes in Order");
            }
            return;
        } else if (command[0].equals("lsdish") && command.length == 1) {
            List<Food> allFoods = this.currentOrder.getFoods();
            if (allFoods.size() >0) {
                for (Food food: allFoods){
                    System.out.println(food.toString());
                }
            } else {
                System.out.println("No Dishes in Order");
            }
            return;
        }
        System.out.println("Invalid Command");
    }

    private void modifyDish() {

        if (this.currentFood instanceof Drink) {
            System.out.printf("Current Drink Type: ");
            System.out.println(this.currentFood.getType());
            System.out.println("Change to ... (Press Enter to Skip)");
            List<String> drinks = this.currentMenu.getDrinks();
            printOptions(drinks);
            String response = this.streamScanner.nextLine();
            if (!response.equals("")) {
                int index;
                try {
                    index = Integer.parseInt(response.trim());
                    if (index >= 0 && (index < drinks.size()) ){
                        this.currentFood.setType(drinks.get(index));
                        System.out.println("Drink Has Been Modified");
                        return;
                    }
                } catch (NumberFormatException e){}
                System.out.println("Invalid Option");
            }
            System.out.println("Drink Not Modified");

        } else if (this.currentFood instanceof Pizza){
            boolean modBit = false;
            Pizza currPizza = (Pizza) this.currentFood;
            System.out.printf("Current Pizza Type: ");
            System.out.println(currPizza.getType());
            System.out.println("Change to ... (Press Enter to Skip)");
            List<String> pizzaTypes = this.currentMenu.getPizzaTypes();
            printOptions(pizzaTypes);
            String response = this.streamScanner.nextLine();
            if (!response.equals("")) {
                int index;
                try {
                    index = Integer.parseInt(response.trim());
                    if (index >= 0 && (index < pizzaTypes.size()) ){
                        currPizza.setType(pizzaTypes.get(index));
                        modBit = true;
                    } else {
                        System.out.println("Invalid Option");
                        System.out.println("Drink Not Modified");
                        return;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Invalid Option");
                    System.out.println("Pizza Not Modified");
                    return;
                }
            }

            System.out.printf("Current Pizza Size: ");
            System.out.println(currPizza.getSize());
            System.out.println("Change to ... (Press Enter to Skip)");
            List<String> pizzaSizes = this.currentMenu.getPizzaSizes(currPizza.getType());
            printOptions(pizzaSizes);
            response = this.streamScanner.nextLine();

            if (!response.equals("")) {
                int index;
                try {
                    index = Integer.parseInt(response.trim());
                    if (index >= 0 && (index < pizzaSizes.size()) ){
                        currPizza.setSize(pizzaSizes.get(index));
                        modBit = true;
                    } else {
                        System.out.println("Invalid Option");
                        if (modBit) {
                            System.out.println("Pizza Has Been Modified");
                        } else {
                            System.out.println("Pizza Not Modified");
                        }
                        return;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Invalid Option");
                    if (modBit) {
                        System.out.println("Pizza Has Been Modified");
                    } else {
                        System.out.println("Pizza Not Modified");
                    }
                    return;
                }
            }

            List<String> pizzaToppings = this.currentMenu.getPizzaToppings();
            if (pizzaToppings.size() > 0) {
                System.out.printf("Current Toppings: ");
                System.out.println(currPizza.getToppings());
                System.out.println("Increase or Decrease (minus sign) Toppings ... (Press Enter to Skip)");
                if (addToppingsToPizza(currPizza) > 0) modBit = true;
            }

            if (modBit) {
                System.out.println("Pizza Has Been Modified");
            } else {
                System.out.println("Pizza Not Modified");
            }
        } else {
            System.out.println("Dish Type can't be modified");
        }
    }
    private void prettyPrintCurrentOrder(){
        System.out.println("List of Dishes");
        List<Food> foods = this.currentOrder.getFoods();
        for (Food food: foods){
            System.out.println("- " + food.toString());
        }
        Delivery deliv = this.currentOrder.getDelivery();
        if (deliv == null) {
            System.out.println("Delivery Method: Pickup");
        } else {
            System.out.printf("Delivery Method: ");
            System.out.println(deliv.toString());
        }
        System.out.printf("Final Price: ");
        System.out.println(this.currentOrder.getPrice());
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
                    System.out.println("\tdelivdetails      \tView Delivery Details");
                    System.out.println("\tprintorder        \tDetails about the Current Order");
                    System.out.println("\tnewdish pizza     \tAdd a Pizza to the Current Order");
                    System.out.println("\tnewdish drink     \tAdd a Drink to the Current Order");
                    System.out.println("\tseldish           \tSelect a Dish in the Current Order");
                    System.out.println("\tlsdish            \tList all Dishes in the Current Order");
                    System.out.println("\t..                \tDeselect Currently Selected Order");
                } else if (command[0].equals("..") && command.length == 1) {
                    this.currentOrder = null;
                }
                else if (command[0].equals("rmorder") && command.length == 1) {
                    System.out.println("we canceled the order, you can now create other orders");
                    this.orderHandler.removeOrder(this.currentOrder);
                    this.currentOrder = null;
                }
                else if (command[0].equals("deliver") && command.length == 1) {
                    getDeliveryDetails();
                }
                else if (command[0].equals("rmdeliver") && command.length == 1) {
                    this.deliveryHandler.removeDelivery(this.currentOrder);
                    System.out.println("we have cancelled the delivery, your item will be ready for pick-up");
                }
                else if (command[0].equals("delivdetails") && command.length ==1){
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
                    modifyDish();
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
                tempSB.append(this.currentFood.toString());
            }
            tempSB.append("$ ");
            System.out.printf(tempSB.toString());
            genInput = this.streamScanner.nextLine();

        }
        this.streamScanner.close();
    }

    private void getDeliveryDetails() {
        System.out.println("Select your delivery type by number");

        List<String> delivMethods = this.deliveryHandler.getDeliveryMethods();

        printOptions(delivMethods);

        int delivTypeIndex = Integer.parseInt(this.streamScanner.nextLine());
        System.out.println("Please tell us your address");
        String address = this.streamScanner.nextLine();
        System.out.println("we will deliver to the place for you!");
        Delivery delivery = this.deliveryHandler.createDelivery(this.currentOrder, address, delivTypeIndex);
        this.currentOrder.setDelivery(delivery);
    }


}
