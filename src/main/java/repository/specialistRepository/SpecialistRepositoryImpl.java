package repository.specialistRepository;

import base.repository.BaseRepositoryImpl;
import model.Specialist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SpecialistRepositoryImpl extends BaseRepositoryImpl<Specialist,Long>implements SpecialistRepository {
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
}
