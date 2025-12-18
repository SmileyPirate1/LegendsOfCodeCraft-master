package character;

import Exceptions.InvalidItemException;
import Exceptions.InventoryFullException;
import Exceptions.ItemNotFoundException;
import Exceptions.OverweightException;
import item.Consumable;
import item.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private int currentMaxSlots = 32;
    private int usedSlots = 0;
    private final int maxSlots = 192;
    private final double maxWeight = 50;
    private double currentWeight;
    private double credits;
    private int inventoryId;
    private int itemId;
    private final ArrayList<Item> items = new ArrayList<>();

    //Konstruktør
    public Inventory(int usedSlots, int currentMaxSlots, double currentWeight, double credits, int inventoryId, int itemId) {
        this.currentMaxSlots = currentMaxSlots;
        this.usedSlots = usedSlots;
        this.currentWeight = currentWeight;
        this.credits = credits;
        this.inventoryId = inventoryId;
        this.itemId = itemId;
    }

    //Vi kaster en checked exception og tvinger den til at boble tydligt igennem alle til character - main
    //Vi tjekker om det item vi vil adde IKKE er instanceof Consumable som er klassen. Hvis ikke tilføjer vi.
    public void addItem(Item item) throws OverweightException {

        //Vi starter med at tjekke om der er noget i inventaret
        if (item == null) throw new InvalidItemException("Ingen gestand fundet");
        //Hvis det viser sig at der er noget i inventaret sikre vi os at vi ikke har fyldt invetaret op først
        if (usedSlots >= currentMaxSlots) {
            throw new InventoryFullException("Ingen ledige slots i dit inventar " + usedSlots / currentMaxSlots);
        }
        //hvis genstanden ikke er en consumable bliver den lagt i et nyt slot
        if (!(item instanceof Consumable)) {
            //der tjekkes efter om vægten ikke overstiger max vægten
            if (currentWeight + item.getWeight() > maxWeight) {
                throw new OverweightException("Genstanden er for tung");
            }
            items.add(item);
            usedSlots++;
            currentWeight += item.getWeight();
            //Return for at have en tidlig exit hvis det ikke er en consumable
            return;
        }

        //incoming er et object af Consumable som indeholder det item vi der er på vej til at blive tjekket
        //Vi kalder getCurrenStackSize for at modtage mængden af den nuværende stack og toPLACE bliver denne værdi
        Consumable incoming = (Consumable) item;
        int toPlace = incoming.getCurrentStackSize();

        if (currentWeight + incoming.getWeight() > maxWeight) {
            throw new OverweightException("Genstanden er for tung");
        }

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
            if (usedSlots >= currentMaxSlots) {
                throw new InventoryFullException("Ikke nok inventar slots til at oprette nye stacke");
            }
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
            usedSlots++;
            toPlace -= chunk;
        }
        currentWeight += item.getWeight();
    }

    public void swap(List<Item> list, int i, int j) {
        Item tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);

    }

    public void bubbleSortByName() {

        boolean swapped;
        for (int i = 0; i < items.size(); i++) {
            swapped = false;
            for (int j = 0; j < items.size() - 1 - i; j++) {
                String a = items.get(j).getName();
                String b = items.get(j + 1).getName();

                int cmp = 0;
                if (a == null && b != null) cmp = 1;
                else if (a != null && b == null) cmp = -1;
                else if (a != null && b != null) cmp = a.compareToIgnoreCase(b);

                if (cmp > 0) {
                    swap(items, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

    }

    public void bubbleSortByWeight() {
        boolean swapped;
        for (int i = 0; i < items.size(); i++) {
            swapped = false;
            for (int j = 0; j < items.size() - 1 - i; j++) {
                if (items.get(j).getWeight() > items.get(j + 1).getWeight()) {
                    swap(items, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public void removeItem(int slot) {
        //Her fjerner vi en genstand
        if (slot < 0 || slot >= items.size()) {
            //Der bliver tjekket om den plads der skal fjernes fra har noget skrevet ind
            throw new ItemNotFoundException("Plads " + (slot + 1) + "findes ikke i inventaret");
        }
        //genstanden bliver valgt og fjernet fra inventaret
        Item i = getItems().get(slot);
        getItems().remove(slot);
        //efterfølgende bliver vægten af genstanden fjernet fra inventarets totale vægt
        double v = getCurrentWeight() - i.getWeight();
        setCurrentWeight(v);
        usedSlots--;

    }

    public String showInventory() {
        //Her bliver inventaret fremvist til brugeren
        StringBuilder output = new StringBuilder();
        output.append("Inventar: \n");
        for (int i = 0; i < getItems().size(); i++) {
            //vær plads i inventaret bliver tjekket og fremskrevet til brugeren, med plads nummer i inventaret
            output.append("Slot: ").append(+i + 1).append(" ").append(getItems().get(i)).append("\n");
        }
        //Efter inventaret er blevet udskrevet for brugeren vist hvor meget af vægten der er brugt
        //Hvor mange slots er blevet brugt og hvor mange kredits karakteren har
        output.append("Inventar vægt: ").append(" ").append(String.format("%.2f", getCurrentWeight())).append("\n");
        output.append("Slots brugte: ").append(" ").append(getUsedSlots()).append(" / ").append(getCurrentSlots()).append("\n");
        output.append("Kredit: ").append(" ").append(getCredits()).append("\n");
        return output.toString();
    }

    public void sellItem(int slot) {
        //Her får karakteren kredits for en genstand
        if (slot < 0 || slot >= items.size()) {
            //Vi starter med at tjekke om inputet macther med en plads med en genstand
            throw new ItemNotFoundException("Pladsen " + (slot + 1) + " findes ikke i inventaret");
        }
        //genstandens værdi bliver lagt til karakterens kredits
        Item i = getItems().get(slot);
        double newValue = i.getValue() + getCredits();
        setCredits(newValue);
    }

    public String useItemAt(int slot) {
        //Her bliver en genstand brugt af karakteren
        if (slot < 0 || slot >= items.size()) {
            //Vi starter med at tjekke at inputet stemmer over ens med en genstand i inventaret
            throw new IndexOutOfBoundsException("Pladsen " + (slot + 1) + " findes ikke i inventaret");
        }

        //Genstanden der bliver valgt skrives til item, hvorefter dens useItem() bliver kaldt
        Item item = getItems().get(slot);
        String result = item.useItem();

        if (item instanceof Consumable) {
            //Her tjekkes om det er en consumable, og hvis det er, om der er flere i stacken, hvis der er bliver den på pladsen
            Consumable c = (Consumable) item;
            if (c.isEmpty()) {
                //Hvis der ikke er flere i stacken bliver genstanden fjernet fra inventaret
                removeItem(slot);
            }
        }
        //Vi retunere værdien af item.useItem()
        return result;
    }

    //Vi replacer alle items med de items vi indlæser fra listen. Først clear derefter add
    public void replaceAllItems(List<Item> newItems) {
        items.clear();
        items.addAll(newItems);

        double totalWeight = 0.0;
        //Vi beregner vægt for consumable.
        for (Item it : items) {
            if (it instanceof Consumable c) {
                totalWeight += c.getWeight() * c.getCurrentStackSize();
            } else {
                totalWeight += it.getWeight();
            }
        }
        currentWeight = totalWeight;
        usedSlots = items.size();
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

    public int getMaxSlots() {
        return maxSlots;
    }

    public int getCurrentSlots() {
        return currentMaxSlots;
    }

    public void setCurrentWeight(double weight) {
        this.currentWeight = weight;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public void setSlots(int currentSlots) {
        this.currentMaxSlots = currentSlots;
    }

    public int getUsedSlots() {
        return usedSlots;
    }
}
