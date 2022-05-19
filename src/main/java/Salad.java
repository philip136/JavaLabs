import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.*;
import java.util.Collections;
import java.util.List;
@Builder
@ToString
@AllArgsConstructor
public class Salad implements Serializable {
    @Getter
    private String name;
    @Getter
    private List<Vegetable> ingredients;
    public void getTotalCalories() {
        int totalCalories = (int) ingredients.stream().mapToDouble(Vegetable::getTotalCalories).sum();
        System.out.println(String.format("Total calories in salad equal %s", totalCalories));
    }
    public void printSortedIngredients() {
        System.out.println("Sorted ingredients by calories:");
        List<Vegetable> vegetables = ingredients;
        Collections.sort(vegetables);
        for (Vegetable vegetable: vegetables) System.out.println(vegetable);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (Vegetable vegetable: ingredients) objectOutputStream.writeObject(vegetable);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        try {
            objectInputStream.defaultReadObject();
            for(int i=0; i < ingredients.size(); i++) {
                Vegetable vegetable = (Vegetable) objectInputStream.readObject();
                System.out.println(String.format("Deserializing vegetable: %s", vegetable));
            }
            
        } catch (EOFException e) {
            System.out.println(e.getMessage());
        }
    }
}


