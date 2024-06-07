package repository.customerRepository;

import base.repository.BaseRepositoryImpl;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl extends BaseRepositoryImpl<Customer, Long> implements CustomerRepository {

    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }

    @Override
    public String getMyClass() {
        return "customer";
    }

    @Override
    public Optional<List<Customer>> findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<Customer> query = session.createQuery("FROM Customer c WHERE c.email = :email", Customer.class);
        query.setParameter("email", email);

        List<Customer> customerList = query.getResultList();
        return Optional.ofNullable(customerList);
    }

    @Override
    public Optional<Customer> signIn(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query<Customer> query = session.createQuery("FROM Customer c WHERE c.email = :email AND c.password = :password", Customer.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        Customer customer = query.uniqueResultOptional().orElse(null);

        return Optional.ofNullable(customer);
    }




    @Override
    public List<Customer> getAllCustomers() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Customer");
            List<Customer> customers = query.list();
            session.getTransaction().commit();
            return customers;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Customer findByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query<Customer> query = session.createQuery("from Person  p where p.email =: email and p.password =: password", Customer.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        Customer customer= query.uniqueResult();
        session.getTransaction().commit();
        return customer;
    }

}
