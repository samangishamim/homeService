package service.serviceService;

import base.service.BaseServiceImpl;
import model.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.serviceRepository.ServiceRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ServiceServiceImpl extends BaseServiceImpl<Service, Long, ServiceRepository> implements ServiceService {
    public ServiceServiceImpl(ServiceRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
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
}
