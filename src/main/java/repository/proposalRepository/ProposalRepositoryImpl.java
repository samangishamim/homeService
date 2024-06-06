package repository.proposalRepository;

import base.repository.BaseRepositoryImpl;
import model.Order;
import model.Proposal;
import model.Specialist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProposalRepositoryImpl extends BaseRepositoryImpl<Proposal, Long> implements ProposalRepository {
    public ProposalRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Proposal> getEntityClass() {
        return Proposal.class;
    }

    @Override
    public String getMyClass() {
        return "proposal";
    }

    @Override
    public Optional<List<Proposal>> findByOrderId(Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Proposal> query = session.createQuery("FROM Proposal p WHERE p.order.id = :orderId", Proposal.class);
        query.setParameter("orderId", orderId);
        List<Proposal> proposalList = query.getResultList();

        return Optional.ofNullable(proposalList);

    }


    @Override

    public Optional<List<Proposal>> findBySpecialistId(Long specialistId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Proposal> query = session.createQuery("FROM Proposal p WHERE p.specialist.id = :specialistId", Proposal.class);
        query.setParameter("specialistId", specialistId);
        List<Proposal> proposalList = query.getResultList();

        return Optional.ofNullable(proposalList);

    }


    @Override
    public Optional<Proposal> findByOrderIdAndSpecialistId(Long orderId, Long specialistId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Proposal> query = session.createQuery("FROM Proposal p WHERE p.order.id = :orderId AND p.specialist.id = :specialistId", Proposal.class);
        query.setParameter("orderId", orderId);
        query.setParameter("specialistId", specialistId);
        Proposal proposal = query.uniqueResult();
        return Optional.ofNullable(proposal);

    }

    @Override
    public List<Proposal> getProposalsByOrderId(Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Proposal> query = session.createQuery("FROM Proposal p WHERE p.order.id = :orderId", Proposal.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();

    }

    @Override

    public Proposal addProposal(Proposal proposal) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(proposal);
        return proposal;
    }


    @Override
    public Proposal updateProposal(Proposal proposal) {
        Session session = sessionFactory.getCurrentSession();
        session.update(proposal);
        return proposal;
    }

    @Override
    public List<Proposal> findByOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Proposal> query = session.createQuery("FROM Proposal p WHERE p.order = :order", Proposal.class);
            query.setParameter("order", order);
            List<Proposal> proposals = query.getResultList();
            session.getTransaction().commit();
            return proposals;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Proposal> getProposalsBySpecialist(Boolean isAccepted) {
        Session session = sessionFactory.getCurrentSession();
            Query<Proposal> query = session.createQuery("FROM Proposal p WHERE p.isAccepted=:isAccepted", Proposal.class);
            query.setParameter("isAccepted", isAccepted);
            return query.getResultList();
    }
}