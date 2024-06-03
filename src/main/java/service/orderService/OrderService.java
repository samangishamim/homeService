package service.orderService;

import base.service.BaseService;
import model.Order;

import java.util.List;

public interface OrderService extends BaseService<Order,Long> {
    List<Order> getAllOrders();

    Order addOrder(Order order);

    Order updateOrder(Order order);

    void deleteOrder(Long id);
}