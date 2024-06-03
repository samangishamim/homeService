package repository.customerRepository;

import base.repository.BaseRepository;
import model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends BaseRepository<Customer, Long> {
    Optional<List<Customer>> findByEmail(String email);
    Optional<Customer> signIn(String email, String password);
}
