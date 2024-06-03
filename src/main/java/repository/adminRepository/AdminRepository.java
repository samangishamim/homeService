package repository.adminRepository;

import base.repository.BaseRepository;
import model.*;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends BaseRepository<Admin, Long> {
    Optional<Admin> signIn(String username, String password);

    List<Admin> findByUsername(String username);

}
