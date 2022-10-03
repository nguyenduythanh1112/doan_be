package bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "payment_model_id")
    private PaymentModel paymentModel;

    @ManyToOne
    @JoinColumn(name = "shipment_model_id")
    private ShipmentModel shipmentModel;

    @ManyToOne
    @JoinColumn(name = "cart_model_id")
    private CartModel cartModel;

    @OneToMany(mappedBy = "orderModel")
    private List<LineItemModel> lineItemModels;
}
