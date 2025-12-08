package item;

public class Item {
    private String name;
    private double weight;
    private double value;
    private int durability;
    private int itemId;

    //Konstruktør
    public Item(String name, double weight, double value, int durability, int itemId) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.durability = durability;
        this.itemId = itemId;
    }

    //getter til ar vi kan kalde værdierne i itemet
    public String getName(){
        return name;
    }
    public double getWeight(){
        return weight;
    }
    public double getValue(){
        return value;
    }
    public int getDurability(){
        return durability;
    }
    public int  getItemId(){
        return itemId;
    }
}
