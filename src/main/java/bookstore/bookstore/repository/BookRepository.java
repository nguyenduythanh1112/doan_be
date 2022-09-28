package bookstore.bookstore.repository;

import bookstore.bookstore.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<BookModel, Integer> {
    @Query("SELECT bookModel FROM BookModel bookModel WHERE bookModel.bookItemModel is null")
    public List<BookModel> findPostedBook();

    @Query("SELECT bookModel FROM BookModel bookModel WHERE bookModel.bookItemModel is not null")
    public List<BookModel> findNotPostedBook();
}
