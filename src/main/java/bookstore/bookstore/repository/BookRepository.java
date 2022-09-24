package bookstore.bookstore.repository;

import bookstore.bookstore.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookModel, Integer> {
    @Query("SELECT bookModel FROM BookModel bookModel WHERE bookModel.bookItemModel!=null")
    public List<BookModel> findPostedBook();
}
