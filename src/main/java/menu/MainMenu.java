package menu;

import model.*;
import menu.AdminMenu;
import menu.CustomerMenu;
import menu.SpecialistMenu;

import java.util.Scanner;

public class MainMenu {

    Scanner scanner = new Scanner(System.in);

    public void displayMenu() {

        int userChoice = -1;
        while (userChoice!=0) {
            System.out.println("**** Choose between the following options: *****");
            System.out.println("1. Admin");
            System.out.println("2. Specialist");
            System.out.println("3. Customer");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            userChoice = Integer.parseInt(scanner.nextLine());
            switch (userChoice) {

                case 1:

                    AdminMenu adminMenu = new AdminMenu();

                    adminMenu.firstMenu();

                    break;

                case 2:

                    SpecialistMenu specialistMenu = new SpecialistMenu();

                    specialistMenu.start();

                    break;

                case 3:

                    CustomerMenu customerMenu = new CustomerMenu();

                    customerMenu.start();

                    break;

                case 0:

                    System.out.println("Exiting the program. Goodbye!");

                    System.exit(0);

                    break;

                default:

                    System.out.println("Invalid choice. Please choose a number between 1 and 4.");

                    break;

            }
        }

    }
}