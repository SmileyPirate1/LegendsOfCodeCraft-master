import item.*;
import character.Character;
import Enums.SlotType;
import Enums.HandType;

import java.util.Collections;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //Item mythicHammer = new Weapon("hammer", 5.3, 3, 10, 1, 15, HandType.twoHand);
        //Item goldenChestplate = new Armour("chestplate", 10, 5, 50, 2, 10, SlotType.chest);
        //Item healthPotion = new Consumable("healthPotion", 1,1,1,3,1, 4,"you feel healthy");

        Character torben = new Character("Torben", 3, 1, 1);

        System.out.println("Velkommen til Legends of CodeCraft, vælg din næste aktion");
        boolean run = true;
        while (run) {
            //loop start
            System.out.println("1: Tilføj genstand");
            System.out.println("2: Fjern genstand");
            System.out.println("3: brug genstande");
            System.out.println("4: Sorter inventar");
            System.out.println("5: Vis inventar");
            System.out.println("6: Sælg genstande");
            System.out.println("7: Upgrader inventar");
            System.out.println("8: Afslut programmet");
            String action = input.nextLine();

            switch (action) {
                case "1":
                    System.out.println("vælg en item type du gerne vil have");
                    System.out.println("1: våben, 2: rustning, 3: consumable");
                    String itemType = input.nextLine();

                    Item newItem = null;
                    if (itemType.equals("1")) {
                        newItem = new Weapon("hammer", 5.3, 3, 10, 1, 15, HandType.twoHand);
                    } else if (itemType.equals("2")) {
                        newItem = new Armour("chestplate", 20, 5, 50, 2, 10, SlotType.chest);
                    } else if (itemType.equals("3")) {
                        newItem = new Consumable("healthPotion", 1, 1, 1, 3, 1, 4, "you feel healthy");
                    } else {
                        System.out.println("du valgte ikke en af mulighederne");
                        break;
                    }
                    boolean add = torben.addItem(newItem);
                    if (add) {
                        System.out.println("Tilføjet: " + newItem);
                        System.out.println("Nuværende vægt" + torben.getCurrentWeight());
                    } else {
                        System.out.println("Genstand er for tungt!");
                    }
                    break;
                case "2":
                    if (torben.getInventory().isEmpty()) {
                        System.out.println("Du har ikke noget i din inventar");
                    } else {
                        System.out.println(torben.showInventory());
                        System.out.println("Vælg en genstand at fjerne ud fra dens plads");
                        int removedItem = input.nextInt();
                        input.nextLine();
                        torben.removeItem(removedItem - 1);
                        System.out.println(torben.getCurrentWeight());
                    }
                    break;

                case "3":
                    if (torben.getInventory().isEmpty()) {
                        System.out.println("Du har ikke noget i din inventar");
                    } else {
                        System.out.println("Vælg den genstand du vil bruge");
                        System.out.println(torben.showInventory());
                        String useItem = input.nextLine();
                        switch (useItem.toLowerCase()) {

                            case "våben":
                            case "weapon":
                                if (torben.useWeapon()) {
                                    System.out.println("Du slår med dit våben");
                                } else {
                                    System.out.println("Du har ikke et våben");
                                }
                                break;

                            case "rustning":
                            case "armour":
                                if (torben.useArmour()) {
                                    System.out.println("Du tager dit armour på");
                                } else {
                                    System.out.println("Du har ikke noget armour");
                                }
                                break;

                            case "health potion":
                                if (torben.useConsumable()) {
                                    System.out.println("Dine sår forsviner");
                                } else {
                                    System.out.println("Du har ikke nogel healing potions");
                                }
                                break;
                        }
                    }
                    break;

                case "4":
                    System.out.println("Sorter efter navn eller vægt");
                    String sortItem = input.nextLine();
                    if (sortItem.equalsIgnoreCase("navn")) {
                        //sorter efter navn
                    } else if (sortItem.equalsIgnoreCase("vægt")) {
                        //sorter efter vægt
                    } else {
                        System.out.println("du valgte ikke en af mulighederne");
                    }

                case "5":
                    System.out.println(torben.showInventory());
                    break;

                case "6":
                    if (torben.getInventory().isEmpty()) {
                        System.out.println("Du har ikke noget i din inventar");
                    } else {
                        System.out.println(torben.showInventory());
                        System.out.println("vælg den genstand du gerne vil sælge");
                        int removedItem = input.nextInt();
                        input.nextLine();
                        //double earning = newItem.getValue();
                        //System.out.println(torben.getCredits() + earning);
                        torben.removeItem(removedItem - 1);
                        System.out.println(torben.getCurrentWeight());
                    }

                case "8":
                    run = false;
                    break;

            }
        }
    }
}