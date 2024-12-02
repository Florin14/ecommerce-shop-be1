package ro.ubb.mp.dao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.mp.dao.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    @Query("SELECT o FROM orders o WHERE o.user.id = :userId AND o.orderState = 'PENDING'")
    List<Order> findPendingOrdersByUserId(@Param("userId") Long userId);
}
