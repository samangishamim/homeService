package service.adminService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.adminRepository.AdminRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long, AdminRepository> implements AdminService {
    public AdminServiceImpl(AdminRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Optional<Admin> signIn(String username, String password) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Admin> admins = repository.findByUsername(username);
            session.getTransaction().commit();
            if (admins.isEmpty()) {
                return Optional.empty();
            }
            Admin admin = admins.get(0);
            if (admin.getPassword().equals(password)) {
                return Optional.of(admin);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();

        }
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Admin> admins = repository.findByUsername(username);
            session.getTransaction().commit();
            if (admins.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(admins.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
