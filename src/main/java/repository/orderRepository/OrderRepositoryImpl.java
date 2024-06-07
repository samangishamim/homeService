package repository.orderRepository;

import base.repository.BaseRepositoryImpl;
import model.Customer;
import model.Order;
import model.Specialist;
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
    public List<Order> getAllOrders() {
       Session session = sessionFactory.getCurrentSession();
            Query<Order> query = session.createQuery("from Order o order by o.id desc", Order.class);
            return query.getResultList();
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
    public List<Order> findByCustomer(Customer customer) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Query<Order> query = session.createQuery("FROM Order o WHERE o.customer = :customer", Order.class);
            query.setParameter("customer", customer);
            List<Order> orders = query.getResultList();
            session.getTransaction().commit();
            return orders;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM Order o WHERE o.status = :status");
            query.setParameter("status", status);
            List<Order> orders = query.list();
            session.getTransaction().commit();
            return orders;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Order> getOrdersBySpecialist(Specialist specialist) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Query<Order> query = session.createQuery("FROM Order o WHERE o.specialist.id=:specialistId", Order.class);
            query.setParameter("specialistId", specialist.getId());
            List<Order> orders = query.getResultList();
            session.getTransaction().commit();
            return orders;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
