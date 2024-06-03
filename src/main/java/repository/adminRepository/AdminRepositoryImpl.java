package repository.adminRepository;

import base.repository.BaseRepositoryImpl;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AdminRepositoryImpl extends BaseRepositoryImpl<Admin, Long> implements AdminRepository {
    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Admin> getEntityClass() {
        return Admin.class;
    }

    @Override
    public String getMyClass() {
        return "user";
    }


    @Override
    public Optional<Admin> signIn(String username, String password) {
        Session session = sessionFactory.getCurrentSession();

        Query<Admin> query = session.createQuery("FROM Admin a WHERE a.username = :username AND a.password = :password", Admin.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        Admin admin = query.uniqueResultOptional().orElse(null);
        return Optional.ofNullable(admin);
    }

    @Override
    public List<Admin> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();

        Query<Admin> query = session.createQuery("FROM Admin a WHERE a.username = :username", Admin.class);

        query.setParameter("username", username);

        return query.getResultList();
    }
}
