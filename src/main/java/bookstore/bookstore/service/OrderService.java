package bookstore.bookstore.service;

import bookstore.bookstore.model.CartModel;
import bookstore.bookstore.model.OrderModel;
import bookstore.bookstore.model.PaymentModel;
import bookstore.bookstore.model.ShipmentModel;
import bookstore.bookstore.repository.CartRepository;
import bookstore.bookstore.repository.OrderRepository;
import bookstore.bookstore.repository.PaymentRepository;
import bookstore.bookstore.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private CartRepository cartRepository;

    public OrderModel save(OrderModel orderModel, int paymentId,String username,int shipmentId) throws Exception{
        PaymentModel paymentModel=paymentRepository.findById(paymentId).get();
        if(paymentModel==null) throw new Exception("Payment not exist");
        ShipmentModel shipmentModel=shipmentRepository.findById(shipmentId).get();
        if(shipmentModel==null) throw new Exception("Shipment not exist");
        CartModel cartModel=cartRepository.findByUsername(username);
        if(cartModel==null)throw new Exception("Cart not exist");
        cartModel.setLineItemModels(List.copyOf(cartModel.getLineItemModels()));
        orderModel.setPaymentModel(paymentModel);
        orderModel.setShipmentModel(shipmentModel);
        orderModel.setCartModel(cartModel);
        if(paymentModel.getType().equals("offline")) orderModel.setStatus("yes");
        else orderModel.setStatus("pending");
        return orderRepository.save(orderModel);
    }

    public OrderModel findAll(OrderModel orderModel){
        return orderRepository.save(orderModel);
    }

}
