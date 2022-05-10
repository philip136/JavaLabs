public class Main {
    private static MainMenu mainMenu;

    public static void main(String[] args) {
        mainMenu = new MainMenu(new HomeControlUnit());
        mainMenu.showMenu();
    }
}
