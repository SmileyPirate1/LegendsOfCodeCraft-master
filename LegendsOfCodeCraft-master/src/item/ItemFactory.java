package item;

import Enums.HandType;

import Enums.SlotType;

import java.io.*;
import java.util.*;

public class ItemFactory {
    private final Map<Integer, Weapon> weaponCatalog = new HashMap<>();
    private final Map<Integer, Armour> armourCatalog = new HashMap<>();
    private final Map<Integer, Consumable> consumableCatalog = new HashMap<>();

    public ItemFactory() {
        //Her bliver 3 lister af genstande oprættet
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
        consumableCatalog.put(4, new Consumable("Teleporterings rulle", 0.010, 30, 1, 15, 1, 10, "Du bliver teleporteret til den nærmeste by"));
    }

    public Item getItem(int choice) {
        //her benytter vi 3 random generator til at vælge hvilken genstand brugeren får ud fra hvilken liste brugeren har valgt at få en genstand fra
        Random rand = new Random();
        //Vi skriver +1 på grund af random starter fra 0 men catalogerne starter fra 1
        int randomWeapon = rand.nextInt(3) + 1;
        int randomArmour = rand.nextInt(8) + 1;
        int randomConsumable = rand.nextInt(4) + 1;
        Item newItem = null;
        //Her bliver det valgt fra catalogerne ud fra inputet fra brugeren og newItem får værdier efter brugerens valg og random generator
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

    //Gemmer en hel liste af items (en linje per item)
    //listen af genstande bliver gemt til en text fil
    public void writeItemsToFile(String path, List<Item> items) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(path))) {
            for (Item it : items) {
                w.write(serializeItem(it));
                w.newLine();
            }
        }
    }

    //Her bliver data'en fra text filen uploaded til programmet
    public List<Item> parseFileToItems(String path) throws IOException {
        List<Item> items = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                Item it = parseItemLine(line);
                if (it != null) items.add(it);
            }
        }
        return items;
    }

    //Serialize bruges til at genoprette objekterne med nogle bestemte værdier. Vi skal sikre os ved loading at objekterne har samme værdier som da vi sendte dem i tekstfilen
    //Det gør vi ved at tjekke op på hvilke type objekt det er og derefter få deres værdier.
    public String serializeItem(Item item) {
        //Her tjekkes om genstanden er en Consumable så vi ved om den skal stackes eller ej
        if (item instanceof Consumable) {
            Consumable c = (Consumable) item;
            int id = c.getItemId();

            if (consumableCatalog.containsKey(id)) {
                return String.join(",", "CONSUMABLE", String.valueOf(id), String.valueOf(c.getCurrentStackSize()));

            }
            return String.join
                    (",",
                            "CONSUMABLE_FULL",
                            escape(c.getName()),
                            String.valueOf(c.getWeight()),
                            String.valueOf(c.getValue()),
                            String.valueOf(c.getDurability()),
                            String.valueOf(c.getItemId()),
                            String.valueOf(c.getCurrentStackSize()),
                            String.valueOf(c.getMaxStackSize()),
                            escape(c.getEffect())
                    );
        } else if (item instanceof Weapon) {
            //her bliver Weapon skrevet ind
            Weapon w = (Weapon) item;
            int id = w.getItemId();

            if (weaponCatalog.containsKey(id)) {
                return String.join(",", "WEAPON", String.valueOf(id));
            }
            return String.join(",",
                    "WEAPON_FULL",
                    escape(w.getName()),
                    String.valueOf(w.getWeight()),
                    String.valueOf(w.getValue()),
                    String.valueOf(w.getDurability()),
                    String.valueOf(w.getItemId()),
                    String.valueOf(w.getDamage()),
                    w.getHandType().name()
            );
        } else if (item instanceof Armour) {
            //her bliver Armour skrevet ind
            Armour a = (Armour) item;
            int id = a.getItemId();
            if (armourCatalog.containsKey(id)) {

                return String.join(",", "ARMOUR", String.valueOf(id));
            }
            return String.join(",",
                    "ARMOUR_FULL",
                    escape(a.getName()),
                    String.valueOf(a.getWeight()),
                    String.valueOf(a.getValue()),
                    String.valueOf(a.getDurability()),
                    String.valueOf(a.getItemId()),
                    String.valueOf(a.getCurrentProtection()),
                    a.getSlotType().name()
            );
        } else {
            return String.join(",",
                    "ITEM_FULL",
                    item.getClass().getSimpleName(),
                    escape(item.getName()),
                    String.valueOf(item.getWeight()),
                    String.valueOf(item.getValue()),
                    String.valueOf(item.getDurability()),
                    String.valueOf(item.getItemId())
            );
        }
    }

    //Her sørger vi for at linjens mellem rum og andet unødvendigt fjernes. Vi tjekker samme tid om der er noget på linjen eller ej.
    public Item parseItemLine(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] parts = splitCSV(line);
        String kind = parts[0];

        switch (kind.toUpperCase()) {
            case "WEAPON": {
                int id = Integer.parseInt(parts[1]);
                Weapon w = weaponCatalog.get(id);
                if (w == null) throw new IllegalArgumentException("Ukendt weapon id: " + id);
                return copyWeapon(w);
            }
            case "ARMOUR": {
                int id = Integer.parseInt(parts[1]);
                Armour a = armourCatalog.get(id);
                if (a == null) throw new IllegalArgumentException("Ukendt armour id: " + id);
                return copyArmour(a);
            }
            case "CONSUMABLE": {
                int id = Integer.parseInt(parts[1]);
                int stack = Integer.parseInt(parts[2]);
                Consumable c = consumableCatalog.get(id);
                if (c == null) throw new IllegalArgumentException("Ukendt consumable id: " + id);
                return copyConsumable(c, stack);
            }

            case "WEAPON_FULL": {
                String name = unescape(parts[1]);
                double weight = Double.parseDouble(parts[2]);
                double value = Double.parseDouble(parts[3]);
                int durability = Integer.parseInt(parts[4]);
                int itemId = Integer.parseInt(parts[5]);
                int damage = Integer.parseInt(parts[6]);
                HandType hand = HandType.valueOf(parts[7]);
                return new Weapon(name, weight, value, durability, itemId, damage, hand);
            }

            case "ARMOUR_FULL": {
                String name = unescape(parts[1]);
                double weight = Double.parseDouble(parts[2]);
                double value = Double.parseDouble(parts[3]);
                int durability = Integer.parseInt(parts[4]);
                int itemId = Integer.parseInt(parts[5]);
                int protection = Integer.parseInt(parts[6]);
                SlotType slot = SlotType.valueOf(parts[7]);
                return new Armour(name, weight, value, durability, itemId, protection, slot);
            }

            case "CONSUMABLE_FULL": {
                String name = unescape(parts[1]);
                double weight = Double.parseDouble(parts[2]);
                double value = Double.parseDouble(parts[3]);
                int durability = Integer.parseInt(parts[4]);
                int itemId = Integer.parseInt(parts[5]);
                int currentStack = Integer.parseInt(parts[6]);
                int maxStack = Integer.parseInt(parts[7]);
                String effect = unescape(parts[8]);
                return new Consumable(name, weight, value, durability, itemId, currentStack, maxStack, effect);
            }
            default:
                throw new IllegalArgumentException("Ukendt linje type: " + kind);
        }
    }

    //Kopi af weapon i for at få værdierne
    private Weapon copyWeapon(Weapon w) {
        if (w == null) return null;
        return new Weapon(w.getName(), w.getWeight(), w.getValue(), w.getDurability(), w.getItemId(), w.getDamage(), w.getHandType());
    }

    //Kopi armour for at få værdierne
    private Armour copyArmour(Armour a) {
        if (a == null) return null;
        return new Armour(a.getName(), a.getWeight(), a.getValue(), a.getDurability(), a.getItemId(), a.getCurrentProtection(), a.getSlotType());
    }

    //Kopi af consumable for at få værdierne.
    private Consumable copyConsumable(Consumable c, Integer overrideStack) {
        if (c == null) return null;
        int stack = (overrideStack != null) ? overrideStack : c.getCurrentStackSize();
        return new Consumable(c.getName(), c.getWeight(), c.getValue(), c.getDurability(), c.getItemId(), stack, c.getMaxStackSize(), c.getEffect());
    }

    //Her splitter vi linjerne op.
    private static String[] splitCSV(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean escape = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (escape) {
                sb.append(c);
                escape = false;
            } else if (c == '\\') {
                escape = true;
            } else if (c == ',') {
                tokens.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString());
        return tokens.toArray(new String[0]);
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace(",", "\\,");
    }

    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\,", ",").replace("\\\\", "\\");
    }
}



