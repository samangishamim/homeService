package service.subserviceService;

import base.service.BaseServiceImpl;
import model.SubService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.subserviceRepository.SubServiceRepository;

import java.util.Collections;
import java.util.List;

public class SubServiceServiceImpl extends BaseServiceImpl<SubService, Long, SubServiceRepository> implements SubServiceService {
    public SubServiceServiceImpl(SubServiceRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
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
}
