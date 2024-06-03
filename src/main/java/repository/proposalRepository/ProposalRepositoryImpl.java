package repository.proposalRepository;

import base.repository.BaseRepositoryImpl;
import model.Proposal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProposalRepositoryImpl extends BaseRepositoryImpl<Proposal, Long> implements ProposalRepository{
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
}
