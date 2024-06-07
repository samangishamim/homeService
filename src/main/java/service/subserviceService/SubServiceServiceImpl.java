package service.subserviceService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.Service;
import model.Specialist;
import model.SubService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.subserviceRepository.SubServiceRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
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
            List<SubService> subServiceList = repository.addSubService(subService);
            if (subServiceList.isEmpty()) {
                SubService subService1 = repository.saveOrUpdate(subService);
                session.getTransaction().commit();
                log.info("the service : {} is added ", subService1);
                return subService1;
            } else {
                log.warn("duplicate sub service");
                return null;
            }

        } catch (Exception e) {
            log.error("error - adding sub service");
            return null;
        }
    }


    @Override
    public void deleteSubService(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            SubService subService = session.get(SubService.class, id);
            if (subService != null) {
                repository.deleteSubService(id);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SubService getSubServiceByName(String name) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            SubService getSub = repository.getSubServiceByName(name);
            return getSub;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SubService> getSubServicesByService(Service service) {

        return repository.findByService(service);

    }

    @Override
    public void addSpecialistToSubservice(Long specialistId, Long subServiceId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Specialist specialist = session.get(Specialist.class, specialistId);
            SubService subService = session.get(SubService.class, subServiceId);

            subService.addSpecialist(specialist);
            repository.saveOrUpdate(subService);
            session.getTransaction().commit();
            log.info("the specialist with id : {} is added to sub service with id: {} ", specialistId, subServiceId);
        }
    }

    @Override
    public void update(SubService subService) {
        repository.saveOrUpdate(subService);
    }

    @Override
    public void removeSpecialist(Long specialistId, Long subServiceId) {
        try (Session session=sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Specialist specialist = session.get(Specialist.class, specialistId);
            SubService subService = session.get(SubService.class, subServiceId);

            subService.removeSpecialist(specialist);
            repository.saveOrUpdate(subService);
            session.getTransaction().commit();
            log.info("specialist with id : {} is remove from sub service with id : {} ", specialistId,subServiceId);
        }catch (Exception e){
            log.error("error - removing specialist from sub service");
        }
    }
}
