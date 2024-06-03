package repository.serviceRepository;

import base.repository.BaseRepository;
import model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends BaseRepository<Service,Long> {
    List<Service> findServicesByName(String name);


    boolean existsServiceById(Long id);
    List<Service> getAllServices();
    Optional<Service> addService(Service service);
    Service updateService(Service service);
    void deleteService(Long id);
}
