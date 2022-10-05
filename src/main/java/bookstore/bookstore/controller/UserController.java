package bookstore.bookstore.controller;

import bookstore.bookstore.model.User;
import bookstore.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(User user){
        try {return ResponseEntity.ok(userService.save(user));}
        catch (Exception exception){return ResponseEntity.badRequest().body(exception.getMessage());}
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(User user){
        try {return ResponseEntity.ok(userService.login(user));}
        catch (Exception exception){return ResponseEntity.badRequest().body(exception.getMessage());}
    }
}
