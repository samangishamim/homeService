package repository.subserviceRepository;

import base.repository.BaseRepository;
import model.Service;
import model.SubService;

import java.util.List;
import java.util.Optional;

public interface SubServiceRepository extends BaseRepository<SubService, Long> {

    Optional<List<SubService>> findByServiceId(Long serviceId);
    List<SubService> getAllSubServices();
    List<SubService> addSubService(SubService subService);

    void deleteSubService(Long id);
    SubService getSubServiceByName(String name);
    List<SubService> findByService(Service service);

}
