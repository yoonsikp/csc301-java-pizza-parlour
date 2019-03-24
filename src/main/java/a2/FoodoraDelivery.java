package a2;

public class FoodoraDelivery extends Delivery{

    public FoodoraDelivery(DeliveryFactory.Builder builder) {
        super(builder);
    }

    public String outputDeliveryDetails(Order order){
        //as CSV
        StringBuilder deliveryCSV = new StringBuilder();
        String line1 = "Address," + this.getAddress() + "\n";
        deliveryCSV.append(line1);
        String line2 = "Order Details," + order.toString() + "\n";
        deliveryCSV.append(line2);
        String line3 = "Order Number," + order.getOrderID();
        deliveryCSV.append(line3);

        return deliveryCSV.toString();
    }


}
