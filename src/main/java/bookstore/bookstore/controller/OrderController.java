package bookstore.bookstore.controller;

import bookstore.bookstore.model.InformationModel;
import bookstore.bookstore.model.OrderModel;
import bookstore.bookstore.service.InformationService;
import bookstore.bookstore.util.JwtUtil;
import bookstore.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private InformationService informationService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping
    public ResponseEntity<?> save(
            HttpServletRequest request,
            int paymentId,
            int shipmentId,
            @ModelAttribute InformationModel informationModel
    ){
        try {

            String username= jwtUtil.getUsernameFromToken(request.getHeader("Authorization"));
            InformationModel ifm = informationService.save(informationModel,username);
            return ResponseEntity.ok(orderService.save(paymentId,username,shipmentId,ifm,request));
        }
        catch (Exception exception){
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> findByUsername(HttpServletRequest request){
        try {
            String username= jwtUtil.getUsernameFromToken(request.getHeader("Authorization"));
            if(username.toLowerCase().equals("admin")) return ResponseEntity.ok(orderService.findAll());
            return ResponseEntity.ok(orderService.findByUsername(username));
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
