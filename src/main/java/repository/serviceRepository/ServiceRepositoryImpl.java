package repository.serviceRepository;

import base.repository.BaseRepository;
import base.repository.BaseRepositoryImpl;
import exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import model.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@Slf4j
public class ServiceRepositoryImpl extends BaseRepositoryImpl<Service, Long> implements ServiceRepository {

    public ServiceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Service> getEntityClass() {
        return Service.class;
    }

    @Override
    public String getMyClass() {
        return "service";
    }

    @Override
    public List<Service> findServicesByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Service> query = session.createQuery("FROM Service s WHERE s.name = :name", Service.class);
        query.setParameter("name", name);

        return query.getResultList();
    }

    @Override
    public boolean existsServiceById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Service> query = session.createQuery("FROM Service s WHERE s.id = :id", Service.class);
        query.setParameter("id", id);

        return !query.getResultList().isEmpty();
    }

    @Override
    public List<Service> getAllServices() {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Service> query = session.createQuery("FROM Service", Service.class);
            List<Service> services = query.getResultList();
            session.getTransaction().commit();
            return services;
        } catch (Exception e) {
            log.error("An error occurred while retrieving services", e);
            return null;
        }
    }

    @Override
    public List<Service> addService(Service service) {
        Session session = sessionFactory.getCurrentSession();
        Query<Service> query = session.createQuery("FROM Service s where s.name=:name", Service.class);
        query.setParameter("name", service.getName());
        List<Service> services = query.getResultList();
       return services;
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
}
