package item;
import Enums.ItemType;

//Item er abstract da vi ikke direkte opretter nogle instanser af item. Vi har istedet subklasser (consumable, weapon og armour)
public abstract class Item {
    private String name;
    private double weight;
    private double value;
    private int durability;
    private int itemId;
    private ItemType itemType;

    //Konstruktør
    public Item(String name, double weight, double value, int durability, int itemId,  ItemType itemType) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.durability = durability;
        this.itemId = itemId;
        this.itemType = itemType;
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

    public abstract String useItem();
}
