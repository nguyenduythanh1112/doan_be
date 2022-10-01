package bookstore.bookstore.controller;

import bookstore.bookstore.model.LineItemModel;
import bookstore.bookstore.repository.LineItemRepository;
import bookstore.bookstore.service.LineItemService;
import bookstore.bookstore.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/lineitem")
@CrossOrigin(origins = "*")
public class LineItemController {
    @Autowired
    private LineItemService lineItemService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> save(HttpServletRequest request, @RequestParam int bookItemId){
        String username= jwtUtil.getUsernameFromToken(request.getHeader("Authorization"));
        try {
            LineItemModel lineItemModel = lineItemService.save(username,bookItemId);
            return ResponseEntity.ok(lineItemModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> add(HttpServletRequest request, @RequestParam int bookItemId){
        String username= jwtUtil.getUsernameFromToken(request.getHeader("Authorization"));
        try {
            LineItemModel lineItemModel = lineItemService.add(username,bookItemId);
            return ResponseEntity.ok(lineItemModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/subtract")
    public ResponseEntity<?> subtract(HttpServletRequest request, @RequestParam int bookItemId){
        String username= jwtUtil.getUsernameFromToken(request.getHeader("Authorization"));
        try {
            LineItemModel lineItemModel = lineItemService.subtract(username,bookItemId);
            return ResponseEntity.ok(lineItemModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> update(HttpServletRequest request, @RequestParam int bookItemId){
        String username= jwtUtil.getUsernameFromToken(request.getHeader("Authorization"));
        try {
            LineItemModel lineItemModel = lineItemService.save(username,bookItemId);
            return ResponseEntity.ok(lineItemModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findByUsername(HttpServletRequest request,@PathVariable String username){
        String usernameFromToken= jwtUtil.getUsernameFromToken(request.getHeader("Authorization"));
        try {
            List<LineItemModel> lineItemModels=lineItemService.findByUsername(usernameFromToken);
            return ResponseEntity.ok(lineItemModels);
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
