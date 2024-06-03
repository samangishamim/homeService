package service.customerService;

import base.service.BaseService;
import model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService  extends BaseService<Customer,Long> {

    Optional<Customer> signIn(String email, String password);

    List<Customer> getAllCustomers();
    Customer updateCustomer(Customer customer);
    void deleteCustomer(Long id);
}
