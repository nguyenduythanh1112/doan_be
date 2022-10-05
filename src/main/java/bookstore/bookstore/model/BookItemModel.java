package bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BookItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String barcode;
    private long exportedPrice;
    private long discount;
    private String status;

    @OneToOne
    @JoinColumn(name = "book_model_id",referencedColumnName = "id")
    private BookModel bookModel;

    @JsonIgnore
    @OneToMany(mappedBy = "bookItemModel", cascade = CascadeType.ALL)
    private List<LineItemModel> lineItemModels;



}
