import Exceptions.NotZeroOrNegativeException;


import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String pathToFile = Paths.get(System.getProperty("user.dir"), "sample.txt").toString();

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException, NotZeroOrNegativeException {
        Vegetable cucumber = new Vegetable(Vegetables.CUCUMBER, 132, 16);
        Vegetable tomato = new Vegetable(Vegetables.TOMATO, 140, 19.9);
        Vegetable onion = new Vegetable(Vegetables.ONION, 112, 40);
        Vegetable broccoli = new Vegetable(Vegetables.BROCCOLI, 452, 34);
        Vegetable pepper = new Vegetable(Vegetables.PEPPER, 226, 27);

        List<Vegetable> standardSaladIngredients = Arrays.asList(cucumber, tomato, onion);
        List<Vegetable> broccoliSaladIngredients = Arrays.asList(cucumber, broccoli, pepper, onion);

        Salad standard = Salad.builder().name("Standard").ingredients(standardSaladIngredients).build();
        Salad broccoliSalad = Salad.builder().name("BroccoliSalad").ingredients(broccoliSaladIngredients).build();
        Menu menu = new Menu(Arrays.asList(standard, broccoliSalad));
        Salad orderedSalad = menu.makeOrder();
        orderedSalad.printSortedIngredients();
        orderedSalad.getTotalCalories();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pathToFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(orderedSalad);
            objectOutputStream.flush();
            objectOutputStream.close();

            FileInputStream fileInputStream = new FileInputStream(pathToFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Salad saladFromFile = (Salad) objectInputStream.readObject();
            System.out.println(String.format("Object of salad from file: %s", saladFromFile.toString()));
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
