package coffeeApp;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderPageRepository extends CrudRepository<OrderPage, Long> {
    List<OrderPage> findByOrderId(Long orderId);
}