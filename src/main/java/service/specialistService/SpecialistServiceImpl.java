package service.specialistService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.Customer;
import model.Order;
import model.Specialist;
import model.SubService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.specialistRepository.SpecialistRepository;

import java.util.Collections;
import java.util.List;

@Slf4j

public class SpecialistServiceImpl extends BaseServiceImpl<Specialist, Long, SpecialistRepository> implements SpecialistService {
    public SpecialistServiceImpl(SpecialistRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
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
            if (specialist != null) {
                session.delete(specialist);
                session.getTransaction().commit();
                System.out.println("Specialist deleted successfully!");
            } else {
                System.out.println("Specialist not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSpecialistToSubService(Long specialistId, Long subServiceId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Specialist specialist = session.get(Specialist.class, specialistId);
            SubService subService = session.get(SubService.class, subServiceId);
            if (specialist != null && subService != null) {
                specialist.getSubServices().add(subService);
                subService.getSpecialists().add(specialist);
                session.save(specialist);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void removeSpecialistFromSubService(Long specialistId, Long subServiceId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Specialist specialist = session.get(Specialist.class, specialistId);
            SubService subService = session.get(SubService.class, subServiceId);
            if (specialist != null && subService != null) {
                specialist.getSubServices().remove(subService);
                subService.getSpecialists().remove(specialist);
                session.save(specialist);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Specialist updateSpecialistCredit(Long specialistId, double credit) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Specialist specialist = session.get(Specialist.class, specialistId);
            if (specialist != null) {
                specialist.setCredit(credit); // now this will work
                session.save(specialist);
            }
            session.getTransaction().commit();
            return specialist;
        } catch (Exception e) {
            return null;
        }
    }

    public Specialist getSpecialistByName(String name) {

        return repository.findByName(name);

    }

    @Override
    public Specialist findByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email, password);    }

    @Override
    public void saveUpdate(Specialist specialist) {
        repository.saveOrUpdate(specialist);
    }

    @Override
    public void update(Specialist specialist) {
        repository.saveOrUpdate(specialist);
    }

    @Override
    public void sendProposal(Order order) {

    }
}
