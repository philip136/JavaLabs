import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Scanner;

@ToString
@AllArgsConstructor
public class Menu {
    private List<Salad> salads;
    public Salad makeOrder() throws ArrayIndexOutOfBoundsException {
        System.out.println("Please select a salad");
        for (Salad salad: salads) System.out.println(salad.toString());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String saladName = scanner.nextLine();
            List<Salad> neededSalad = salads.stream().filter(s -> s.getName().equalsIgnoreCase(saladName)).toList();
            if (neededSalad.size() == 0) throw new ArrayIndexOutOfBoundsException("There is no salad with that name");
            else System.out.println("Bon appetit");
            return neededSalad.get(0);
        }
    }
}
