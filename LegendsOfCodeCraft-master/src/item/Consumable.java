package item;

public class Consumable extends Item {
    private int stackSize;
    private final int maxStackSize;
    private final String effect;

    //Konstruktør
    public Consumable(String name, double weight, double value, int durability, int itemId, int stackSize, int maxStackSize, String effect) {
        super(name, weight, value, durability, itemId);
        this.stackSize = stackSize;
        this.effect = effect;
        this.maxStackSize = maxStackSize;
    }

    //addToStack har et parameter amount som har værdien af toPlace fra inventory.
    public int addToStack(int amount) {
        int canAdd = amount;
        if (stackSize + amount > maxStackSize) {
            canAdd = maxStackSize - stackSize;
        }
        stackSize += canAdd;
        return canAdd;
    }

    //Vi tjekker om item er stackable
    public boolean canStackWith(Consumable other) {
        if (other == null) return false;
        return this.getName().equalsIgnoreCase(other.getName())
                && this.maxStackSize == other.getMaxStackSize();
    }

    //Det her bliver kaldt når en Consumable bliver brugt, hvis der ikke er noget i den gældene stack får brugeren at vide der ikke er flere, ellers trækker den en fra stacken
    public String consumeOne() {
        if (stackSize <= 0) {
            return "Ingen tilbage af " + getName() + ".";
        }
        stackSize--;
        return effect;
    }

    @Override
    public String useItem() {
        return consumeOne();
    }

    public boolean isEmpty() {
        return stackSize <= 0;
    }

    @Override
    public String toString() {
        return getName() + " " + getCurrentStackSize() + "/" + getMaxStackSize() + " Vægt: " + getWeight() * getCurrentStackSize();
    }

    public int getCurrentStackSize() {
        return stackSize;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public String getEffect() {
        return effect;
    }
}
