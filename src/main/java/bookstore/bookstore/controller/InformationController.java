package bookstore.bookstore.controller;

import bookstore.bookstore.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bookstore.bookstore.util.JwtUtil;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/information")
@CrossOrigin(origins = "*")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> findByUserName(HttpServletRequest request){
        try {
            String username= jwtUtil.getUsernameFromToken(request.getHeader("Authorization"));
            return ResponseEntity.ok(informationService.findByUsername(username));

        } catch (Exception exception){return ResponseEntity.badRequest().body(exception.getMessage());}
    }
}
