package bookstore.bookstore.controller;

import bookstore.bookstore.model.BookItemModel;
import bookstore.bookstore.service.BookItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookitem")
@CrossOrigin(origins = "*")
public class BookItemController {
    @Autowired
    private BookItemService bookItemService;
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(bookItemService.findAll());
    }
    @PostMapping
    public ResponseEntity<?> save(@ModelAttribute BookItemModel bookItemModel, @RequestParam int bookId){
        try {
            if(bookId<=0) return ResponseEntity.badRequest().body(">0");
            BookItemModel bim=bookItemService.save(bookItemModel,bookId);
            if(bim==null) return ResponseEntity.badRequest().body("1-1");
            return ResponseEntity.ok(bim);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Exist");
        }
    }
    @PutMapping
    public ResponseEntity<?> update(@ModelAttribute BookItemModel bookItemModel, @RequestParam int bookId){
        try {
            if(bookId<=0) return ResponseEntity.badRequest().body(">0");
            BookItemModel bim=bookItemService.update(bookItemModel,bookId);
            if(bim==null) return ResponseEntity.badRequest().body("1-1");
            return ResponseEntity.ok(bim);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Exist");
        }
    }
    @DeleteMapping
    public ResponseEntity delete(@RequestParam int id){
        bookItemService.delete(id);
        return ResponseEntity.ok("Success");
    }
    @GetMapping("/public")
    public ResponseEntity<?> bookItemPublic(){
        return ResponseEntity.ok(bookItemService.findByStatus("yes"));
    }
    @GetMapping("/private")
    public ResponseEntity<?> bookItemPrivate(){
        return ResponseEntity.ok(bookItemService.findByStatus("no"));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        BookItemModel bookItemModel=bookItemService.findById(id);
        if(bookItemModel==null) return ResponseEntity.badRequest().body("Not existed");
        return ResponseEntity.ok(bookItemModel);
    }
}
