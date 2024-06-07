package repository.orderRepository;

import base.repository.BaseRepository;
import model.Customer;
import model.Order;
import model.Specialist;
import myEnum.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository  extends BaseRepository<Order, Long> {

    List<Order> getAllOrders();
    Order addOrder(Order order);
    List<Order> findByCustomer(Customer customer);
    List<Order> getOrdersByStatus(OrderStatus status);
    List<Order> getOrdersBySpecialist(Specialist specialist);
}
