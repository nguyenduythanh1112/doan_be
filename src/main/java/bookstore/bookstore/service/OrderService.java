package bookstore.bookstore.service;

import bookstore.bookstore.model.*;
import bookstore.bookstore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private InformationRepository informationRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private VnPayService vnPayService;

    @Autowired
    private LineItemService lineItemService;
    @Autowired
    private UserRepository userRepository;
    public OrderModel save(
            int paymentId,
            String username,
            int shipmentId,
            InformationModel informationModel,
            HttpServletRequest request) throws Exception {

        PaymentModel paymentModel=paymentRepository.findById(paymentId).get();
        if(paymentModel==null) throw new Exception("Payment not exist");

        ShipmentModel shipmentModel=shipmentRepository.findById(shipmentId).get();
        if(shipmentModel==null) throw new Exception("Shipment not exist");

        CartModel cartModel=cartRepository.findByUsername(username);
        if(cartModel==null)throw new Exception("Cart not exist");



        OrderModel orderModel=new OrderModel();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String createDate = simpleDateFormat.format(calendar.getTime());

        long amount=0;
//        for(LineItemModel lineItemModel:cartModel.getLineItemModels()) amount+=lineItemModel.getQuantity()*lineItemModel.getBookItemModel().getExportedPrice();
//        System.out.println(amount);
//        amount=0;
        for(LineItemModel lineItemModel:lineItemService.findByUsername(username)){
            amount+=lineItemModel.getQuantity()*lineItemModel.getBookItemModel().getExportedPrice();
        }
        System.out.println(amount);

        orderModel.setAmount(amount);
        orderModel.setDate(createDate);
        orderModel.setPaymentModel(paymentModel);
        orderModel.setShipmentModel(shipmentModel);
        orderModel.setCartModel(cartModel);
        orderModel.setInformationToShip(informationModel.toString());

        if(paymentModel.getType().equals("offline")) orderModel.setStatus("yes");
        else {
            orderModel.setStatus("no");
            orderModel.setUrlToPay(vnPayService.makeUrlToPay(orderModel,request));
        }
        OrderModel om=orderRepository.save(orderModel);
        for(LineItemModel lineItemModel:cartModel.getLineItemModels()){
            lineItemModel.setOrderModel(om);
            lineItemRepository.save(lineItemModel);
        }

        System.out.println("Save order ok");
        System.out.println(om.getId());

        return om;
    }

    public List<OrderModel> findByUsername(String username){
        if(userRepository.findByUsername(username).getRole().toLowerCase().equals("admin")) return orderRepository.findAll();
        return orderRepository.findByUsername(username);
    }

    public List<OrderModel> findAll(){
        return orderRepository.findAll();
    }

    public OrderModel saveOrderById(int id, String status){
        OrderModel orderModel=orderRepository.findById(id).get();
        orderModel.setStatus(status);
        return orderRepository.save(orderModel);
    }

    public OrderModel findByIdentifyId(String identifyId){
        return orderRepository.findByIdentifyId(identifyId);
    }

    public OrderModel update(OrderModel orderModel){
        return orderRepository.save(orderModel);
    }
}
