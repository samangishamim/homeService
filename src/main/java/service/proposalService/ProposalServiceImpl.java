package service.proposalService;

import base.service.BaseServiceImpl;
import model.Proposal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.proposalRepository.ProposalRepository;

import java.util.Collections;
import java.util.List;

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
    public Proposal addProposal(Proposal proposal) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Proposal addedProposal = repository.addProposal(proposal);
            session.getTransaction().commit();
            return addedProposal;
        } catch (Exception e) {
            return null;
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
            e.printStackTrace();
        }
    }
}
