package a2;



public abstract class Delivery {

    public static class Builder {
        private String address;
        private String type;
        private String orderID;

        /**
         * Sets builder's address.
         * @param address address to set to
         * @return current builder instance
         */
        Builder address(String address) {
            this.address = address;
            return this;
        }

        /**
         * Sets builder's type.
         * @param type type to set to
         * @return current builder instance
         */
        Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets builder's orderID.
         * @param orderID orderID to set to
         * @return current builder instance
         */
        Builder orderID(String orderID) {
            this.orderID = orderID;
            return this;
        }

        /**
         * Build the desired Delivery type depending on the builder's type. Returns an UbereatsDelivery if the type
         * is ubereats, a FoodoraDelivery if the type is foodora, and a HouseDelivery if the type is house.
         * @return new Delivery object
         */
        Delivery build(){
            if(this.type.equals("ubereats")) {
                return new UbereatsDelivery(this);
            }else if(this.type.equals("foodora")){
                return new FoodoraDelivery(this);
            }else{
                return new HouseDelivery(this);
            }
        }

    }

    private String address;
    private String type;
    private String orderID;

    /**
     * Constructor for a Delivery object.
     * @param builder the builder that builds a Delivery
     */
    Delivery(Delivery.Builder builder) {
        this.address = builder.address;
        this.type = builder.type;
        this.orderID = builder.orderID;
    }

    /**
     * Getter for this delivery's address
     * @return this delivery's address
     */
     String getAddress(){
        return this.address;
    }

    /**
     * Getter for this delivery's type
     * @return this delivery's type
     */
    private String getType() {
        return type;
    }

    /**
     * Abstract method for returning a representation of the delivery details of a given Delivery. This method will
     * be implemented in extending classes.
     * @param order the order whose delivery details we want
     * @return delivery details for the delivery of order
     */
    public abstract String outputDeliveryDetails(Order order);

    /**
     * Returns a string representation of a Delivery object.
     * @return String representation of a Delivery
     */
    public String toString(){
        return this.getType() + " to " + this.getAddress() + " for Order: " + orderID;
    }
}
