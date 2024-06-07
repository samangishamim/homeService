package service.subserviceService;

import base.service.BaseService;
import model.Service;
import model.SubService;

import java.util.List;

public interface SubServiceService extends BaseService<SubService,Long> {
    List<SubService> getAllSubServices();

    SubService addSubService(SubService subService);

    void deleteSubService(Long id);
    SubService getSubServiceByName(String name);
    List<SubService> getSubServicesByService(Service service);

    void addSpecialistToSubservice(Long specialistId, Long subServiceId);
    void update(SubService subService);

    void removeSpecialist(Long specialistId, Long subServiceId);

}
