package item;

import Enums.ItemType;

public class Consumable extends Item{
    private static final ItemType itemType = ItemType.consumable;
    private int stackSize;
    private int maxStackSize;
    private String effect;

    //Konstruktør
    public Consumable(String name, double weight, double value, int durability, int itemId, int stackSize, int maxStackSize, String effect) {
        super(name, weight, value, durability, itemId, itemType);
        this.stackSize = stackSize;
        this.effect = effect;
        this.maxStackSize = maxStackSize;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return getName() + " " + getCurrentStackSize() + "/" + getMaxStackSize();
    }

    public int getCurrentStackSize() {
        return stackSize;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int spaceLeft(){
        return maxStackSize - stackSize;
    }
    //addToStack har et parameter amount som har værdien af toPlace fra inventory.
    public int addToStack(int amount){
        int canAdd = amount;
        if (stackSize + amount > maxStackSize){
            canAdd = maxStackSize - stackSize;
        }
        stackSize += canAdd;
        return canAdd;
    }
    //Vi tjekker om item er stackable
    public boolean canStackWith(Consumable other){
        if (other == null) return false;
        return this.getName().equalsIgnoreCase(other.getName())
                && this.maxStackSize == other.getMaxStackSize();
    }
    public String consumeOne(){
        if (stackSize <= 0){
            return "Ingen tilbage af " + getName() + ".";
        }
        stackSize--;
        return effect;
    }

    @Override
    public String useItem(){
        return consumeOne();
    }

    public boolean isEmpty(){
        return stackSize <= 0;
    }
}
