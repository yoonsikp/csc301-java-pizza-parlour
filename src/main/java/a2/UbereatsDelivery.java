package a2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.StringJoiner;

public class UbereatsDelivery extends Delivery {

  public UbereatsDelivery(Delivery.Builder builder) {
    super(builder);
  }

  public String outputDeliveryDetails(Order order) {
    //as JSON

    HashMap<String, String> deliveryDetailsJSON = new HashMap<>();
    deliveryDetailsJSON.put("Address", order.getDelivery().getAddress());
    StringJoiner sj = new StringJoiner(" + ");
    for (Food food : order.getFoods()) {
      sj.add(food.toString().replaceAll(", ", " ~ "));
    }
    deliveryDetailsJSON.put("Order Details", sj.toString());
    deliveryDetailsJSON.put("Order Number", order.getOrderID());

    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    return gson.toJson(deliveryDetailsJSON);
  }
}