import Exceptions.InventoryFullException;
import Exceptions.OverweightException;
import item.*;
import character.Character;

import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Character torben = new Character("Torben", 3, 1, 1);
        ItemFactory itemFactory = new ItemFactory();

        System.out.println("Velkommen til Legends of CodeCraft, vælg din næste aktion");

        boolean run = true;
        try {
            while (run) {
                printMainMenu();
                String action;
                try {
                    action = input.nextLine();
                } catch (NoSuchElementException | IllegalStateException e) {
                    System.out.println("Fejl ved input");
                    break;
                }
                switch (action) {
                    case "1":
                        System.out.println("vælg en item type du gerne vil have");
                        System.out.println("1: våben, 2: rustning, 3: consumable");
                        String itemType;
                        try {
                            itemType = input.nextLine();
                        } catch (Exception e) {
                            System.out.println("Fejl ved input");
                            break;
                        }

                        try {
                            Item newItem;
                            if (itemType.equals("1")) {
                                newItem = itemFactory.getItem(1);
                            } else if (itemType.equals("2")) {
                                newItem = itemFactory.getItem(2);
                            } else if (itemType.equals("3")) {
                                newItem = itemFactory.getItem(3);
                            } else {
                                System.out.println("du valgte ikke en af mulighederne");
                                break;
                            }
                            if (newItem == null) {
                                System.out.println("Ingen genstand er indlæst");
                            }

                            torben.addItem(newItem);
                                System.out.println("Tilføjet: " + newItem);

                                //Vi griber overweight exception fra inventory som er boblet igennem programmet.
                        } catch (InventoryFullException | OverweightException e) {
                            System.out.println(e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Der er sket en kritisk fejl");
                            run = false;
                        }
                        break;

                    //Fjern genstand use cas "2".
                    case "2":
                        if (torben.getInventory().isEmpty()) {
                            System.out.println("Du har ikke noget i din inventar");
                        } else {
                            System.out.println(torben.showInventory());
                            System.out.println("Vælg en genstand at fjerne ud fra dens plads");
                            try {
                                int removedItem = input.nextInt();
                                input.nextLine();
                                torben.removeItem(removedItem - 1);
                                System.out.println(torben.getCurrentWeight());
                            } catch (InputMismatchException e) {
                                System.out.println("Du skal indtaste et gyldigt tal");
                                input.nextLine();
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Pladsen eksistere ikke i inventaret");
                            } catch (Exception e) {
                                System.out.println("Der er sket en kritisk fejl");
                            }
                        }
                        break;

                    //Brug genstand use case "3"
                    case "3":
                        if (torben.getInventory().isEmpty()) {
                            System.out.println("Du har ikke noget i din inventar");
                        } else {
                            System.out.println("Vælg den genstand du vil bruge");
                            System.out.println(torben.showInventory());
                            int choice;
                            try {
                                choice = input.nextInt();
                            } catch (Exception e) {
                                System.out.println("Kunne ikke indlæse dit valg");
                                break;
                            }
                            try {
                                String result = torben.useItemAt(choice - 1);
                                System.out.println(result);
                                input.nextLine();
                            } catch (Exception e) {
                                System.out.println("Der er sket en kritisk fejl");
                            }
                        }
                        break;

                    //Sorter inventar use case "4".
                    case "4":
                        System.out.println("Sorter efter navn eller vægt");
                        String sortItem;
                        try {
                            sortItem = input.nextLine();
                        } catch (Exception e) {
                            System.out.println("Kunne ikke indlæse dit valg");
                            break;
                        }
                        try {
                            if (sortItem.equalsIgnoreCase("navn")) {
                                torben.sortByName();
                            } else if (sortItem.equalsIgnoreCase("vægt")) {
                                torben.sortByWeight();
                            } else {
                                System.out.println("du valgte ikke en af mulighederne");
                            }
                        } catch (Exception e) {
                            System.out.println("Der er sket en kritisk fejlD");
                            break;
                        }
                        break;

                        //Vis inventar use case "5"
                    case "5":
                        try {
                            System.out.println(torben.showInventory());
                        } catch (Exception e) {
                            System.out.println("Der er sket en kritisk fejl");
                        }
                        break;

                    //Sælg genstand use case "6"
                    case "6":
                        if (torben.getInventory().isEmpty()) {
                            System.out.println("Du har ikke noget i din inventar");
                        } else {
                            System.out.println(torben.showInventory());
                            System.out.println("vælg den genstand du gerne vil sælge");
                            try {
                                int soldItem = input.nextInt();
                                input.nextLine();

                                torben.sellItem(soldItem - 1);
                                System.out.println("Nye balance: " + torben.getCredits());
                                torben.removeItem(soldItem - 1);
                            } catch (InputMismatchException e) {
                                System.out.println("Du indtastede ikke et gyldigt tal");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Pladsen eksistere ikke");
                            } catch (Exception e) {
                                System.out.println("Der er sket en kritisk fejl");
                            }
                        }
                        break;

                    //Opgrader inventar use case "7"
                    case "7":
                        try {
                            if (torben.getSlots() == torben.getMaxSlots()) {
                                System.out.println("Du har maks inventar slots");
                            } else {
                                System.out.println("Her kan du købe mere plads til dit inventar");
                                System.out.println("Køb en ekstra plads til dit inventar? (ja/nej)");
                                String upgrade = input.nextLine();
                                if (upgrade.equalsIgnoreCase("ja")) {
                                    if (torben.getCredits() >= 15) {
                                        torben.upgradeSlot();
                                        System.out.println("Du har nu " + torben.getSlots() + " pladser i dit inventar");
                                    } else {
                                        System.out.println("Du har ikke nok credits til at upgrader dit inventar ");
                                        break;
                                    }
                                } else if (upgrade.equalsIgnoreCase("nej")) {
                                    break;
                                } else {
                                    System.out.println("du valgte ikke en af mulighederne");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Der er sket en kritisk fejl");
                        }
                        break;

                    case "8":
                        System.out.println("Din data bliver gemt");

                    case "9":
                        System.out.println("Din sidste gemte data bliver loaded in");

                    case "10":
                        run = false;
                        break;

                    default:
                        System.out.println("Ugyldigt valg prøv igen");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Der er sket en kritisk fejl");
        } finally {
            try {
                input.close();
            } catch (Exception ignore) {
            }
        }
    }

    public static void printMainMenu() {
        System.out.println("1: Tilføj genstand");
        System.out.println("2: Fjern genstand");
        System.out.println("3: Brug genstande");
        System.out.println("4: Sorter inventar");
        System.out.println("5: Vis inventar");
        System.out.println("6: Sælg genstande");
        System.out.println("7: Upgrader inventar");
        System.out.println("8: Gem data");
        System.out.println("9: Indlæs data");
        System.out.println("10: Afslut programmet");
    }
}