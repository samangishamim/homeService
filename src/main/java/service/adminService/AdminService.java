package service.adminService;

import base.service.BaseService;
import model.*;

import java.util.List;
import java.util.Optional;

public interface AdminService extends BaseService<Admin,Long> {
    Optional<Admin> signIn(String username, String password);


    Optional<Admin> findByUsername(String username) ;


    List<Service> getAllServices();


    Service addService(Service service);


    Service updateService(Service service);


    void deleteService(Long id);


    List<SubService> getAllSubServices();


    SubService addSubService(SubService subService);


    SubService updateSubService(SubService subService);


    void deleteSubService(Long id);


    List<Customer> getAllCustomers();


    Customer addCustomer(Customer customer);


    Customer updateCustomer(Customer customer);


    void deleteCustomer(Long id);


    List<Specialist> getAllSpecialists();


    Specialist addSpecialist(Specialist specialist);


    Specialist updateSpecialist(Specialist specialist);


    void deleteSpecialist(Long id);


    void addSpecialistToSubService(Long specialistId, Long subServiceId);


    void removeSpecialistFromSubService(Long specialistId, Long subServiceId);


    List<Order> getAllOrders();


    Order addOrder(Order order);


    Order updateOrder(Order order);


    void deleteOrder(Long id);

}
