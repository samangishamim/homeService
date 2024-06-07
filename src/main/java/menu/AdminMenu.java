package menu;

import lombok.extern.slf4j.Slf4j;
import model.*;
import myEnum.Status;
import org.apache.commons.text.RandomStringGenerator;
import service.adminService.AdminService;
import service.serviceService.ServiceService;
import service.specialistService.SpecialistService;
import service.subserviceService.SubServiceService;
import utility.ApplicationContext;
import utility.Input;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class AdminMenu {
    final AdminService adminService = ApplicationContext.getAdminService();

    final ServiceService serviceService = ApplicationContext.getServiceService();

    final SubServiceService subServiceService = ApplicationContext.getSubServiceService();

    final SpecialistService specialistService = ApplicationContext.getSpecialistService();

    Scanner scanner = new Scanner(System.in);


    public void firstMenu() {
        while (true) {

            System.out.println("******  Welcome to the Admin Menu! ******");

            System.out.println("1. sign up");

            System.out.println("2. sign in");

            System.out.println("0. exit");
            int choice = Input.getIntegerNum();
            switch (choice) {

                case 1:

                    signUp();

                    break;

                case 2:

                    signIn();

                    break;

                case 0:

                    System.out.println("Exiting...");

                    return;

                default:

                    System.out.println("Invalid choice. Please try again.");

            }

        }
    }

    public void signIn() {

        System.out.println("**** sign in ****");
        System.out.println("Please enter username :");

        String username = Input.getString();
        System.out.println("Please enter password:");
        String password = Input.getString();

        Admin admin = adminService.findByUserNameAndPassword(username, password);
        if (admin == null) {
            System.out.println("Invalid email or password. Please try again.");
            return;
        }
        while (true) {
            System.out.println("1. Add Service");
            System.out.println("2. Add Sub-Service");
            System.out.println("3. edit Sub-Service");
            System.out.println("4. enable Specialist ");
            System.out.println("5. Add Specialist to Sub-Service");

            System.out.println("6. Remove Specialist from Sub-Service");

            System.out.println("7. See Service and sub service List");

            System.out.println("0. Exit");


            int choice = Input.getIntegerNum();


            switch (choice) {

                case 1:

                    addService();

                    break;

                case 2:

                    addSubService();

                    break;

                case 3:

                    editSubService();

                    break;

                case 4:

                    enableSpecialist();

                    break;

                case 5:

                    addSpecialistToSubService();

                    break;

                case 6:

                    removeSpecialistFromSubService();

                    break;

                case 7:

                    seeServiceList();

                    break;
                case 0:

                    System.out.println("Exiting...");

                    return;

                default:

                    System.out.println("Invalid choice. Please try again.");

            }

        }

    }


    private void addService() {

        System.out.print("Enter service name: ");

        String serviceName = Input.getString();


        Service.ServiceBuilder serviceBuilder = Service.builder();

        Service service = serviceBuilder

                .name(serviceName)

                .build();


        serviceService.addService(service);

    }


    private void addSubService() {


        System.out.print("Enter service id: ");
        Long serviceId = Long.parseLong(scanner.nextLine());
        Service service = serviceService.findById(serviceId);

        System.out.print("Enter sub service name: ");
        String subServiceName = Input.getString();

        System.out.print("Enter sub service description: ");
        String subServiceDescription = Input.getString();

        System.out.print("Enter sub service base price: ");
        double subServiceBasePrice = Input.getDoubleNum();

        SubService.SubServiceBuilder subServiceBuilder = SubService.builder();
        SubService subService = subServiceBuilder
                .name(subServiceName)
                .description(subServiceDescription)
                .basePrice(subServiceBasePrice)
                .build();
        subService.setService(service);
        subServiceService.addSubService(subService);
    }

    public void enableSpecialist() {

        System.out.print("Enter specialist ID: ");

        Long specialistId = scanner.nextLong();

        Specialist specialist = specialistService.findById(specialistId);

        specialist.setEnable(true);
        specialist.setSpecialistStatus(Status.CONFIRMED);

        specialistService.updateSpecialist(specialist);

        System.out.println("Specialist enabled successfully!");

    }

    public void removeSpecialistFromSubService() {

        System.out.print("Enter specialist ID: ");
        Long specialistId = Input.getLongNum();

        System.out.print("Enter sub-service ID: ");
        Long subServiceId = Input.getLongNum();


        subServiceService.removeSpecialist(specialistId,subServiceId);
    }

    private void addSpecialistToSubService() {

        System.out.print("Enter sub service id: ");
        Long subServiceId = Input.getLongNum();


        System.out.print("Enter specialist id: ");
        Long specialistId = Input.getLongNum();

        subServiceService.addSpecialistToSubservice(specialistId, subServiceId);
    }


    private void seeServiceList() {
        List<Service> services = serviceService.getAllServices();
        System.out.println("--------------------");
        for (Service service : services) {
            System.out.println("service profile ");
            System.out.println("service id:" + service.getId());
            System.out.println("Service Name: " + service.getName());
            System.out.println("  ********   ");
            for (SubService subService : service.getSubServices()) {
                System.out.println("* sub service profile ");
                System.out.println("sub service id :" + subService.getId());
                System.out.println("Sub Service Name: " + subService.getName());
                System.out.println("Description: " + subService.getDescription());
                System.out.println("Base Price: " + subService.getBasePrice());
                System.out.println("--------------------");
//                for (Specialist specialist : subService.getSpecialists()) {
//                    System.out.println("    Specialist Name: " + specialist.getFirstName());
//                }
            }
        }
    }

    public void editSubService() {

        System.out.print("Enter sub-service ID: ");
        Long subServiceId = Long.parseLong(scanner.nextLine());

        SubService subService = subServiceService.findById(subServiceId);

        System.out.print("Enter new sub-service description: ");
        String newSubServiceDescription = scanner.nextLine();

        System.out.print("Enter new sub-service base price: ");
        double newSubServiceBasePrice = Double.parseDouble(scanner.nextLine());


        subService.setDescription(newSubServiceDescription);
        subService.setBasePrice(newSubServiceBasePrice);

        subServiceService.saveOrUpdate(subService);
    }

    private void seeSubServiceList() {

        List<SubService> subServices = subServiceService.getAllSubServices();
        for (SubService subService : subServices) {
            System.out.println("Sub Service Name: " + subService.getName());
            System.out.println("  Description: " + subService.getDescription());
            System.out.println("  Base Price: " + subService.getBasePrice());
            for (Specialist specialist : subService.getSpecialists()) {
                System.out.println("  Specialist Name: " + specialist.getFirstName());
            }
        }
    }


    public void signUp() {
        System.out.println("**** sign in  ****");
        System.out.println("Please enter username :");
        String userName = Input.getString();
        String password = autoPassword(8);
        Admin admin = Admin
                .builder()
                .username(userName)
                .password(password)
                .build();

        adminService.saveOrUpdate(admin);
        System.out.println("username : " + userName);
        System.out.println("password : " + password);
    }

    private String autoPassword(int passLen) {
        RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .build();
        String password = generator.generate(passLen);
        return password;
    }
}