package bookstore.bookstore.controller;

import bookstore.bookstore.model.BookModel;
import bookstore.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@ModelAttribute BookModel bookModel) {
        BookModel bm = bookService.save(bookModel);
        if (bm == null) return ResponseEntity.badRequest().body("Error");
        return ResponseEntity.ok(bm);
    }
    @PutMapping
    public ResponseEntity<?> update(@ModelAttribute BookModel bookModel){
        BookModel bm=bookService.save(bookModel);
        if(bm==null) return ResponseEntity.badRequest().body("Error");
        return ResponseEntity.ok(bm);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteById(@RequestParam int id){
        try{bookService.deleteById(id);return ResponseEntity.ok("Success");}
        catch (Exception exception){return ResponseEntity.badRequest().body("Not existed");}
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        BookModel bookModel=bookService.findById(id);
        if(bookModel==null) return ResponseEntity.badRequest().body("Not exist");
        return ResponseEntity.ok(bookModel);
    }

    @GetMapping("/postedbook")
    public ResponseEntity<?> findById(){
        return  ResponseEntity.ok(bookService.findPostedBook());
    }

}
