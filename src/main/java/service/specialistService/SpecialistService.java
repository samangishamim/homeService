package service.specialistService;

import base.service.BaseService;
import model.Customer;
import model.Order;
import model.Specialist;

import java.util.List;

public interface SpecialistService extends BaseService<Specialist,Long> {
    List<Specialist> getAllSpecialists();

    Specialist addSpecialist(Specialist specialist);

    Specialist updateSpecialist(Specialist specialist);

    void deleteSpecialist(Long id);

    void addSpecialistToSubService(Long specialistId, Long subServiceId);

    void removeSpecialistFromSubService(Long specialistId, Long subServiceId);

    Specialist updateSpecialistCredit(Long specialistId, double credit);
    Specialist getSpecialistByName(String name);
    Specialist findByEmailAndPassword(String email, String password);
    void saveUpdate(Specialist specialist);
    void update(Specialist specialist);
        void sendProposal(Order order);
}

