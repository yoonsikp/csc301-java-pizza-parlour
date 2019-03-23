package a2;

public class Food {
    private String type;
    private Float price;
    public String getType() {
        return this.type;
    }
    public Float getPrice(){ return this.price; }
    public void setPrice(Float newPrice){ this.price = newPrice;}

    public void setType(String type) {
        this.type = type;
    }
}
