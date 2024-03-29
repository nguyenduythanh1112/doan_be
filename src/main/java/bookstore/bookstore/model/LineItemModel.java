package bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LineItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;

    @ManyToOne
    @JoinColumn(name="cart_model_id")
    @JsonIgnore
    private CartModel cartModel;

    @ManyToOne
    @JoinColumn(name="book_item_model_id")
    private BookItemModel bookItemModel;

    @ManyToOne
    @JoinColumn(name="order_model_id")
    @JsonIgnore
    private OrderModel orderModel;

}
