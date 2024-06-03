package repository.subserviceRepository;

import base.repository.BaseRepositoryImpl;
import model.SubService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SubServiceRepositoryImpl extends BaseRepositoryImpl<SubService,Long>implements SubServiceRepository {
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
}
