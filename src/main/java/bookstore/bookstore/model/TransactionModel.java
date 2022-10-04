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
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bankCode;
    private String bankTransactionNo;
    private String identifyCode;
    private String status;
    private String date;

    @ManyToOne
    @JoinColumn(name = "order_model_id")
    @JsonIgnore
    private OrderModel orderModel;

    @ManyToOne
    @JoinColumn(name = "payment_model_id")
    @JsonIgnore
    private PaymentModel paymentModel;


}
