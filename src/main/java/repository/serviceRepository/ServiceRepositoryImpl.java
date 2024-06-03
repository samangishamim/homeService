package repository.serviceRepository;

import base.repository.BaseRepository;
import base.repository.BaseRepositoryImpl;
import model.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ServiceRepositoryImpl extends BaseRepositoryImpl<Service,Long> implements ServiceRepository{

    public ServiceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Service> getEntityClass() {
        return Service.class;
    }

    @Override
    public String getMyClass() {
        return "service";
    }

    @Override
    public List<Service> findServicesByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Service> query = session.createQuery("FROM Service s WHERE s.name = :name", Service.class);
        query.setParameter("name", name);

        return query.getResultList();
    }

    @Override
    public boolean existsServiceById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Service> query = session.createQuery("FROM Service s WHERE s.id = :id", Service.class);
        query.setParameter("id", id);

        return !query.getResultList().isEmpty();
    }
}
