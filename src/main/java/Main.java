import menu.AdminMenu;
import menu.MainMenu;
import model.Customer;
import service.customerService.CustomerService;
import utility.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMenu();
    }
}
