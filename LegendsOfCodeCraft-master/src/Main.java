import item.*;
import character.Character;
import Enums.SlotType;
import Enums.HandType;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Item mythicHammer = new Weapon("hammer", 5.3, 3, 10, 1, 15, HandType.twoHand);
        Item goldenChestplate = new Armour("chestplate", 10, 5, 50, 2, 10, SlotType.chest);
        Item healthPotion = new Consumable("healthPotion", 0.5,1,1,3,80, 39,"you feel healthy");

        Character torben = new Character("Torben", 3, 1, 1);


        System.out.println("Velkommen til Legends of CodeCraft, vælg din næste aktion");
        boolean run = true;
        while (run) {
            //loop start
            System.out.println("1: Tilføj genstand");
            System.out.println("2: Fjern genstand");
            System.out.println("5: Vis inventar");
            System.out.println("8: Afslut programmet");
            String action = input.nextLine();

            switch (action) {
                case "1":
                    System.out.println("vælg en item type du gerne vil have");
                    System.out.println("1: våben, 2: rustning, 3: consumable");
                    String itemType = input.nextLine();
                    if (itemType.equals("1")) {
                        torben.addItem(mythicHammer);
                    } else if (itemType.equals("2")) {
                        torben.addItem(goldenChestplate);
                    } else if (itemType.equals("3")) {
                        torben.addItem(healthPotion);
                    } else {
                        System.out.println("du valgte ikke en af mulighederne");
                    }
                    break;
                case "2":
                    System.out.println("Her er dit inventar");
                    for (int i = 0; i < torben.getInventory().size(); i++) {
                        System.out.println(torben.getInventory().get(i));
                    }
                    System.out.println("vælg en genstand at fjerne ud fra dens plads");
                    int removedItem = input.nextInt();
                    input.nextLine();
                    torben.getInventory().remove(removedItem - 1);
                    break;

                case "5":
                    System.out.println("Her er dit inventar");
                    for (int i = 0; i < torben.getInventory().size(); i++) {
                        System.out.println(torben.getInventory().get(i));
                    }
                    break;

                case "8":
                    run = false;
                    break;

            }
        }

        for (int i = 0; i < torben.getInventory().size(); i++){
            System.out.println(torben.getInventory().get(i));
        }
    }
}