package bookstore.bookstore.repository;

import bookstore.bookstore.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartModel, Integer> {
    @Query(value = "select cartModel from CartModel cartModel where cartModel.user.username = ?1")
    public CartModel findByUsername(String username);
}
