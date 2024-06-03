package service.orderService;

import base.service.BaseServiceImpl;
import model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.orderRepository.OrderRepository;

import java.util.Collections;
import java.util.List;

public class OrderServiceImpl extends BaseServiceImpl<Order, Long, OrderRepository> implements OrderService {
    public OrderServiceImpl(OrderRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Order> getAllOrders() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Order> orders = repository.getAllOrders();
            session.getTransaction().commit();
            return orders;
        } catch (Exception e) {
            return Collections.emptyList();
        }    }

    @Override
    public Order addOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Order addedOrder = repository.addOrder(order);
            session.getTransaction().commit();
            return addedOrder;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Order updateOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Order updatedOrder = repository.updateOrder(order);
            session.getTransaction().commit();
            return updatedOrder;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteOrder(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order!= null) {
                session.delete(order);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}