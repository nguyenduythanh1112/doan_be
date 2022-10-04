package bookstore.bookstore.service;

import bookstore.bookstore.config.VnPayConfig;
import bookstore.bookstore.model.OrderModel;
import bookstore.bookstore.model.TransactionModel;
import bookstore.bookstore.repository.OrderRepository;
import bookstore.bookstore.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;





@Service
public class VnPayService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionRepository transactionRepository;



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
            String identifyId=VnPayConfig.getRandomNumber(8);
            vnp_Params.put("vnp_TxnRef", identifyId);
            orderModel.setIdentifyId(identifyId);
            vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_Returnurl);
            vnp_Params.put("vnp_OrderInfo",VnPayConfig.getRandomNumber(8)+orderModel.getDate());
            vnp_Params.put("vnp_IpAddr", VnPayConfig.getIpAddress(request));
            vnp_Params.put("vnp_CreateDate", orderModel.getDate());
            vnp_Params.put("vnp_Amount", orderModel.getTotalPrice()+"00");

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

    public IPNResponse IPN(HttpServletRequest request)  {
        try {
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldKey = java.net.URLEncoder.encode(params.nextElement().toString(), StandardCharsets.US_ASCII.toString());
                String fieldVal = java.net.URLEncoder.encode(request.getParameter(fieldKey), StandardCharsets.US_ASCII.toString());
                if (fieldKey != null && StringUtils.hasText(fieldVal)) fields.put(fieldKey, fieldVal);
            }
            if (fields.containsKey("vnp_SecureHash")) fields.remove("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) fields.remove("vnp_SecureHashType");
            String secureHash = VnPayConfig.hashAllFields(fields);
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (!secureHash.equals(vnp_SecureHash))
                return IPNResponse.builder().RspCode("97").Message("Invalid Checksum").build();
            String txnRef = request.getParameter("vnp_TxnRef");
            OrderModel orderModel = orderRepository.findByIdentifyId(txnRef);
            if (orderModel == null) return IPNResponse.builder().RspCode("01").Message("Order not Found").build();
            if (orderModel.getTotalPrice() * 100 != Long.parseLong(request.getParameter("vnp_Amount")))
                return IPNResponse.builder().RspCode("04").Message("Invalid Amount").build();
            if (orderModel.getStatus().equals("yes"))
                return IPNResponse.builder().RspCode("02").Message("Order already confirmed").build();
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setBankCode(request.getParameter("vnp_BankCode"));
            transactionModel.setBankTransactionNo(request.getParameter("vnp_BankTranNo"));
            transactionModel.setIdentifyCode(request.getParameter("vnp_TransactionNo"));
            transactionModel.setStatus("no");
            orderModel.setStatus("no");
            if (request.getParameter("vnp_ResponseCode").equals("00")) {
                transactionModel.setStatus("yes");
                orderModel.setStatus("yes");
            }
            transactionModel.setOrderModel(orderModel);
            transactionModel.setPaymentModel(orderModel.getPaymentModel());
            transactionRepository.save(transactionModel);
            orderRepository.save(orderModel);
            return IPNResponse.builder().RspCode("00").Message("Confirm Success").build();
        }
        catch (Exception exception){
            return IPNResponse.builder().RspCode("99").Message("Unknown error").build();
        }
    }




}
