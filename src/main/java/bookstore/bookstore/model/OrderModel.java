package bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String date;
    private String status;
    private String informationToShip;
    private String urlToPay;
    private long amount;

    public long getTotalPrice(){
        return amount+paymentModel.getAmount()+shipmentModel.getAmount();
    }

    @ManyToOne
    @JoinColumn(name = "payment_model_id")
    private PaymentModel paymentModel;

    @ManyToOne
    @JoinColumn(name = "shipment_model_id")
    private ShipmentModel shipmentModel;

    @ManyToOne
    @JoinColumn(name = "cart_model_id")
    @JsonIgnore
    private CartModel cartModel;

    @OneToMany(mappedBy = "orderModel")
    private List<LineItemModel> lineItemModels;
}
