package service.serviceService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.serviceRepository.ServiceRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ServiceServiceImpl extends BaseServiceImpl<Service, Long, ServiceRepository> implements ServiceService {
    public ServiceServiceImpl(ServiceRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Service> getAllServices() {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<Service> services = repository.getAllServices();
            session.getTransaction();
            return services;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Service addService(Service service) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Service> services = repository.addService(service);

            if (services.isEmpty()) {
                Service service1 = repository.saveOrUpdate(service);
                session.getTransaction().commit();
                log.info("the service : {} is added ", service1);
                return service1;

            } else {
                log.warn("duplicate service");
                return null;
            }
        }catch (Exception e){
            log.error("error - adding service ");
            return null;
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
