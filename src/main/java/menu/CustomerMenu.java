package menu;

import model.*;
import myEnum.OrderStatus;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
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
            System.out.println("***** Welcome to the Customer Menu! ****** ");
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
        Customer customer = null;

        while (true) {

            System.out.println("Please enter email:");
            String email = Input.getString();

            System.out.println("Please enter password:");
            String password = Input.getString();

            customer = customerService.findByEmailAndPassword(email, password);
            if (customer == null) {
                System.out.println("Invalid email or password. Please try again.");
                continue;
            }
            while (true) {
                System.out.println("1. register order ");
                System.out.println("2. change password ");
                System.out.println("3. see profile ");
                System.out.println("4. update profile  ");
                System.out.println("5. select proposal  ");
                System.out.println("6. do payment  ");
                System.out.println("7. add credit ");
                System.out.println("0. exit  ");
                int choice = Input.getIntegerNum();
                switch (choice) {
                    case 1:
                        registerOrder(customer);
                        break;
                    case 2:
                        changePassword(customer.getId());
                        break;
                    case 3:
                        seeProfile(customer);
                        break;
                    case 4:
                        updateProfile(customer);
                        break;
                    case 5:
                        selectProposal(customer);
                        break;
                    case 6:
                        doPayment(customer);
                        break;
                    case 7:
                        addCredit(customer);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
    }

    public void signUp() {
        System.out.println("**** sign up  ****");
        System.out.println("Please enter firstname :");
        String firstName = Input.getString();

        System.out.println("Please enter lastname :");
        String lastName = Input.getString();
        String email = getValidEmail();
        String password = autoPassword(8);
        Person person = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();

        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .registrationDateTime(LocalDateTime.now())
                .build();
        customerService.saveOrUpdate(customer);
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

    public void registerOrder(Customer customer) {
        System.out.println("Select a service:");
        List<Service> services = serviceService.getAllServices();
        for (int i = 0; i < services.size(); i++) {
            System.out.println((i + 1) + ". " + services.get(i).getName());
        }
        int serviceChoice = Input.getIntegerNum();
        Service service = services.get(serviceChoice - 1);
        System.out.println("Select a sub-service:");
        List<SubService> subServices = subServiceService.getSubServicesByService(service);
        for (int i = 0; i < subServices.size(); i++) {
            System.out.println((i + 1) + ". " + subServices.get(i).getName());
        }
        int subServiceChoice = Input.getIntegerNum();
        SubService subService = subServices.get(subServiceChoice - 1);

        System.out.println("Enter order description:");
        String description = scanner.nextLine();

        System.out.println("Enter proposed price:");
        double price = Input.getDoubleNum();

        System.out.println("Enter delivery date and time:");
        LocalDateTime deliveryDate = Input.getLocalDateTime();

        System.out.println("Enter province:");
        String province = scanner.nextLine();

        System.out.println("Enter city:");
        String city = scanner.nextLine();

        System.out.println("Enter detail:");
        String detail = scanner.nextLine();
        Address address = Address.builder()
                .province(province)
                .city(city)
                .detail(detail)
                .build();

        Order order = Order.builder()
                .description(description)
                .proposedPrice(price)
                .workDate(deliveryDate)
                .address(address)
                .subService(subService)
                .status(OrderStatus.WAITING_FOR_SPECIALIST_PROPOSAL)
                .customer(customer)
                .build();


        orderService.saveAnOrder(order, address, customer.getId(), subService.getId());
        System.out.println("Order registered successfully!");
    }

    public void changePassword(Long customerId) {

        Customer customer = customerService.findById(customerId);
        System.out.println("enter your old password ");
        String oldPassword = scanner.nextLine();
        if (!oldPassword.equals(customer.getPassword())) {
            System.out.println("Invalid old password. Please try again.");
            return;
        }
        String newPassword1 = getNewPassword("enter your new password : ");
        String newPassword2 = getNewPassword("enter your new password again : ");
        if (!newPassword1.equals(newPassword2)) {
            return;
        }

        customer.setPassword(newPassword1);
        customerService.saveOrUpdate(customer);
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


    private String getNewPassword(String announce) {
        String newPassword;
        while (true) {
            System.out.println(announce);
            newPassword = scanner.nextLine();
            if (newPassword.trim().isEmpty()) {
                System.out.println("New password cannot be empty. Please try again.");
            } else {
                break;
            }
        }
        return newPassword;
    }

    public void seeProfile(Customer customer) {
        System.out.println("Profile:");
        System.out.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Credit: " + customer.getCredit());
    }


    public void updateProfile(Customer customer) {

        System.out.println("Enter new first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter new last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter new email:");
        String email = scanner.nextLine();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customerService.saveOrUpdate(customer);
        System.out.println("Profile updated successfully!");

    }

    public void selectProposal(Customer customer) {
        System.out.println("Select an order:");
        List<Order> orders = orderService.getOrdersByCustomer(customer);
        for (int i = 0; i < orders.size(); i++) {
            System.out.println((i + 1) + ". " + orders.get(i).getDescription());
        }
        int orderChoice = Input.getIntegerNum();
        Order order = orders.get(orderChoice - 1);
        System.out.println("Select a proposal:");
        List<Proposal> proposals = proposalService.getProposalsByOrder(order);
        for (int i = 0; i < proposals.size(); i++) {
            System.out.println((i + 1) + ". duration :  " + proposals.get(i).getDuration());
        }
        int proposalChoice = Input.getIntegerNum();
        Proposal proposal = proposals.get(proposalChoice - 1);


        proposalService.acceptProposal(proposal.getId());

        orderService.updateOrder(order.getId(), OrderStatus.WAITING_FOR_SPECIALIST_SELECTION);
        System.out.println("Proposal selected successfully!");
    }


    public void doPayment(Customer customer) {
        System.out.println("Select an order:");
        List<Order> orders = orderService.getOrdersByCustomer(customer);
        List<Order> newOrders = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getStatus() == OrderStatus.STARTED) {
                newOrders.add(orders.get(i));
            }
        }
        for (int i = 0; i < newOrders.size(); i++) {
            System.out.println((i + 1) + ". " + newOrders.get(i).getDescription());
        }
        int orderChoice = Input.getIntegerNum();
        Order order = newOrders.get(orderChoice - 1);
        order.setStatus(OrderStatus.DONE);
        orderService.saveOrUpdate(order);

        Proposal proposal = proposalService.findByOrderIdAndAccepted(order.getId());
        Specialist specialist = proposal.getSpecialist();

        double paymentAmount = proposal.getProposedPrice();

        double credit = customer.getCredit();
        if (paymentAmount > credit) {
            System.out.println("charge your credit");
            return;
        }
        customer.setCredit(credit - paymentAmount);
        customerService.saveOrUpdate(customer);

        double credit1 = specialist.getCredit();
        specialist.setCredit(credit1+paymentAmount);
        specialistService.saveOrUpdate(specialist);
        System.out.println("payment is done");
    }


    public void addCredit(Customer customer) {
        System.out.println("Enter amount to add to credit:");
        double amount = Input.getDoubleNum();
        customer.setCredit(customer.getCredit() + amount);
        customerService.saveOrUpdate(customer);
        System.out.println("Credit added successfully!");
    }
}
