package repository.orderRepository;

import base.repository.BaseRepositoryImpl;
import model.Order;
import myEnum.OrderStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.Collections;
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

    @Override
    public List<Order> getAllOrders() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Order o order by o.id desc");
            List<Order> orders = query.list();
            session.getTransaction().commit();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public Order addOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Query query = session.createQuery("insert into Order (description, proposedPrice, workDate, customer, subService, status, address) values (:description, :proposedPrice, :workDate, :customer, :subService, :status, :address)");
            query.setParameter("description", order.getDescription());
            query.setParameter("proposedPrice", order.getProposedPrice());
            query.setParameter("workDate", order.getWorkDate());
            query.setParameter("customer", order.getCustomer());
            query.setParameter("subService", order.getSubService());
            query.setParameter("status", order.getStatus());
            query.setParameter("address", order.getAddress());

            int result = query.executeUpdate();
            session.getTransaction().commit();
            if (result > 0) {
                return order;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order updateOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createQuery("update Order set description = :description, proposedPrice = :proposedPrice, workDate = :workDate, customer = :customer, subService = :subService, status = :status, address = :address where id = :id");

            query.setParameter("description", order.getDescription());
            query.setParameter("proposedPrice", order.getProposedPrice());
            query.setParameter("workDate", order.getWorkDate());
            query.setParameter("customer", order.getCustomer());
            query.setParameter("subService", order.getSubService());
            query.setParameter("status", order.getStatus());
            query.setParameter("address", order.getAddress());
            query.setParameter("id", order.getId());

            int result = query.executeUpdate();
            session.getTransaction().commit();
            if (result > 0) {
                return order;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
