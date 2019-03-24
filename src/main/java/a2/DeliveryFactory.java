package a2;

public class DeliveryFactory {
    public static class Builder {
        private String address;
        private String type;
        private int status;

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder status() {
            this.status = 0;
            return this;
        }

        public int getStatus() {
            return status;
        }

        public String getAddress() {
            return address;
        }

        public String getType() {
            return type;
        }

        public Delivery build(){
            if(this.type.equals("ubereats")) {
                return new UbereatsDelivery(this);
            }else if(this.type.equals("foodora")){
                return new FoodoraDelivery(this);
            }else{
                return new HouseDelivery(this);
            }
        }

    }

    public Delivery createDelivery(Order currOrder, String address, String delivType) {
        Delivery currDelivery = new DeliveryFactory.Builder().address(address)
                .type(delivType).status().build();
        return currDelivery;

    }
}
