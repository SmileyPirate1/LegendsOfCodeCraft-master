package character;

import item.Consumable;
import item.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int slots;
    private final int maxSlots = 192;
    private double maxWeight;
    private double currentWeight;
    private double credits;
    private int inventoryId;
    private int itemId;
    private final ArrayList<Item> items = new ArrayList<>();

    //Konstruktør
    public Inventory(int slots, double maxWeight, double currentWeight, double credits, int inventoryId, int itemId) {
        this.slots = slots;
        this.maxWeight = maxWeight;
        this.currentWeight = currentWeight;
        this.credits = credits;
        this.inventoryId = inventoryId;
        this.itemId = itemId;
    }

    //Vi tjekker om det item vi vil adde IKKE er instanceof Consumable som er klassen. Hvis ikke tilføjer vi.
    public boolean addItem(Item item) {
        if (!(item instanceof Consumable)) {
            if (currentWeight + item.getWeight() > maxWeight) {
                return false;
            }
            items.add(item);
            currentWeight += item.getWeight();
            return true;
        }

        //incoming er et object af Consumable som indeholder det item vi der er på vej til at blive tjekket
        //Vi kalder getCurrenStackSize for at modtage mængden af den nuværende stack og toPLACE bliver denne værdi
        Consumable incoming = (Consumable) item;
        int toPlace = incoming.getCurrentStackSize();

        //Vi tjekker igennem alle items for at se hvor vi kan ligge oveni en stack.
        for (Item i : items) {
            if (toPlace == 0) break;

            //Vi opretter et object af Consumable med værdien af det sted vi er nået i listen
            //Objektet vi tjekker mod ekstiere allerede i listen
            //Hvis existing "canStackWith" det item som er incoming
            if (i instanceof Consumable) {
                Consumable existing = (Consumable) i;
                if (existing.canStackWith(incoming)) {
                    int moved = existing.addToStack(toPlace);
                    toPlace -= moved;
                }
            }
        }

        while (toPlace > 0) {
            int chunk = incoming.getMaxStackSize();
            if (chunk > toPlace) {
                chunk = toPlace;
            }

            Consumable newStack = new Consumable(
                    incoming.getName(),
                    incoming.getWeight(),
                    incoming.getValue(),
                    incoming.getDurability(),
                    incoming.getItemId(),
                    chunk,
                    incoming.getMaxStackSize(),
                    incoming.getEffect()
            );

            items.add(newStack);
            toPlace -= chunk;
        }
        currentWeight += item.getWeight();
        return true;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }
    public double getMaxWeight() {
        return maxWeight;
    }
    public double getSlots(){
        return slots;
    }
    public void setCurrentWeight(double weight){
        this.currentWeight = weight;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }
}
