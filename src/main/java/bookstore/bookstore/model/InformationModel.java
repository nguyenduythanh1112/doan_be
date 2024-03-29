package bookstore.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InformationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String city;
    private String town;
    private String ward;
    private String detailAddress;
    private String phoneNumber;
    private String name;

    @OneToOne(mappedBy = "informationModel",cascade = CascadeType.ALL)
    private User user;

    public String toString() {
        return city+"-"+town+"-"+ward+"-"+detailAddress+"-"+phoneNumber+"-"+name;
    }
}
