package service.subserviceService;

import base.service.BaseService;
import model.SubService;

import java.util.List;

public interface SubServiceService extends BaseService<SubService,Long> {
    List<SubService> getAllSubServices();

    SubService addSubService(SubService subService);

    SubService updateSubService(SubService subService);

    void deleteSubService(Long id);
}
