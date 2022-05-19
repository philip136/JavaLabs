import Exceptions.NotZeroOrNegativeException;


import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String pathToFile = Paths.get(System.getProperty("user.dir"), "sample.txt").toString();

    public static void main(String[] args) throws NotZeroOrNegativeException {
        // Lab №1,2
        Menu menu = new Menu();
        menu.showMenu();

        // Lab №3
        Vegetable cucumber = new Vegetable(Vegetables.CUCUMBER, 132, 16);
        Vegetable tomato = new Vegetable(Vegetables.TOMATO, 140, 19.9);
        List<Vegetable> standardSaladIngredients = Arrays.asList(cucumber, tomato);
        Salad salad = Salad.builder().name("RussianStandard").ingredients(standardSaladIngredients).build();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pathToFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(salad);
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
