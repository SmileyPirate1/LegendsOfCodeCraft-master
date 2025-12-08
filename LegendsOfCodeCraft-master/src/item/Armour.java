package item;
import Enums.SlotType;

public class Armour extends Item{
    private int currentProtection;
    private SlotType slotType;

    //Konstruktør
    public Armour(String name, double weight, double value, int durability, int itemId, int currentProtection, SlotType slotType) {
        super(name, weight, value, durability, itemId );
        this.currentProtection = currentProtection;
        this.slotType =  slotType;
    }

    @Override
    public String toString() {
        //her fremviser vi navnet på rustningen, hvor meget den beskytter og hvor den sidder
        return "Armour: " + getName() + " Protection: " + currentProtection + " SlotType: " + slotType;
    }
}
