package repository.adminRepository;

import base.repository.BaseRepository;
import model.*;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends BaseRepository<Admin, Long> {
    Optional<Admin> signIn(String username, String password);

    List<Admin> findByUsername(String username);
    List<Service> getAllServices();
    Optional<Service> addService(Service service);
    Service updateService(Service service);
    void deleteService(Long id);
    List<SubService> getAllSubServices();
    Optional<SubService> addSubService(SubService subService);
    SubService updateSubService(SubService subService);
    void deleteSubService(Long id);
    List<Customer> getAllCustomers();
    Customer addCustomer(Customer customer);
     List<Specialist> getAllSpecialists();
    Specialist addSpecialist(Specialist specialist);
    void deleteSpecialist(Long id);
    void removeSpecialistFromSubService(Long specialistId, Long subServiceId);
    List<Order> getAllOrders();
    Order addOrder(Order order);
    Order updateOrder(Order order);
    void addSpecialistToSubService(Long specialistId, Long subServiceId);
}
