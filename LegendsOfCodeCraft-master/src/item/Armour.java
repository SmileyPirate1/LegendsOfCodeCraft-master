package item;
import Enums.ItemType;
import Enums.SlotType;

public class Armour extends Item{
    private static final ItemType itemType = ItemType.armour;
    private int currentProtection;
    private SlotType slotType;

    //Konstruktør
    public Armour(String name, double weight, double value, int durability, int itemId, int currentProtection, SlotType slotType) {
        super(name, weight, value, durability, itemId, itemType );
        this.currentProtection = currentProtection;
        this.slotType =  slotType;
    }

    @Override
    public String toString() {
        //her fremviser vi navnet på rustningen, hvor meget den beskytter og hvor den sidder
        return "Armour: " + getName() + " Protection: " + currentProtection + " SlotType: " + slotType;
    }

    @Override
    public String useItem(){
        return "Du tager dit udstyr på";
    }
}
