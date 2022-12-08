package bookstore.bookstore.controller;

import bookstore.bookstore.model.BookModel;
import bookstore.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/book")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @ModelAttribute BookModel bookModel) {
        BookModel bm = bookService.save(bookModel);
        return ResponseEntity.ok(bm);
    }
    @PutMapping
    public ResponseEntity<?> update(@Valid @ModelAttribute BookModel bookModel){
        BookModel bm=bookService.save(bookModel);
        return ResponseEntity.ok(bm);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestParam int id){
        bookService.deleteById(id);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        try {return ResponseEntity.ok(bookService.findById(id));}
        catch (Exception exception){return ResponseEntity.badRequest().body("Not exist");}
    }

    @GetMapping("/postedbook")
    public ResponseEntity<?> findPostedBook(){
        return  ResponseEntity.ok(bookService.findPostedBook());
    }

    @GetMapping("/notpostedbook")
    public ResponseEntity<?> findNotPostedBook(){
        return  ResponseEntity.ok(bookService.findNotPostedBook());
    }
}
