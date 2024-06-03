package service.adminService;

import base.service.BaseService;
import model.*;

import java.util.List;
import java.util.Optional;

public interface AdminService extends BaseService<Admin,Long> {
    Optional<Admin> signIn(String username, String password);


    Optional<Admin> findByUsername(String username) ;

}
