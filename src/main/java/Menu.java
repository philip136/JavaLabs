import Exceptions.NotZeroOrNegativeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.w3c.dom.ranges.Range;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@ToString
public class Menu {
    private static final String[] operationNames = {
        "1. Create a vegetable",
        "2. Choose a vegetable for a salad",
        "3. Make a salad",
        "4. Sort all vegetables in salad by calories and get total calories",
        "5. Show all salads",
        "6. Find vegetables in salad by calories range",
        "0. Exit"
    };
    private List<Vegetable> vegetables;
    private List<Vegetable> vegetablesForSalad;
    private List<Salad> salads;

    public Menu() {
        vegetables = new ArrayList<>();
        vegetablesForSalad = new ArrayList<>();
        salads = new ArrayList<>();
    }

    private static void printMenuOperations() {
        for (String operationName: operationNames) System.out.println(operationName);
    }
    public void showMenu() throws IllegalArgumentException, NotZeroOrNegativeException {
        boolean isRunning = true;
        while (isRunning) {
            printMenuOperations();
            int indexOperation = getInputAndNotify("Operation number").nextInt();
            switch (indexOperation) {
                case 1:
                    String vegetableName = getInputAndNotify("Vegetable name").nextLine().toUpperCase();
                    double vegetableWeight = Double.parseDouble(getInputAndNotify("Vegetable weight").nextLine().replace(",","."));
                    double vegetableCalories = Double.parseDouble(getInputAndNotify("Vegetable calories").nextLine().replace(",","."));
                    Vegetable vegetable = new Vegetable(Vegetables.valueOf(vegetableName), vegetableWeight, vegetableCalories);
                    vegetables.add(vegetable);
                    break;
                case 2:
                    System.out.println("Please select a vegetable index");
                    for (int index=0; index < vegetables.size(); index++) {
                        System.out.println(index + 1 + ": " + vegetables.get(index).getType().name());
                    }
                    int vegetableIndex = getInputAndNotify("").nextInt();
                    Vegetable veg = vegetables.get(vegetableIndex - 1);
                    vegetablesForSalad.add(veg);
                    break;
                case 3:
                    String newSaladName = getInputAndNotify("Please enter a name for the salad").nextLine();
                    boolean saladIsExist = salads.stream().anyMatch(s -> s.getName().equalsIgnoreCase(newSaladName));
                    if (!saladIsExist | vegetablesForSalad.size() != 0) {
                        Salad newSalad = Salad.builder()
                                .name(newSaladName)
                                .ingredients(vegetablesForSalad)
                                .build();
                        salads.add(newSalad);
                    } else {
                        System.out.println("Failed to create salad, salad is exist or vegetables for salad is empty");
                    }
                    break;
                case 4:
                    Salad desiredSalad = getSalad(getInputAndNotify("Salad name for searching").nextLine());
                    desiredSalad.printSortedIngredients();
                    desiredSalad.getTotalCalories();
                    break;
                case 5:
                    for(Salad salad: salads) System.out.println(salad.toString());
                    break;
                case 6:
                    Salad saladForFindVegetablesByRange = getSalad(
                            getInputAndNotify("Salad name for searching").nextLine());
                    int caloriesStartRange = getInputAndNotify("Calories start range").nextInt();
                    int caloriesEndRange = getInputAndNotify("Calories end range").nextInt();
                    for (Vegetable ingredient: saladForFindVegetablesByRange.getIngredients()) {
                        if (ingredient.getCalories() > caloriesStartRange && ingredient.getCalories() < caloriesEndRange) {
                            System.out.println(ingredient);
                        }
                    }
                    break;
                case 0:
                    isRunning = false;
            }
        }
    }
    private Salad getSalad(String saladName) {
        List<Salad> filteredSalads = salads.stream()
                .filter(s -> s.getName().equalsIgnoreCase(saladName))
                .toList();
        if (filteredSalads.size() == 0) throw new IllegalArgumentException("Salad not found");
        return filteredSalads.get(0);
    }
    private static Scanner getInputAndNotify(String notificationMsg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("Please input: %s", notificationMsg));
        return scanner;
    }
}
