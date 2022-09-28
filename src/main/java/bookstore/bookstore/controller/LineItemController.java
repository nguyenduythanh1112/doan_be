package bookstore.bookstore.controller;

import bookstore.bookstore.model.LineItemModel;
import bookstore.bookstore.repository.LineItemRepository;
import bookstore.bookstore.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lineitem")
public class LineItemController {
    @Autowired
    private LineItemService lineItemService;

    @PostMapping
    public ResponseEntity<?> create(@RequestParam int bookItemId, @RequestParam String username){
        try {
            LineItemModel lineItemModel = lineItemService.save(username,bookItemId);
            return ResponseEntity.ok(lineItemModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> update(@RequestParam int bookItemId, @RequestParam String username){
        try {
            LineItemModel lineItemModel = lineItemService.save(username,bookItemId);
            return ResponseEntity.ok(lineItemModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username){
        try {
            List<LineItemModel> lineItemModels=lineItemService.findByUsername(username);
            return ResponseEntity.ok(lineItemModels);
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
