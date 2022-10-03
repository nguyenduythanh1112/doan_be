package bookstore.bookstore.service;

import bookstore.bookstore.model.ShipmentModel;
import bookstore.bookstore.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    public ShipmentModel save(ShipmentModel shipmentModel){
        return shipmentRepository.save(shipmentModel);
    }

    public List<ShipmentModel> findAll(){
        return shipmentRepository.findAll();
    }

    public ShipmentModel shipmentModel(int id){
        return shipmentRepository.findById(id).get();
    }
}
