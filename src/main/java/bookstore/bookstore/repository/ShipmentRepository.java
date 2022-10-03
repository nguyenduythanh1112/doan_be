package bookstore.bookstore.repository;

import bookstore.bookstore.model.ShipmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentModel, Integer> {

}
