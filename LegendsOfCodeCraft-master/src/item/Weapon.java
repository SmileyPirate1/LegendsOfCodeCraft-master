package item;

import Enums.HandType;
import Enums.ItemType;

public class Weapon extends Item{
    private static final ItemType itemType = ItemType.weapon;
    private int currentDamage;
    private HandType handType;



    //Konstruktør
    public Weapon(String name, double weight, double value, int durability, int itemId, int currentDamage,  HandType handType) {
        super(name, weight, value, durability, itemId, itemType);
        this.currentDamage = currentDamage;
        this.handType = handType;

    }



    @Override
    public String toString() {
        //her viser vi navn, skade og hånd type våbnet bruger
        return "Weapon: " + getName() + " Damage: " + currentDamage + " Handtype: " + handType;
    }

    @Override
    public String useItem(){
        return "Du slår med dit våben";
    }



}
