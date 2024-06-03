package service.specialistService;

import base.service.BaseService;
import model.Customer;
import model.Specialist;

import java.util.List;

public interface SpecialistService extends BaseService<Specialist,Long> {
    List<Specialist> getAllSpecialists();

    Specialist addSpecialist(Specialist specialist);

    Specialist updateSpecialist(Specialist specialist);

    void deleteSpecialist(Long id);

    void addSpecialistToSubService(Long specialistId, Long subServiceId);

    void removeSpecialistFromSubService(Long specialistId, Long subServiceId);

}

