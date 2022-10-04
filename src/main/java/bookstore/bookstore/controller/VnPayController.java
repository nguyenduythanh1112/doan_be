package bookstore.bookstore.controller;

import bookstore.bookstore.model.OrderModel;
import bookstore.bookstore.service.OrderService;
import bookstore.bookstore.service.VnPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/IPN")
@CrossOrigin(origins = "*")
public class VnPayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private VnPayService vnPayService;
    @GetMapping
    public ResponseEntity<?> showRespond(HttpServletRequest request) {
        return ResponseEntity.ok(vnPayService.IPN(request));
    }
}
