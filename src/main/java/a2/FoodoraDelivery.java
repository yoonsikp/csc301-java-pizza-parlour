package a2;

import java.util.StringJoiner;

public class FoodoraDelivery extends Delivery{

    public FoodoraDelivery(Delivery.Builder builder) {
        super(builder);
    }

    public String outputDeliveryDetails(Order order){
        //as CSV
        StringBuilder deliveryCSV = new StringBuilder();
        String line1 = "Address,Order Details,Order Number\n";
        deliveryCSV.append(line1);
        String addressLine = this.getAddress() + ",";
        deliveryCSV.append(addressLine);
        StringJoiner sj = new StringJoiner(" + ");
        for(Food food : order.getFoods()){
            sj.add(food.toString().replaceAll(", ", " ~ "));
        }
        deliveryCSV.append(sj.toString());
        deliveryCSV.append(",").append(order.getOrderID());

        return deliveryCSV.toString();
    }


}
