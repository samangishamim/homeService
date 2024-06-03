package repository.adminRepository;

import base.repository.BaseRepositoryImpl;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AdminRepositoryImpl extends BaseRepositoryImpl<Admin, Long> implements AdminRepository {
    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Admin> getEntityClass() {
        return Admin.class;
    }

    @Override
    public String getMyClass() {
        return "user";
    }


    @Override
    public Optional<Admin> signIn(String username, String password) {
        Session session = sessionFactory.getCurrentSession();

        Query<Admin> query = session.createQuery("FROM Admin a WHERE a.username = :username AND a.password = :password", Admin.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        Admin admin = query.uniqueResultOptional().orElse(null);
        return Optional.ofNullable(admin);
    }

    @Override
    public List<Admin> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();

        Query<Admin> query = session.createQuery("FROM Admin a WHERE a.username = :username", Admin.class);

        query.setParameter("username", username);

        return query.getResultList();
    }

    @Override
    public List<Service> getAllServices() {
        Session session = sessionFactory.getCurrentSession();
        Query<Service> query = session.createQuery("FROM Service", Service.class);
        return query.getResultList();
    }

    @Override
    public Optional<Service> addService(Service service) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Service> query = session.createQuery("FROM Service s where s.name=:name", Service.class);
            query.setParameter("name", service.getName());
            List<Service> services = query.getResultList();
            if (services.isEmpty()) {
                session.save(service);
                session.getTransaction().commit();
                return Optional.of(service);
            } else {
                session.getTransaction().rollback();
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Service updateService(Service service) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Service> query = session.createQuery("FROM Service s where s.id=:id", Service.class);
            query.setParameter("id", service.getId());
            List<Service> services = query.getResultList();
            if (!services.isEmpty()) {
                session.update(service);
                session.getTransaction().commit();
                return service;
            } else {
                session.getTransaction().rollback();
                return null;
            }
        } catch (Exception e) {
            return null; // or throw an exception, depending on your requirements
        }
    }

    @Override
    public void deleteService(Long id) {
        Session session = sessionFactory.getCurrentSession();
        if (!session.getTransaction().isActive()) {
            session.beginTransaction();
        }
        try {
            Query<Service> query = session.createQuery("FROM Service s where s.id=:id", Service.class);
            query.setParameter("id", id);
            List<Service> services = query.getResultList();
            if (!services.isEmpty()) {
                Service service = services.get(0);
                session.delete(service);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<SubService> getAllSubServices() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<SubService> subServices = session.createQuery("FROM SubService", SubService.class).list();
            session.getTransaction().commit();
            return subServices;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<SubService> addSubService(SubService subService) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<SubService> query = session.createQuery("FROM SubService s where s.serviceId=:serviceId and s.name=:name", SubService.class);
            query.setParameter("serviceId", subService.getId());
            query.setParameter("name", subService.getName());
            List<SubService> subServices = query.getResultList();
            if (subServices.isEmpty()) {
                session.save(subService);
                session.getTransaction().commit();
                return Optional.of(subService);
            } else {
                session.getTransaction().rollback();
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public SubService updateSubService(SubService subService) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<SubService> query = session.createQuery("UPDATE SubService SET name = :name, basePrice = :basePrice, description = :description WHERE id = :id", SubService.class);
            query.setParameter("name", subService.getName());
            query.setParameter("basePrice", subService.getBasePrice());
            query.setParameter("description", subService.getDescription());
            query.setParameter("id", subService.getId());

            int result = query.executeUpdate();
            session.getTransaction().commit();
            if (result > 0) {
                return subService;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteSubService(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {

            session.beginTransaction();

            Query query = session.createQuery("delete from SubService where id = :id");

            query.setParameter("id", id);

            query.executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {

            e.printStackTrace();

        }
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
    public Customer addCustomer(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {

            session.beginTransaction();

            Query<Customer> query = session.createQuery("FROM Customer c WHERE c.email = :email", Customer.class);

            query.setParameter("email", customer.getEmail());
            List<Customer> customers = query.getResultList();
            if (customers.isEmpty()) {
                session.save(customer);
                session.getTransaction().commit();
                return customer;
            } else {
                session.getTransaction().rollback();
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Specialist> getAllSpecialists() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Specialist> query = session.createQuery("FROM Specialist", Specialist.class);
            List<Specialist> specialists = query.getResultList();
            session.getTransaction().commit();
            return specialists;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Specialist addSpecialist(Specialist specialist) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(specialist);
            session.getTransaction().commit();
            return specialist;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void deleteSpecialist(Long id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Specialist specialist = session.get(Specialist.class, id);
            if (specialist != null) {
                session.delete(specialist);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void removeSpecialistFromSubService(Long specialistId, Long subServiceId) {

        try (Session session = sessionFactory.getCurrentSession()) {

            session.beginTransaction();

            Query query = session.createQuery("update Specialist s set s.subServices = :subServices where s.id = :specialistId");

            query.setParameter("subServices", getSubServicesWithoutSubServiceId(subServiceId, specialistId));

            query.setParameter("specialistId", specialistId);

            query.executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    public List<Order> getAllOrders() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Order o order by o.id desc");
            List<Order> orders = query.list();
            session.getTransaction().commit();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Order addOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Query query = session.createQuery("insert into Order (description, proposedPrice, workDate, customer, subService, status, address) values (:description, :proposedPrice, :workDate, :customer, :subService, :status, :address)");
            query.setParameter("description", order.getDescription());
            query.setParameter("proposedPrice", order.getProposedPrice());
            query.setParameter("workDate", order.getWorkDate());
            query.setParameter("customer", order.getCustomer());
            query.setParameter("subService", order.getSubService());
            query.setParameter("status", order.getStatus());
            query.setParameter("address", order.getAddress());

            int result = query.executeUpdate();
            session.getTransaction().commit();
            if (result > 0) {
                return order;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order updateOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createQuery("update Order set description = :description, proposedPrice = :proposedPrice, workDate = :workDate, customer = :customer, subService = :subService, status = :status, address = :address where id = :id");

            query.setParameter("description", order.getDescription());
            query.setParameter("proposedPrice", order.getProposedPrice());
            query.setParameter("workDate", order.getWorkDate());
            query.setParameter("customer", order.getCustomer());
            query.setParameter("subService", order.getSubService());
            query.setParameter("status", order.getStatus());
            query.setParameter("address", order.getAddress());
            query.setParameter("id", order.getId());

            int result = query.executeUpdate();
            session.getTransaction().commit();
            if (result > 0) {
                return order;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addSpecialistToSubService(Long specialistId, Long subServiceId) {
        try {

            Session session = sessionFactory.getCurrentSession();


            Query query = session.createQuery("FROM Specialist s WHERE s.id = :specialistId");

            query.setParameter("specialistId", specialistId);

            Specialist specialist = (Specialist) query.uniqueResult();


            query = session.createQuery("FROM SubService s WHERE s.id = :subServiceId");

            query.setParameter("subServiceId", subServiceId);

            SubService subService = (SubService) query.uniqueResult();


            if (specialist != null && subService != null) {

                specialist.getSubServices().add(subService);

                subService.getSpecialists().add(specialist);

            }

        } catch (  Exception e) {

            String errorMessage = e.getMessage();

                System.err.println("Exception: " + errorMessage);
            }
    }


    private List<SubService> getSubServicesWithoutSubServiceId(Long subServiceId, Long specialistId) {

        try (Session session = sessionFactory.getCurrentSession()) {

            session.beginTransaction();

            Query query = session.createQuery("select s.subServices from Specialist s where s.id = :specialistId");

            query.setParameter("specialistId", specialistId);

            List<SubService> subServices = query.list();

            subServices.removeIf(subService -> subService.getId().equals(subServiceId));

            return subServices;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }
    }
}
