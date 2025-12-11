package character;

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
        this.inventory = new Inventory(0, 32, 0, 1, inventoryId,1);
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
        return inventory.showInventory();
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

    //Metode til at fjerne items, kalder inventory for at have alle 3 lag med. main - character - inventory og tilbage samme vej.
    public void removeItem(int slot) {
        inventory.removeItem(slot);
    }

    public void sellItem(int slot) {
        inventory.sellItem(slot);
    }

    public void sortByWeight(){
        inventory.bubbleSortByWeight();
    }

    public void sortByName(){
        inventory.bubbleSortByName();
    }

    public void upgradeSlot() {
        int upgrade = 1 + inventory.getCurrentSlots();
        inventory.setSlots(upgrade);
    }

    public int getSlots() {
        return inventory.getCurrentSlots();
    }

    public double getCredits() {
        return inventory.getCredits();
    }

    public double getMaxSlots(){
        return inventory.getMaxSlots();
    }
    public String useItemAt(int index){
        if (index >= inventory.getItems().size()){
            throw new IndexOutOfBoundsException("Ugyldigt plads" + index);
        }
        Item item = inventory.getItems().get(index);

        String result = item.useItem();

        if (item instanceof Consumable){
            Consumable c = (Consumable) item;
            if (c.isEmpty()){
                inventory.removeItem(index);
            }
        }
        return result;
    }
}
