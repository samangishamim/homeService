package repository.serviceRepository;

import base.repository.BaseRepository;
import model.Service;

import java.util.List;

public interface ServiceRepository extends BaseRepository<Service,Long> {
    List<Service> findServicesByName(String name);


    boolean existsServiceById(Long id);
}
