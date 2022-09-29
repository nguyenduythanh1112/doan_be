package bookstore.bookstore.service;

import bookstore.bookstore.model.BookModel;
import bookstore.bookstore.repository.BookItemRepository;
import bookstore.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookItemRepository bookItemRepository;
    private boolean validate(BookModel bookModel){
        return bookModel.getTitle()!=null&&!bookModel.getTitle().trim().equals("")&&
            bookModel.getSummary()!=null&&!bookModel.getSummary().trim().equals("")&&
            bookModel.getNumberOfPage()>=0&&
            bookModel.getLanguage()!=null&&!bookModel.getLanguage().trim().equals("")&&
            bookModel.getImage()!=null&&!bookModel.getImage().trim().equals("")&&
            bookModel.getFile()!=null&&!bookModel.getLanguage().trim().equals("")&&
            bookModel.getDescription()!=null&&!bookModel.getDescription().trim().equals("")&&
            bookModel.getImportedPrice()>=0&&
            bookModel.getImportedQuantity()>=0&&
            bookModel.getExportedQuantity()>=0&&
            bookModel.getPublisher()!=null&&!bookModel.getPublisher().trim().equals("")&&
            bookModel.getAuthor()!=null&&!bookModel.getAuthor().trim().equals("")&&
            bookModel.getCategory()!=null&&!bookModel.getCategory().trim().equals("");
    }
    public BookModel save(BookModel bookModel){
        if(validate(bookModel)) return bookRepository.save(bookModel);
        return null;
    }
    @Transactional
    public void deleteById(int id){
        try{
            BookModel bookModel=findById(id);
            if(bookModel!=null&&bookModel.getBookItemModel()!=null){
                bookItemRepository.deleteById(bookModel.getBookItemModel().getId());
            }
            bookRepository.deleteById(id);
        }
        catch (Exception exception){
            System.out.println("Not Existed");
        }

    }
    public  BookModel findById(int id){
        return bookRepository.findById(id).get();
    }
    public List<BookModel> findAll(){
        return bookRepository.findAll();
    }

    public List<BookModel> findPostedBook(){
        List<BookModel> bookModels=findAll();
        List<BookModel> result=new ArrayList<>();
        for(BookModel bookModel:bookModels){
            if(bookModel.getBookItemModel()!=null)result.add(bookModel);
        }
        return result;
    }
    public List<BookModel> findNotPostedBook(){
        List<BookModel> bookModels=findAll();
        List<BookModel> result=new ArrayList<>();
        for(BookModel bookModel:bookModels){
            if(bookModel.getBookItemModel()==null)result.add(bookModel);
        }
        return result;
    }

}
