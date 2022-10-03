package bookstore.bookstore.service;

import bookstore.bookstore.model.PaymentModel;
import bookstore.bookstore.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentModel> findAll(){
        return paymentRepository.findAll();
    }
}
