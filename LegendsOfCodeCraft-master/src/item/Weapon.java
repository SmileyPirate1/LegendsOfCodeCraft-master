package item;

import Enums.HandType;
import Enums.ItemType;

public class Weapon extends Item{
    private static final ItemType itemType = ItemType.weapon;
    private int damage;
    private HandType handType;

    //Konstruktør
    public Weapon(String name, double weight, double value, int durability, int itemId, int damage,  HandType handType) {
        super(name, weight, value, durability, itemId, itemType);
        this.damage = damage;
        this.handType = handType;
    }

    @Override
    public String toString() {
        //her viser vi navn, skade og hånd type våbnet bruger
        return "Weapon: " + getName() + " Damage: " + damage + " Vægt: " + getWeight() + " Handtype: " + handType;
    }

    @Override
    public String useItem(){
        return "Du slår med dit våben";
    }
}
