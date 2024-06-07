package service.proposalService;

import base.service.BaseService;
import model.Order;
import model.Proposal;

import java.util.List;

public interface ProposalService extends BaseService<Proposal,Long> {
    List<Proposal> getProposalsByOrderId(Long orderId);
    Proposal addProposal(Proposal proposal);
    Proposal updateProposal(Proposal proposal);
    void deleteProposal(Long id);
    List<Proposal> getProposalsByOrder(Order order);
    List<Proposal> getProposalsBySpecialist(Boolean isAccepted);
    void acceptProposal(Long proposalId);
    Proposal findByOrderIdAndAccepted(Long orderId);
}

