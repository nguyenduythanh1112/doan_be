package bookstore.bookstore.service;

import bookstore.bookstore.model.CartModel;
import bookstore.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

}
