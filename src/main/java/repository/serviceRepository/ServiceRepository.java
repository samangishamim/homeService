package repository.serviceRepository;

import base.repository.BaseRepository;
import model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends BaseRepository<Service,Long> {

    List<Service> getAllServices();
    List<Service> addService(Service service);
    Service updateService(Service service);
    void deleteService(Long id);
}
