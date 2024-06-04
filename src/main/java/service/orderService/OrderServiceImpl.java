package service.orderService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.Order;
import myEnum.OrderStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.orderRepository.OrderRepository;

import java.util.Collections;
import java.util.List;

@Slf4j
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
    @Override

    public Order addOrder(Order order) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            if (order.getProposedPrice() <= 0 || order.getDescription() == null || order.getDescription().trim().isEmpty() || order.getWorkDate() == null ) {
                throw new Exception("Invalid order details");
            }
            Order addedOrder = repository.addOrder(order);
            session.getTransaction().commit();
            return addedOrder;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Order order = session.get(Order.class, orderId);
            if (order != null) {
                order.setStatus(status);
                session.save(order);
            }
            session.getTransaction().commit();
            return order;
        } catch (Exception e) {
            return null;
        }
    }
}
