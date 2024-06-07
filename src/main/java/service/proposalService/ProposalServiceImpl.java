package service.proposalService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.Order;
import model.Proposal;
import model.Specialist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import repository.proposalRepository.ProposalRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
public class ProposalServiceImpl extends BaseServiceImpl<Proposal, Long, ProposalRepository> implements ProposalService {
    public ProposalServiceImpl(ProposalRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Proposal> getProposalsByOrderId(Long orderId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Proposal> proposals = repository.getProposalsByOrderId(orderId);
            session.getTransaction().commit();
            return proposals;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Proposal updateProposal(Proposal proposal) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Proposal updatedProposal = repository.updateProposal(proposal);
            session.getTransaction().commit();
            return updatedProposal;
        } catch (Exception e) {
            System.out.println("An error occurred while updating the proposal: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteProposal(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Proposal proposal = session.get(Proposal.class, id);
            if (proposal != null) {
                session.delete(proposal);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the proposal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Proposal> getProposalsByOrder(Order order) {
        return repository.findByOrder(order);
    }

    @Override
    public List<Proposal> getProposalsBySpecialist(Boolean isAccepted) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Proposal> proposals = repository.getProposalsBySpecialist(isAccepted);
            session.getTransaction().commit();
            return proposals;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void acceptProposal(Long proposalId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Proposal proposal = session.get(Proposal.class, proposalId);
            proposal.setAccepted(true);
            repository.saveOrUpdate(proposal);
            session.getTransaction().commit();
            log.info("the proposal with id : {}  is accepted", proposalId);
        }
    }

    @Override
    public Proposal findByOrderIdAndAccepted(Long orderId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Proposal> query = session.createQuery("FROM Proposal p join p.order o where o.id =:oId and p.isAccepted ", Proposal.class);
            query.setParameter("oId",orderId);
            Proposal proposal = query.uniqueResult();
            log.info("the proposal with id : {}  is accepted", orderId);
            return proposal;
        }
    }



    @Override
    public Proposal addProposal(Proposal proposal) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            if (proposal.getProposedPrice() <= 0 || proposal.getDuration() <= 0) {
                throw new Exception("Invalid proposal details");
            }
            Proposal addedProposal = repository.addProposal(proposal);
            session.getTransaction().commit();
            return addedProposal;
        } catch (Exception e) {
            System.out.println("An error occurred while adding the proposal: " + e.getMessage());
            return null;
        }
    }
}
