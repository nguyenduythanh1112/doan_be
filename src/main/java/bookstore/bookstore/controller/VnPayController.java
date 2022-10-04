package bookstore.bookstore.controller;

import bookstore.bookstore.model.OrderModel;
import bookstore.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/IPN")
@CrossOrigin(origins = "*")
public class VnPayController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> showRespond(
            @RequestParam String vnp_Amount,
            @RequestParam String vnp_BankCode,
            @RequestParam String vnp_BankTranNo,
            @RequestParam String vnp_CardType,
            @RequestParam String vnp_OrderInfo,
            @RequestParam String vnp_PayDate,
            @RequestParam String vnp_ResponseCode,
            @RequestParam String vnp_TmnCode,
            @RequestParam String vnp_TransactionNo,
            @RequestParam String vnp_TransactionStatus,
            @RequestParam String vnp_TxnRef,
            @RequestParam String vnp_SecureHash) {


        if (vnp_TransactionStatus.equals("00")) {
            OrderModel orderModel = orderService.findByIdentifyId(vnp_TxnRef);
            orderModel.setStatus("yes");
            orderService.update(orderModel);
            return ResponseEntity.ok("Success");
        }
        return (ResponseEntity<?>) ResponseEntity.badRequest().body("Errror");
    }
}
