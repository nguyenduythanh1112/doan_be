package bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "title not null")
    @NotBlank(message = "title not blank")
    private String title;

    @NotNull(message = "summary not null")
    @NotBlank(message = "summary not blank")
    private String summary;

    @NotNull
    @Min(value = 1)
    private int numberOfPage;

    @NotNull(message = "language not null")
    @NotBlank(message = "language not blank")
    private String language;

    @NotNull(message = "image not null")
    @NotBlank(message = "image not blank")
    private String image;

    @NotNull(message = "file not null")
    @NotBlank(message = "file not blank")
    private String file;

    @NotNull(message = "description not null")
    @NotBlank(message = "description not blank")
    private String description;

    @Min(value = 0)
    private long importedPrice;
    @Min(value = 0)
    private int importedQuantity;
    @Min(value = 0)
    private int exportedQuantity;

    @NotNull(message = "publisher not null")
    @NotBlank(message = "publisher not blank")
    private String publisher;

    @NotNull(message = "author not null")
    @NotBlank(message = "author not blank")
    private String author;

    @NotNull(message = "category not null")
    @NotBlank(message = "category not blank")
    private String category;

    @NotNull
    @Min(value = 0)
    private long height;

    @NotNull
    @Min(value = 0)
    private long weight;

    @NotNull
    @Min(value = 0)
    private long longs;

    @OneToOne(mappedBy = "bookModel",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private BookItemModel bookItemModel;

    public int getRemainQuantity(){
        return importedQuantity-exportedQuantity;
    }
}
