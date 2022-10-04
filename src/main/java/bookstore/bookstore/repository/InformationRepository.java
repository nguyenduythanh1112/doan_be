package bookstore.bookstore.repository;

import bookstore.bookstore.model.InformationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<InformationModel,Integer> {
    @Query("select information from InformationModel information where information.user.username=?1")
    public InformationModel findByUsername(String username);
}
