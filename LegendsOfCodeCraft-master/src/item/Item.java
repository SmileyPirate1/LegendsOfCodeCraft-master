package item;

//Item er abstract da vi ikke direkte opretter nogle instanser af item. Vi har istedet subklasser (consumable, weapon og armour)
public abstract class Item {
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

    public String serialize (){
        String type = this.getClass().getSimpleName();
        return String.join(",",
                "ITEM",
                type,
                escape(name),
                String.valueOf(weight),
                String.valueOf(value),
                String.valueOf(durability),
                String.valueOf(itemId));
    }
    protected static String escape(String s){
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace(",", "\\,");
    }
    protected static String unescape(String s){
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace(",", "\\,");
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
