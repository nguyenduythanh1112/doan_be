package bookstore.bookstore.repository;

import bookstore.bookstore.model.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionModel,Integer> {

}
