package item;

import Enums.HandType;

import Enums.SlotType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ItemFactory {
    private final Map<Integer, Weapon> weaponCatalog = new HashMap<>();
    private final Map<Integer, Armour> armourCatalog = new HashMap<>();
    private final Map<Integer, Consumable> consumableCatalog = new HashMap<>();

    public ItemFactory() {
        weaponCatalog.put(1, new Weapon("Rusten kniv", 1.0, 3, 100, 1, 10, HandType.offHand));
        weaponCatalog.put(2, new Weapon("Mystik Excalibur", 8.0, 20, 100, 2, 30, HandType.oneHand));
        weaponCatalog.put(3, new Weapon("Stor hammer", 15.0, 15, 100, 3, 50, HandType.twoHand));
        armourCatalog.put(1, new Armour("Ring brynje", 5.0, 4, 50, 4, 5, SlotType.chest));
        armourCatalog.put(2, new Armour("Læder sko", 1.0, 2, 10, 5, 5, SlotType.feet));
        armourCatalog.put(3, new Armour("Træspand", 2.0, 1, 20, 6, 3, SlotType.head));
        armourCatalog.put(4, new Armour("Skildpade buckler", 3.0, 6, 25, 7, 10, SlotType.shield));
        armourCatalog.put(5, new Armour("Fjer Kape", 1.0, 3, 15, 8, 1, SlotType.cloak));
        armourCatalog.put(6, new Armour("Diamand bukser", 10.0, 20, 10, 9, 10, SlotType.legs));
        armourCatalog.put(7, new Armour("Større skulderpanser", 2, 3, 30, 10, 15, SlotType.shoulders));
        armourCatalog.put(8, new Armour("Lysende Handsker", 1.0, 2, 74, 11, 0, SlotType.hands));
        consumableCatalog.put(1, new Consumable("Livs eliksir", 0.10, 2, 1, 12, 1, 10, "Dine sår forsvinder"));
        consumableCatalog.put(2, new Consumable("Røg bombe", 0.250, 4, 1, 13, 1, 3, "Du laver en røg sky"));
        consumableCatalog.put(3, new Consumable("Fart eliksir", 0.10, 4, 1, 14, 1, 5, "Du bliver meget hurtig"));
        consumableCatalog.put(4, new Consumable("Teleporterings rulle", 0.010,30,1,15,1,10,"Du bliver teleporteret til den nærmeste by"));
    }

    public Item getItem(int choice) {
        Random rand = new Random();
        int randomWeapon = rand.nextInt(3) + 1;
        int randomArmour = rand.nextInt(8) + 1;
        int randomConsumable = rand.nextInt(4) + 1;
        Item newItem = null;
        switch (choice) {
            case 1:
                newItem = weaponCatalog.get(randomWeapon);
            break;
            case 2:
                newItem = armourCatalog.get(randomArmour);
            break;
            case 3:
                newItem = consumableCatalog.get(randomConsumable);
            break;
        }
        return newItem;
    }
}

