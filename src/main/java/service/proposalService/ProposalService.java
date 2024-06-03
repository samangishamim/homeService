package service.proposalService;

import base.service.BaseService;
import model.Proposal;

import java.util.List;

public interface ProposalService extends BaseService<Proposal,Long> {
    List<Proposal> getProposalsByOrderId(Long orderId);
    Proposal addProposal(Proposal proposal);
    Proposal updateProposal(Proposal proposal);
    void deleteProposal(Long id);

}
