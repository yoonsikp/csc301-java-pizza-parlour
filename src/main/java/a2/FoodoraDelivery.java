package a2;

import java.util.StringJoiner;

/**
 * This is the FoodoraDelivery class.
 * The FoodoraDelivery class extends the Delivery class and inherits all of its characteristics.
 * The FoodoraDelivery class can output its delivery details in CSV format.
 */
public class FoodoraDelivery extends Delivery {

    /**
     * Constructor for a Foodora delivery.
     *
     * @param builder the builder that constructs the delivery
     */
    public FoodoraDelivery(Delivery.Builder builder) {
        super(builder);
    }

    /**
     * Returns a CSV representation of the delivery details of a given FoodoraDelivery.
     *
     * @param order the order whose delivery details we want
     * @return CSV representation of order's delivery
     */
    public String outputDeliveryDetails(Order order) {
        //as CSV
        StringBuilder deliveryCSV = new StringBuilder();
        String line1 = "Address,Order Details,Order Number\n";
        deliveryCSV.append(line1);
        String addressLine = this.getAddress() + ",";
        deliveryCSV.append(addressLine);
        StringJoiner sj = new StringJoiner(" + ");
        for (Food food : order.getFoods()) {
            sj.add(food.toString().replaceAll(", ", " ~ "));
        }
        deliveryCSV.append(sj.toString());
        deliveryCSV.append(",").append(order.getOrderID());

        return deliveryCSV.toString();
    }


}
