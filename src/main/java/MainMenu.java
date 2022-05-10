import Exceptions.EntityNotFoundException;
import Exceptions.IncorrectNameException;
import Exceptions.PowerException;

import java.util.Scanner;

public class MainMenu {
    private HomeControlUnit homeControlUnit;
    private static String[] operationNames = {
            "1. Add Room",
            "2. Add an unit",
            "3. Delete unit",
            "4. Power ON unit",
            "5. Power OFF unit",
            "6. Show all units",
            "7. Show sorted powered units (power decrement)",
            "8. Show total actual power",
            "9. Find unit",
            "0. Exit"};

    public MainMenu(HomeControlUnit homeControlUnit) {
        this.homeControlUnit = homeControlUnit;
    }

    private static void printMenuOperations() {
        for (String operationName: operationNames) {
            System.out.println(operationName);
        }
    }

    public void showMenu() {
        boolean runWhile = true;
        while (runWhile) {
            printMenuOperations();
            int k = inputInt("Operation number");
            switch (k) {
                case 1: // Add New Room
                    try {
                        homeControlUnit.addNewRoom(inputString("Room name"));
                    } catch(IncorrectNameException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2: // Add New Unit
                    try {
                        homeControlUnit.addNewUnit(inputString("Unit name"), inputInt("Unit power"), inputString("Existing room name"));
                    } catch(IncorrectNameException | PowerException | EntityNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3: // Delete Unit
                    try {
                        homeControlUnit.deleteUnitFromRoom(inputInt("Existing Unit ID"));
                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4: // Power ON Unit
                    try {
                        homeControlUnit.powerOnUnit(inputInt("Existing Unit ID"));
                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5: // Power OFF Unit
                    try {
                        homeControlUnit.powerOffUnit(inputInt("Existing Unit ID"));
                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6: // Show ALL UNITS
                    homeControlUnit.printAllUnits();
                    break;
                case 7: // Show Sorted UNITS
                    homeControlUnit.printAllSortedUnits();
                    break;
                case 8: // Show total actual power
                    homeControlUnit.showActualPower();
                    break;
                case 9: // Find Unit
                    homeControlUnit.findUnit(inputString("Please input unit name or part of name"));
                    break;
                case 0: // Exit from Application
                    runWhile = false;
            }
        }
    }
    private static int inputInt(String str) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input " + str);
        return scan.nextInt();
    }

    private static String inputString(String str) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input " + str);
        return scan.nextLine();
    }
}
