package bookstore.bookstore.repository;

import bookstore.bookstore.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel,Integer> {
    public OrderModel findByIdentifyId(String identifyId);

    @Query("select orderModel from OrderModel orderModel where orderModel.cartModel.user.username = ?1")
    public List<OrderModel> findByUsername(String username);
}
