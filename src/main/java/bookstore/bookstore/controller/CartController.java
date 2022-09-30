package bookstore.bookstore.controller;

import bookstore.bookstore.model.CartModel;
import bookstore.bookstore.model.LineItemModel;
import bookstore.bookstore.service.CartService;
import bookstore.bookstore.service.LineItemService;
import bookstore.bookstore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private LineItemService lineItemService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/item")
    public ResponseEntity<?> findByUsername(HttpServletRequest request){
        try {
            String username= jwtUtil.getUsernameFromToken(request.getHeader("Authorization"));
            List<LineItemModel> lineItemModels = lineItemService.findByUsername(username);
            return ResponseEntity.ok(lineItemModels);
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body("User not have cart");
        }
    }
}
