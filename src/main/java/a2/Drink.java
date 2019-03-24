package a2;

public class Drink extends Food{

    public static class Builder extends Food.FoodBuilder<Builder> {
        @Override protected Builder getThis() {
            return this;
        }
        public Food build(){
            return new Drink(this);
        }
    }
    private Drink(Builder builder){
        super(builder);
    }
    public String toString(){
        return this.getType() +
                " ($" + this.getPrice() + ")";
    }

}
