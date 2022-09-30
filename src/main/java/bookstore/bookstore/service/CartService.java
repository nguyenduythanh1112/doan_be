package bookstore.bookstore.service;

import bookstore.bookstore.model.CartModel;
import bookstore.bookstore.model.LineItemModel;
import bookstore.bookstore.repository.CartRepository;
import bookstore.bookstore.repository.LineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private LineItemRepository lineItemRepository;


    public CartModel save(CartModel cartModel){
        return cartRepository.save(cartModel);
    }

    public List<CartModel> findAll(){
        return cartRepository.findAll();
    }


    public CartModel findByUsername(String username){
        return cartRepository.findByUsername(username);
    }
}
