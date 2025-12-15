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

        //her sætter vi et while løkke op til at køre programmet så længe man vil tilføje, fjerne eller se inventar
        //run bruges til at tjekke om man stadig vil køre programmet
        boolean run = true;
        try {
            while (run) {
                //Her printer vi Main Menu som fortæller om mulighederne brugeren har
                printMainMenu();
                //her vælger brugeren hvad han vil fra Main Menu, han vælger ved at skrive tallet på muligheden
                String action;
                //med denne try/catch ser vi om brugeren vælger et gyldigt input, hvis ikke for brugeren en fejl og mulighed for at prøve igen
                try {
                    action = input.nextLine();
                } catch (NoSuchElementException | IllegalStateException e) {
                    System.out.println("Fejl ved input");
                    break;
                }
                switch (action) {
                    //Her har vi første mulighed fra Menuen, hvor brugeren skal vælge hvilket slags genstand brugeren vil have
                    case "1":
                        System.out.println("vælg en item type du gerne vil have");
                        System.out.println("1: våben, 2: rustning, 3: consumable");
                        String itemType;
                        //Her tjekker vi om inputet fra brugeren er et gyldigt input
                        try {
                            itemType = input.nextLine();
                        } catch (Exception e) {
                            System.out.println("Fejl ved input");
                            break;
                        }

                        //Vi bruger en try/catch til at tjekke at genstanden kan tilføjes, enten kan den tilføjes, genstanden vejer for meget eller der skete en ukendt fejl
                        try {
                            //Her benytter vi os af itemFactory til at hente den genstand som brugeren gerne vil have
                            Item newItem;
                            //if/else bruges til at identificere hvilket input bliver brugt og hvad skal tilføjes til inventaret
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
                            //I tilfælde intet blev tilføjet til inventaret fortæller vi det til brugeren
                            if (newItem == null) {
                                System.out.println("Ingen genstand er indlæst");
                            }

                            //Her bliver genstanden tilføjet til karakterens inventar
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
                        //Vi tjekker først om der er noget i inventaret, hvis inventaret er tomt melder vi det til brugeren
                        if (torben.getInventory().isEmpty()) {
                            System.out.println("Du har ikke noget i din inventar");
                        } else {
                            //Her fremviser vi til brugeren hele inventaret
                            System.out.println(torben.showInventory());
                            System.out.println("Vælg en genstand at fjerne ud fra dens plads");
                            //Her bruger we try/catch til at tjekke inputet fra brugeren og tjekker at det stemmer over ens med en plads i inventaret
                            try {
                                int removedItem = input.nextInt();
                                input.nextLine();
                                //her bliver genstanden fjernet fra inventaret ud fra pladsen brugeren har valgt
                                //-1 bliver skrevet for at vælge den plads i arraet som brugeren gern vil vælger på grund af et array starter fra 0 men brugeren vælger fra 1
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
                        //Vi tjekker om brugerens inventar er tomt og fortæller brugeren om der er noget eller ej
                        if (torben.getInventory().isEmpty()) {
                            System.out.println("Du har ikke noget i din inventar");
                        } else {
                            //Her fremviser vi inventaret og beder brugeren om at vælge hvilken genstand brugeren gerne vil bruge
                            System.out.println("Vælg den genstand du vil bruge");
                            System.out.println(torben.showInventory());
                            int choice;
                            //Her tjekker vi ved brug af try/catch at brugerens valg stemmer over ens med en genstand i inventaret
                            try {
                                choice = input.nextInt();
                            } catch (Exception e) {
                                System.out.println("Kunne ikke indlæse dit valg");
                                break;
                            }
                            //Her tjekker vi at genstanden kan bruges af programmet
                            try {
                                //Genstanden som brugeren gerne vil bruge bliver valgt
                                //-1 bruges til at indikere hvilken genstand skal bruges ud fra arraet, et array starter fra 0 men brugerens valg starter fra 1
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
                        //Her bliver inventaret sorteret efter navn eller vægt
                        System.out.println("Sorter efter navn eller vægt");
                        String sortItem;
                        //Vi bruger en try/catch til at sikre at brugerens input kan bruges
                        try {
                            sortItem = input.nextLine();
                        } catch (Exception e) {
                            System.out.println("Kunne ikke indlæse dit valg");
                            break;
                        }
                        //Her bruges en try/catch til at sikre at inputet er gyldigt
                        try {
                            //if/else bliver brugt til at tjekke inputet og sorter inventar efter valg
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
                        //Vi viser inventaret fra karakteren som det står i listen på nuværende tidspunkt
                        //try/catchen er her for at sikre at inventaret kan vises
                        try {
                            System.out.println(torben.showInventory());
                        } catch (Exception e) {
                            System.out.println("Der er sket en kritisk fejl");
                        }
                        break;

                    //Sælg genstand use case "6"
                    case "6":
                        //Her tjekker vi efter om der er noget i inventaret
                        if (torben.getInventory().isEmpty()) {
                            System.out.println("Du har ikke noget i din inventar");
                        } else {
                            //her bliver inventaret fremvist til brugeren så man kan vælge derfra hvilken genstand skal ruges
                            System.out.println(torben.showInventory());
                            System.out.println("vælg den genstand du gerne vil sælge");
                            //try/catch bruges til at tjekke et gyldigt input
                            try {
                                int soldItem = input.nextInt();
                                input.nextLine();

                                //Her vælger vi genstanden der bliver solgt ud fra en list som starter på 0 mens brugerens valg starter fra 1, derfor bruger vi -1
                                torben.sellItem(soldItem - 1);
                                //Her bliver karakterens nye credit fremvist til brugeren
                                System.out.println("Nye balance: " + torben.getCredits());
                                //Her bliver den solgte genstand fjernet fra inventaret, valgt med -1 eftersom arraet starter på 0 mens brugerens valg starter fra 1
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
                            //Her tjekkes der om brugeren har mulighed for at upgrader mere plads i inventaret
                            if (torben.getSlots() == torben.getMaxSlots()) {
                                System.out.println("Du har maks inventar slots");
                            } else {
                                //Her får brugeren valget om de vil tilføje mere plads til inventaret
                                System.out.println("Her kan du købe mere plads til dit inventar");
                                System.out.println("Køb en ekstra plads til dit inventar? (ja/nej)");
                                String upgrade = input.nextLine();
                                if (upgrade.equalsIgnoreCase("ja")) {
                                    //her bliver der tjekket om karakteren har nok credits til at upgrader sit inventar
                                    if (torben.getCredits() >= 15) {
                                        //her bliver inventaret upgraderet og brugeren får at vide hvor mange pladser brugeren har nu
                                        torben.upgradeSlot();
                                        System.out.println("Du har nu " + torben.getSlots() + " pladser i dit inventar");
                                    } else {
                                        //hvis karakteren ikke har nok credits til at upgradere for brugeren det at vide
                                        System.out.println("Du har ikke nok credits til at upgrader dit inventar ");
                                        break;
                                    }
                                } else if (upgrade.equalsIgnoreCase("nej")) {
                                    //hvis brugeren siger nej til upgradering hopper vi til starten af loopet
                                    break;
                                } else {
                                    //hvis brugeren ikke indtastede et af valgende får brugeren det at vide
                                    System.out.println("du valgte ikke en af mulighederne");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Der er sket en kritisk fejl");
                        }
                        break;

                    case "8":
                        System.out.println("Angiv filsti til gemning (fx. items.txt)");
                        String savePath = input.nextLine();
                        try {
                            torben.saveItems(savePath, itemFactory);
                            System.out.println("Din data er gemt");
                        } catch (java.io.IOException e) {
                            System.out.println("Kunne ikke gemme filen");
                        } break;

                    case "9":
                        System.out.println("Angiv filsti til indlæsning (fx. items.txt)");
                        String loadPath = input.nextLine();
                        try { torben.loadItems(loadPath, itemFactory);
                            System.out.println("Data indlæst");
                        } catch (java.io.IOException e) {
                            System.out.println("Kunne ikke indlæse filen");
                        } catch (IllegalArgumentException e){
                            System.out.println("Filens indhold er ugyldigt");
                        } break;

                    case "10":
                        //her slutter while løkken og programmet bliver lukket
                        run = false;
                        break;

                    default:
                        //hvis brugeren vælger noget der ikke var en mulighed fra Main Menu
                        System.out.println("Ugyldigt valg prøv igen");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Der er sket en kritisk fejl");
        } finally {
            //finally sker altid, eksempelvis lukker fil
            try {
                input.close();
            } catch (Exception ignore) {
            }
        }
    }

    public static void printMainMenu() {
        //her er Main Menu som viser alle brugerens valg
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