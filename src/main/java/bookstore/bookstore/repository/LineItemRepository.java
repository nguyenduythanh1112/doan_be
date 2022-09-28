package bookstore.bookstore.repository;

import bookstore.bookstore.model.LineItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LineItemRepository extends JpaRepository<LineItemModel,Integer> {
    @Query(value = "select lineItemModel from LineItemModel lineItemModel " +
            "where lineItemModel.cartModel.id = ?1 " +
            "and lineItemModel.bookItemModel.id = ?2")
    public LineItemModel findByCartIdAndBookItemId(int cartId,int bookItemId);

    @Query(value = "select lineItemModel from LineItemModel lineItemModel where " +
            "lineItemModel.cartModel.id = ?1 ")
    public List<LineItemModel> findByCartId(int cartId);

    @Query(value = "select lineItemModel from LineItemModel lineItemModel where " +
            "lineItemModel.cartModel.user.username = ?1 ")
    public List<LineItemModel> findByUsername(String username);
}
