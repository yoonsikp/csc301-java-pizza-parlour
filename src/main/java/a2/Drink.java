package a2;

public class Drink extends Food{

    public String toString(){
        StringBuilder drinkString = new StringBuilder();
        drinkString.append(this.getType());
        drinkString.append(" ($");
        drinkString.append(this.getPrice());
        drinkString.append(")");

        return drinkString.toString();
    }

}
