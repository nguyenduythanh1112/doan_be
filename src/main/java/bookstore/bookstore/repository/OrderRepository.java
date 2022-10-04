package bookstore.bookstore.repository;

import bookstore.bookstore.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel,Integer> {
    public OrderModel findByIdentifyId(String identifyId);
}
