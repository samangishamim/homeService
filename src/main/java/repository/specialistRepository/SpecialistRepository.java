package repository.specialistRepository;

import base.repository.BaseRepository;
import model.Specialist;

import java.util.List;
import java.util.Optional;

public interface SpecialistRepository  extends BaseRepository<Specialist, Long> {



    Optional<Specialist> findByOrderIdAndProposalId(Long orderId, Long proposalId);
    List<Specialist> getAllSpecialists();
    Specialist addSpecialist(Specialist specialist);
    void deleteSpecialist(Long id);
    void removeSpecialistFromSubService(Long specialistId, Long subServiceId);
    void addSpecialistToSubService(Long specialistId, Long subServiceId);
    Specialist findByName(String name);
    Specialist updateSpecialist(Specialist specialist);
    Specialist findByEmailAndPassword(String email, String password);
    public Specialist updateSpecialistCredit(Long specialistId, double credit);
    void saveUpdate(Specialist specialist);
    void update(Specialist specialist);

}
