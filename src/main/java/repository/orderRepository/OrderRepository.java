package repository.orderRepository;

import base.repository.BaseRepository;
import model.Order;
import myEnum.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository  extends BaseRepository<Order, Long> {
    Optional<List<Order>> findByCustomer(Long customerId);

    Optional<List<Order>> findBySubService(Long subServiceId);

    Optional<List<Order>> findByStatus(OrderStatus status);
}
