package service.customerService;

import base.service.BaseService;
import model.Customer;
import model.Person;

import java.util.List;
import java.util.Optional;

public interface CustomerService  extends BaseService<Customer,Long> {

    Optional<Customer> signIn(String email, String password);

    List<Customer> getAllCustomers();

    void deleteCustomer(Long id);
    Customer updateCustomerCredit(Long customerId, double credit);
    Customer findByEmailAndPassword(String email, String password);
}
