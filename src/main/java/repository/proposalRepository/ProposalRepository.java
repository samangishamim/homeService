package repository.proposalRepository;

import base.repository.BaseRepository;
import model.Order;
import model.Proposal;
import model.Specialist;

import java.util.List;
import java.util.Optional;

public interface ProposalRepository extends BaseRepository<Proposal, Long> {

    Optional<List<Proposal>> findByOrderId(Long orderId);

    Optional<List<Proposal>> findBySpecialistId(Long specialistId);

    Optional<Proposal> findByOrderIdAndSpecialistId(Long orderId, Long specialistId);
    List<Proposal> getProposalsByOrderId(Long orderId);
    Proposal addProposal(Proposal proposal);
    Proposal updateProposal(Proposal proposal);

    List<Proposal> findByOrder(Order order);
    List<Proposal> getProposalsBySpecialist(Boolean isAccepted);

}