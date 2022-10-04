package bookstore.bookstore.service;

import bookstore.bookstore.config.VnPayConfig;
import bookstore.bookstore.model.OrderModel;
import bookstore.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnPayService {

    @Autowired
    private OrderRepository orderRepository;


    public String makeUrlToPay(OrderModel orderModel, HttpServletRequest request) throws Exception{
        if(orderModel==null) throw new Exception("Oder not exist");
        if(orderModel.getStatus().equals("YES")) throw new Exception("Oder was payed");
        else {
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", "2.1.0");
            vnp_Params.put("vnp_Command", "pay");
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_OrderType", "VN");
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_TmnCode", VnPayConfig.vnp_TmnCode);
            vnp_Params.put("vnp_TxnRef", VnPayConfig.getRandomNumber(8));
            vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_Returnurl);
            vnp_Params.put("vnp_OrderInfo",VnPayConfig.getRandomNumber(8)+orderModel.getDate());
            vnp_Params.put("vnp_IpAddr", VnPayConfig.getIpAddress(request));
            vnp_Params.put("vnp_CreateDate", orderModel.getDate());
            vnp_Params.put("vnp_Amount", orderModel.getTotalPrice()+"");

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = simpleDateFormat.parse(orderModel.getDate());
            date.setTime(date.getTime()+24*60*60*1000);
            String vnp_ExpireDate = simpleDateFormat.format(date.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);


            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;
            return paymentUrl;
        }
    }
}
