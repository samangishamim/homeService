package repository.specialistRepository;

import base.repository.BaseRepositoryImpl;
import model.Specialist;
import model.SubService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SpecialistRepositoryImpl extends BaseRepositoryImpl<Specialist, Long> implements SpecialistRepository {
    public SpecialistRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Specialist> getEntityClass() {
        return Specialist.class;
    }

    @Override
    public String getMyClass() {
        return "specialist";
    }

    @Override
    public Optional<List<Specialist>> findByOrderId(Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Specialist> query = session.createQuery("FROM Specialist s WHERE s.order.id = :orderId", Specialist.class);
        query.setParameter("orderId", orderId);
        List<Specialist> specialistList = query.getResultList();
        return Optional.ofNullable(specialistList);

    }


    @Override

    public Optional<List<Specialist>> findByProposalId(Long proposalId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Specialist> query = session.createQuery("FROM Specialist s WHERE s.proposal.id = :proposalId", Specialist.class);
        query.setParameter("proposalId", proposalId);
        List<Specialist> specialistList = query.getResultList();
        return Optional.ofNullable(specialistList);

    }


    @Override

    public Optional<Specialist> findByOrderIdAndProposalId(Long orderId, Long proposalId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Specialist> query = session.createQuery("FROM Specialist s WHERE s.order.id = :orderId AND s.proposal.id = :proposalId", Specialist.class);
        query.setParameter("orderId", orderId);
        query.setParameter("proposalId", proposalId);
        Specialist specialist = query.uniqueResult();
        return Optional.ofNullable(specialist);

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

    @Override
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

        } catch (Exception e) {

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
