package bookstore.bookstore.repository;

import bookstore.bookstore.model.BookItemModel;
import bookstore.bookstore.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookItemRepository extends JpaRepository<BookItemModel, Integer> {
    public List<BookItemModel> findByStatus(String status);

}
