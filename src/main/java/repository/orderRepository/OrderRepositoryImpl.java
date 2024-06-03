package repository.orderRepository;

import base.repository.BaseRepositoryImpl;
import model.Order;
import myEnum.OrderStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl extends BaseRepositoryImpl<Order, Long> implements OrderRepository {
    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Order> getEntityClass() {
        return Order.class;
    }

    @Override
    public String getMyClass() {
        return "order";
    }

    @Override
    public Optional<List<Order>> findByCustomer(Long customerId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("FROM Order o WHERE o.customer.id = :customerId", Order.class);
        query.setParameter("customerId", customerId);
        List<Order> orderList = query.getResultList();

        return Optional.ofNullable(orderList);
    }

    @Override
    public Optional<List<Order>> findBySubService(Long subServiceId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("FROM Order o WHERE o.subService.id = :subServiceId", Order.class);
        query.setParameter("subServiceId", subServiceId);
        List<Order> orderList = query.getResultList();

        return Optional.ofNullable(orderList);
    }

    @Override
    public Optional<List<Order>> findByStatus(OrderStatus status) {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("FROM Order o WHERE o.status = :status", Order.class);
        query.setParameter("status", status);
        List<Order> orderList = query.getResultList();

        return Optional.ofNullable(orderList);
    }
}
