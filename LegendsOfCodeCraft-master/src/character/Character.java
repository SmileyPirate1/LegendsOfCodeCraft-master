package character;

import java.util.ArrayList;

import item.Consumable;
import item.Item;

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

    public void addItem(Item item){
        inventory.addItem(item);
    }

    public java.util.List<Item> getInventory(){
        return inventory.getItems();
    }
}
