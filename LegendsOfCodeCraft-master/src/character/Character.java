package character;

import java.util.ArrayList;
import java.util.Collections;

import item.Armour;
import item.Consumable;
import item.Item;
import item.Weapon;

public class Character {
    private String name;
    private int level;
    private int inventoryId;
    private int characterId;
    private final Inventory inventory;

    //Konstruktør
    public Character(String name, int level, int inventoryId, int characterId) {
        this.name = name;
        this.level = level;
        this.inventoryId = inventoryId;
        this.characterId = characterId;

        //Her opretter vi også et inventory
        this.inventory = new Inventory(32, 50, 0, 0, 1, inventoryId);
    }

    public String getName() {
        return name;
    }

    public boolean addItem(Item item) {
        return inventory.addItem(item);
    }

    public java.util.List<Item> getInventory() {
        return inventory.getItems();
    }

    public String showInventory() {
        StringBuilder output = new StringBuilder();
        output.append("Inventar: \n");
        for (int i = 0; i < inventory.getItems().size(); i++) {
            output.append("Slot: ").append(+i + 1).append(" ").append(inventory.getItems().get(i)).append("\n");
        }
        return output.toString();
    }


    public double getCurrentWeight() {
        return inventory.getCurrentWeight();
    }

    public double getMaxWeight() {
        return inventory.getMaxWeight();
    }

    public boolean useWeapon() {
        for (Item i : inventory.getItems()) {
            if (i instanceof Weapon) {
                return true;
            }
        }
        return false;
    }

    public boolean useArmour() {
        for (Item i : inventory.getItems()) {
            if (i instanceof Armour) {
                return true;
            }
        }
        return false;
    }

    public boolean useConsumable() {
        for (Item i : inventory.getItems()) {
            if (i instanceof Consumable) {
                return true;
            }
        }
        return false;
    }

    //Metode til at fjerne items
    public void removeItem(int slot) {
        Item i = inventory.getItems().get(slot);
        inventory.getItems().remove(slot);
        double v = inventory.getCurrentWeight() - i.getWeight();
        inventory.setCurrentWeight(v);
    }

    public double getCredits() {
        return inventory.getCredits();
    }

    public void sellItem(double credits) {
        double i = inventory.getCredits();
        double v = inventory.getCurrentWeight() + i;
        inventory.setCurrentWeight(v);
    }
}
