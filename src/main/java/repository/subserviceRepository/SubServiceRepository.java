package repository.subserviceRepository;

import base.repository.BaseRepository;
import model.SubService;

import java.util.List;
import java.util.Optional;

public interface SubServiceRepository extends BaseRepository<SubService, Long> {

    Optional<List<SubService>> findByServiceId(Long serviceId);
}
