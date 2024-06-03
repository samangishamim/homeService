package repository.subserviceRepository;

import base.repository.BaseRepositoryImpl;
import model.SubService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SubServiceRepositoryImpl extends BaseRepositoryImpl<SubService,Long>implements SubServiceRepository {
    public SubServiceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<SubService> getEntityClass() {
        return SubService.class;
    }

    @Override
    public String getMyClass() {
        return "sub_service";
    }

    @Override
    public Optional<List<SubService>> findByServiceId(Long serviceId) {
        Session session = sessionFactory.getCurrentSession();
        Query<SubService> query = session.createQuery("FROM SubService s WHERE s.service.id = :serviceId", SubService.class);
        query.setParameter("serviceId", serviceId);
        List<SubService> subServiceList = query.getResultList();
        return Optional.ofNullable(subServiceList);
    }
}
