package bookstore.bookstore.service;

import bookstore.bookstore.model.BookItemModel;
import bookstore.bookstore.model.BookModel;
import bookstore.bookstore.repository.BookItemRepository;
import bookstore.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class BookItemService {

    @Autowired
    private BookItemRepository bookItemRepository;

    @Autowired
    private BookRepository bookRepository;
    public BookItemModel save(BookItemModel bookItemModel, int idBookModel) throws Exception{
        Optional bookModelOptional=bookRepository.findById(idBookModel);
        if(bookModelOptional.isPresent()){
            System.out.println("YES 1");
            BookModel bookModel= (BookModel) bookModelOptional.get();
            if(bookModel.getBookItemModel()==null||bookModel.getBookItemModel().getId()==bookItemModel.getId()) {
                bookItemModel.setBookModel(bookModel);
                bookItemRepository.save(bookItemModel);
                System.out.println("bookItemModel "+bookItemModel);
                return bookItemModel;
            }
            else throw new Exception("Error");
        }
        else throw new Exception("Error");
    }
    public BookItemModel update(BookItemModel bookItemModel, int idBookModel) throws Exception{
        if(idBookModel<=0) return null;
        BookModel bm=bookRepository.findById(idBookModel).get();
        BookItemModel bim=bookItemRepository.findById(bookItemModel.getId()).get();
        if(bm!=null&&bim!=null&&bim.getBookModel().getId()==bm.getId()){
            return bookItemRepository.save(bookItemModel);
        }
        return null;
    }


    public void delete(int id){

        try{
            BookItemModel bookItemModel=findById(id);
            bookItemModel.setBookModel(null);
            bookItemRepository.save(bookItemModel);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }

    }
    public List<BookItemModel> findAll(){
        return bookItemRepository.findAll()
                .stream()
                .filter(bookItemModel -> bookItemModel.getBookModel()!=null)
                .collect(Collectors.toList());
    }

    public BookItemModel findById(int id){
        try{
            return bookItemRepository.findById(id).get();
        }
        catch (Exception exception){
            return null;
        }
    }

    public List<BookItemModel> findByStatus(String status){
        return bookItemRepository
                .findByStatus(status)
                .stream()
                .filter(bookItemModel -> bookItemModel.getBookModel()!=null)
                .collect(Collectors.toList());
    }
}
