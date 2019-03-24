package a2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.StringJoiner;

public class UbereatsDelivery extends Delivery {

    /**
     * Constructor for a Foodora delivery.
     * @param builder the builder that constructs the delivery
     */
    public UbereatsDelivery(Delivery.Builder builder) {
        super(builder);
    }

    /**
     * Returns a JSON representation of the delivery details of a given UbereatsDelivery.
     * @param order the order whose delivery details we want
     * @return JSON representation of order's delivery
     */
    public String outputDeliveryDetails(Order order){

        HashMap<String, String> deliveryDetailsJSON = new HashMap<>();
        deliveryDetailsJSON.put("Address", order.getDelivery().getAddress());
        StringJoiner sj = new StringJoiner(" + ");
        for(Food food : order.getFoods()){
            sj.add(food.toString().replaceAll(", ", " ~ "));
        }
        deliveryDetailsJSON.put("Order Details", sj.toString());
        deliveryDetailsJSON.put("Order Number", order.getOrderID());

    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    return gson.toJson(deliveryDetailsJSON);
  }
}