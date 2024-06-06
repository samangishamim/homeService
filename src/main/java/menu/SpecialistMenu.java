package menu;

import model.*;
import myEnum.OrderStatus;
import myEnum.Status;
import org.apache.commons.text.RandomStringGenerator;
import repository.customerRepository.CustomerRepository;
import repository.specialistRepository.SpecialistRepository;
import repository.subserviceRepository.SubServiceRepository;
import service.adminService.AdminService;
import service.customerService.CustomerService;
import service.orderService.OrderService;
import service.proposalService.ProposalService;
import service.serviceService.ServiceService;
import service.specialistService.SpecialistService;
import service.subserviceService.SubServiceService;
import utility.ApplicationContext;
import utility.Input;
import utility.Validation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SpecialistMenu {
    private final AdminService adminService = ApplicationContext.getAdminService();

    private final ServiceService serviceService = ApplicationContext.getServiceService();

    private final OrderService orderService = ApplicationContext.getOrderService();

    private final SubServiceService subServiceService = ApplicationContext.getSubServiceService();

    private final SpecialistService specialistService = ApplicationContext.getSpecialistService();
    private final CustomerService customerService = ApplicationContext.getCustomerService();
    private final ProposalService proposalService = ApplicationContext.getProposalService();
    Scanner scanner = new Scanner(System.in);


    public void start() {
        while (true) {
            System.out.println("Welcome to the Specialist Menu!");
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
        System.out.println("Please enter email:");
        String email = Input.getString();
        System.out.println("Please enter password:");
        String password = Input.getString();
        Specialist specialist = specialistService.findByEmailAndPassword(email, password);
        if (specialist == null) {
            System.out.println("Invalid email or password. Please try again.");
            return;
        }
        while (true) {
            System.out.println("1. send proposal");
            System.out.println("2. see proposal accept list ");
            System.out.println("3. change password");
            System.out.println("4. see  profile ");
            System.out.println("5. update profile ");
            System.out.println("0. exit");
            int choice = Input.getIntegerNum();
            switch (choice) {
                case 1:
                    sendProposal(specialist);
                    break;
                case 2:
                    seeProposalAcceptList();
                    break;
                case 3:
                    changePassword(specialist);
                    break;
                case 4:
                    seeProfile(specialist);
                    break;
                case 5:
                    updateProfile(specialist);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void sendProposal(Specialist specialist) {
        System.out.println("Sending proposal...");
        Order order = getOrderFromUser();
        System.out.println("duration : ");
        int duration = Integer.parseInt(scanner.nextLine());

        System.out.println("price : ");
        double price = Double.parseDouble(scanner.nextLine());

        Proposal proposal = Proposal.builder()
                .order(order)
                .duration(duration)
                .proposedPrice(price)
                .proposalDateTime(LocalDateTime.now())
                .specialist(specialist)
                .build();
        proposalService.saveOrUpdate(proposal);
        System.out.println("Proposal sent successfully!");
    }


    private Order getOrderFromUser() {
        List<Order> orders = orderService.getOrdersByStatus(OrderStatus.WAITING_FOR_SPECIALIST_PROPOSAL);
        if (orders.isEmpty()) {
            System.out.println("No orders available. Please try again later.");
            return null;
        }
        System.out.println("Available orders:");
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.println((i + 1) + ". Order ID: " + order.getId() + ", Customer: " + order.getCustomer().getFirstName());
        }
        System.out.println("Please enter the number of the order you'd like to propose:");
        int orderNum = Input.getIntegerNum() - 1;
        if (orderNum < 0 || orderNum >= orders.size()) {
            System.out.println("Invalid order number. Please try again.");
            return getOrderFromUser();
        }

        return orders.get(orderNum);
    }


    private void seeProposalAcceptList() {
        List<Proposal> proposalsBySpecialist = proposalService.getProposalsBySpecialist(Boolean.TRUE);
        for (Proposal proposal : proposalsBySpecialist) {
            System.out.println("the accepted list : "+proposal.isAccepted());
            System.out.println("the specialist first name : "+proposal.getSpecialist().getFirstName());
            System.out.println("the specialist last name : "+proposal.getSpecialist().getLastName());
        }
    }
    public void changePassword(Specialist specialist) {
        String oldPassword = getOldPassword();
        if (!oldPassword.equals(specialist.getPassword())) {
            System.out.println("Invalid old password. Please try again.");
            return;
        }
        String newPassword = getNewPassword();
        specialist.setPassword(newPassword);
        specialistService.updateSpecialist(specialist);
        System.out.println("Password changed successfully!");
    }


    private String getOldPassword() {
        String oldPassword;
        while (true) {
            System.out.println("Enter old password:");
            oldPassword = scanner.nextLine();
            if (oldPassword.trim().isEmpty()) {
                System.out.println("Old password cannot be empty. Please try again.");
            } else {
                break;
            }
        }
        return oldPassword;
    }


    private String getNewPassword() {
        String newPassword;
        while (true) {
            System.out.println("Enter new password:");
            newPassword = scanner.nextLine();
            if (newPassword.trim().isEmpty()) {
                System.out.println("New password cannot be empty. Please try again.");
            } else {
                break;
            }
        }
        String confirmPassword;
        while (true) {
            System.out.println("Enter new password again:");
            confirmPassword = scanner.nextLine();
            if (confirmPassword.trim().isEmpty()) {
                System.out.println("Confirm password cannot be empty. Please try again.");
            } else if (!newPassword.equals(confirmPassword)) {
                System.out.println("New passwords do not match. Please try again.");
            } else {
                break;
            }
        }
        return newPassword;
    }


    private void seeProfile(Specialist specialist) {
        if (specialist != null) {
            System.out.println("Seeing profile...");
            System.out.println("Name: " + specialist.getFirstName() + " " + specialist.getLastName());
            System.out.println("Email: " + specialist.getEmail());
            System.out.println("Score: " + specialist.getScore());
            System.out.println("Enable: " + specialist.isEnable());
            System.out.println("Credit: " + specialist.getCredit());
            System.out.println("Specialist Status: " + specialist.getSpecialistStatus());
        } else {
            System.out.println("Specialist profile not found.");
        }
    }


    private void updateProfile(Specialist specialist) {
        System.out.println("Please enter new first name:");
        String newFirstName = Input.getString();
        specialist.setFirstName(newFirstName);

        System.out.println("Please enter new last name:");
        String newLastName = Input.getString();
        specialist.setLastName(newLastName);

        specialistService.saveOrUpdate(specialist);
        System.out.println("Profile updated successfully.");
    }

    public void signUp() {
        System.out.println("**** sign up as a specialist ****");

        System.out.println("Please enter firstname :");
        String firstName = Input.getString();

        System.out.println("Please enter lastname :");
        String lastName = Input.getString();
        String email = getValidEmail();
        String password = autoPassword(8);
        System.out.println("Please upload your personal photo (max 300KB, JPG format):");
        byte[] photo = uploadPhoto();
        Specialist specialist = Specialist.builder()
                .registrationDateTime(LocalDateTime.now())
                .score(0)
                .enable(false)
                .credit(0.0)
                .specialistStatus(Status.CONFIRMED)
                .photo(photo)
                .build();
        specialist.setFirstName(firstName);
        specialist.setLastName(lastName);
        specialist.setEmail(email);
        specialist.setPassword(password);
        System.out.println("Select sub-services:");
        List<SubService> subServices = subServiceService.getAllSubServices();
        for (int i = 0; i < subServices.size(); i++) {
            System.out.println((i + 1) + ". " + subServices.get(i).getName());
        }
        List<Integer> selectedSubServices = new ArrayList<>();
        while (true) {
            System.out.println("Enter the number of the sub-service (0 to finish):");
            int choice = Input.getIntegerNum();
            if (choice == 0) {
                break;
            }
            if (choice < 1 || choice > subServices.size()) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
            selectedSubServices.add(choice - 1);
        }
        List<SubService> specialistSubServices = new ArrayList<>();
        for (int i : selectedSubServices) {
            specialistSubServices.add(subServices.get(i));
        }
        specialist.setSubServices(specialistSubServices);
        specialistService.saveOrUpdate(specialist);
        System.out.println("username : " + firstName + " " + lastName);
        System.out.println("password : " + password);
        System.out.println("email : " + email);
    }

    public String autoPassword(int passLen) {
        RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .build();
        String password = generator.generate(passLen);
        return password;
    }

    public String getValidEmail() {
        Scanner scanner = new Scanner(System.in);
        String email;
        while (true) {
            System.out.println("Please enter your email :");
            email = scanner.nextLine();
            if (Validation.validateEmail(email))
                break;
            else
                System.out.println("Invalid email address. Please try again.");
        }
        return email;
    }

    private String getContentType(File file) {
        String contentType = "unknown";
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
            contentType = "image/jpeg";
        } else {
            System.out.println("Error: File is not a valid JPG file.");
        }
        return contentType;
    }

    private byte[] uploadPhoto() {
        byte[] photo = null;
        while (true) {
            System.out.println("Enter the file path of your photo:");
            String filePath = Input.getString();
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                if (file.length() <= 300 * 1024) { // 300KB
                    String fileType = getContentType(file);
                    if (fileType.equals("image/jpeg")) { // JPG format
                        try {
                            photo = Files.readAllBytes(file.toPath());
                            break;
                        } catch (Exception e) {
                            System.out.println("Error reading file: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid file format. Please upload a JPG file.");
                    }
                } else {
                    System.out.println("File size exceeds 300KB. Please upload a smaller file.");
                }
            } else {
                System.out.println("Invalid file path. Please try again.");
            }
        }
        return photo;
    }
}
