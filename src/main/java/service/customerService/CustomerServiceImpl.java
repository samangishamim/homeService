package service.customerService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.customerRepository.CustomerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long, CustomerRepository> implements CustomerService {
    public CustomerServiceImpl(CustomerRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }



    @Override
    public Optional<Customer> signIn(String email, String password) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<Customer>> customersOptional = repository.findByEmail(email);
            session.getTransaction().commit();
            if (customersOptional.isPresent()) {
                List<Customer> customers = customersOptional.get();
                if (!customers.isEmpty()) {
                    Customer customer = customers.get(0);
                    if (customer.getPassword().equals(password)) {
                        return Optional.of(customer);
                    }
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Customer> customers = repository.getAllCustomers();
            session.getTransaction().commit();
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            // Get the existing customer from the database
            Customer existingCustomer = session.get(Customer.class, customer.getId());


            // Update the properties of the existing customer
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPassword(customer.getPassword());
            existingCustomer.setCredit(customer.getCredit());


            // Save the updated customer to the database
            session.save(existingCustomer);
            session.getTransaction().commit();
            return existingCustomer;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            if (customer!= null) {
                session.delete(customer);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override

    public Customer updateCustomerCredit(Long customerId, double credit) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, customerId);
            if (customer != null) {
                customer.setCredit(credit);
                session.save(customer);
            }
            session.getTransaction().commit();
            return customer;
        } catch (Exception e) {
            return null;
        }
    }
}
