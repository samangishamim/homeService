package service.serviceService;

import base.service.BaseService;
import model.Service;

import java.util.List;

public interface ServiceService  extends BaseService<Service,Long> {
    List<Service> getAllServices();

    Service addService(Service service);

    Service updateService(Service service);

    void deleteService(Long id);
}
