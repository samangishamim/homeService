package service.orderService;

import base.service.BaseService;
import model.Address;
import model.Customer;
import model.Order;
import model.Specialist;
import myEnum.OrderStatus;

import java.util.List;

public interface OrderService extends BaseService<Order,Long> {
    List<Order> getAllOrders();

    Order addOrder(Order order);

    void updateOrder(Long orderId,OrderStatus status);

    void deleteOrder(Long id);
    Order updateOrderStatus(Long orderId, OrderStatus status);

    List<Order> getOrdersByCustomer(Customer customer);

    void saveAnOrder(Order order, Address address, Long customerId, Long subserviceId);
    List<Order> getOrdersByStatus(OrderStatus status);
    List<Order> getOrdersBySpecialist(Specialist specialistId);
}
