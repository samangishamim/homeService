package repository.subserviceRepository;

import base.repository.BaseRepositoryImpl;
import model.Service;
import model.SubService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class SubServiceRepositoryImpl extends BaseRepositoryImpl<SubService, Long> implements SubServiceRepository {
    public SubServiceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<SubService> getEntityClass() {
        return SubService.class;
    }

    @Override
    public String getMyClass() {
        return "sub_service";
    }

    @Override
    public Optional<List<SubService>> findByServiceId(Long serviceId) {
        Session session = sessionFactory.getCurrentSession();
        Query<SubService> query = session.createQuery("FROM SubService s WHERE s.service.id = :serviceId", SubService.class);
        query.setParameter("serviceId", serviceId);
        List<SubService> subServiceList = query.getResultList();
        return Optional.ofNullable(subServiceList);
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
    public List<SubService> addSubService(SubService subService) {
        Session session = sessionFactory.getCurrentSession();
        Query<SubService> query = session.createQuery("FROM SubService s where s.name=:name", SubService.class);
        query.setParameter("name", subService.getName());
        return query.getResultList();
    }



    @Override
    public void deleteSubService(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Query query = session.createQuery("delete from SubService where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SubService getSubServiceByName(String name) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Query<SubService> query = session.createQuery("from SubService where name = :name", SubService.class);
            query.setParameter("name", name);
            SubService subService = query.uniqueResult();
            return subService;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SubService> findByService(Service service) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<SubService> query = session.createQuery("SELECT s FROM SubService s WHERE s.service = :service", SubService.class);
            query.setParameter("service", service);
            List<SubService> subServices = query.getResultList();
            session.getTransaction().commit();
            return subServices;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
