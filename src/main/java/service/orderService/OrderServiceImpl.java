package service.orderService;

import base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import model.*;
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
        }
    }

    @Override
    public void updateOrder(Long orderId, OrderStatus status) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Order order = session.get(Order.class, orderId);
            order.setStatus(status);
            repository.saveOrUpdate(order);
            session.getTransaction().commit();
            log.info("the proposal for order with id: {} is accepted",orderId);
        }
    }

    @Override
    public void deleteOrder(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
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
            if (order.getProposedPrice() <= 0 || order.getDescription() == null || order.getDescription().trim().isEmpty() || order.getWorkDate() == null) {
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

    @Override
    public List<Order> getOrdersByCustomer(Customer customer) {
        return repository.findByCustomer(customer);
    }

    @Override
    public void saveAnOrder(Order order, Address address, Long customerId, Long subserviceId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Customer customer = session.get(Customer.class, customerId);
            SubService subService = session.get(SubService.class, subserviceId);

            order.setAddress(address);
            order.setCustomer(customer);
            order.setSubService(subService);
            repository.saveOrUpdate(order);

            session.getTransaction().commit();
            log.info("order is register");
        }
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return repository.getOrdersByStatus(status);
    }

    @Override
    public List<Order> getOrdersBySpecialist(Specialist specialistId) {
        return repository.getOrdersBySpecialist(specialistId);
    }
}
