package repository.proposalRepository;

import base.repository.BaseRepository;
import model.Proposal;
import java.util.List;
import java.util.Optional;

public interface ProposalRepository extends BaseRepository<Proposal, Long> {

    Optional<List<Proposal>> findByOrderId(Long orderId);

    Optional<List<Proposal>> findBySpecialistId(Long specialistId);

    Optional<Proposal> findByOrderIdAndSpecialistId(Long orderId, Long specialistId);

}