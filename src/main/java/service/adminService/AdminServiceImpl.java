package service.adminService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.adminRepository.AdminRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long, AdminRepository> implements AdminService {
    public AdminServiceImpl(AdminRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Optional<Admin> signIn(String username, String password) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Admin> admins = repository.findByUsername(username);
            session.getTransaction().commit();
            if (admins.isEmpty()) {
                return Optional.empty();
            }
            Admin admin = admins.get(0);
            if (admin.getPassword().equals(password)) {
                return Optional.of(admin);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();

        }
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Admin> admins = repository.findByUsername(username);
            session.getTransaction().commit();
            if (admins.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(admins.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Service> getAllServices() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Service> services = repository.getAllServices();
            session.getTransaction().commit();
            return services;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Service addService(Service service) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Service> addedServiceOptional = repository.addService(service);
            session.getTransaction().commit();
            // Check if the service was added successfully

            if (addedServiceOptional.isPresent()) {
                return addedServiceOptional.get();
            } else {
                return null; // or throw an exception, depending on your requirements
            }
        } catch (Exception e) {
            return null; // or throw an exception, depending on your requirements
        }
    }

    @Override
    public Service updateService(Service service) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Service updatedService = repository.updateService(service);
            session.getTransaction().commit();
            return updatedService;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteService(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            repository.deleteService(id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SubService> getAllSubServices() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<SubService> subServices = repository.getAllSubServices();
            session.getTransaction().commit();
            return subServices;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public SubService addSubService(SubService subService) {
        try (Session session = sessionFactory.getCurrentSession()) {
        session.beginTransaction();
        SubService addedSubService = repository.addSubService(subService).orElse(null);
        session.getTransaction().commit();
        return addedSubService;
    } catch (Exception e) {
        return null;
    }
    }

    @Override
    public SubService updateSubService(SubService subService) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            SubService updatedSubService = repository.updateSubService(subService);
            session.getTransaction().commit();
            return updatedSubService;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteSubService(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            SubService subService = session.get(SubService.class, id);
            if (subService!= null) {
                repository.deleteSubService(id);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
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
    public Customer addCustomer(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Customer addedCustomer = repository.addCustomer(customer);
            session.getTransaction().commit();
            return addedCustomer;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Customer updatedCustomer = repository.addCustomer(customer);
            session.getTransaction().commit();
            return updatedCustomer;

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
    public List<Specialist> getAllSpecialists() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Specialist> specialistList = repository.getAllSpecialists();
            session.getTransaction().commit();
            return specialistList;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Specialist addSpecialist(Specialist specialist) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Specialist addedSpecialist = repository.addSpecialist(specialist);
            session.getTransaction().commit();
            return addedSpecialist;
        } catch (Exception e) {

            return null;

        }
    }

    @Override
    public Specialist updateSpecialist(Specialist specialist) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Specialist updatedSpecialist = (Specialist) session.merge(specialist);
            session.getTransaction().commit();
            return updatedSpecialist;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteSpecialist(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Specialist specialist = session.get(Specialist.class, id);
            if (specialist!= null) {
                session.delete(specialist);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSpecialistToSubService(Long specialistId, Long subServiceId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            repository.addSpecialistToSubService(specialistId, subServiceId);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSpecialistFromSubService(Long specialistId, Long subServiceId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            repository.removeSpecialistFromSubService(specialistId, subServiceId);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAllOrders() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Order> orders = repository.getAllOrders();
            session.getTransaction().commit();
            return orders;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Order addOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Order addedOrder = repository.addOrder(order);
            session.getTransaction().commit();
            return addedOrder;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Order updateOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Order updatedOrder = repository.updateOrder(order);
            session.getTransaction().commit();
            return updatedOrder;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteOrder(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order!= null) {
                session.delete(order);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
