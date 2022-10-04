package bookstore.bookstore.service;

import bookstore.bookstore.model.InformationModel;
import bookstore.bookstore.model.User;
import bookstore.bookstore.repository.InformationRepository;
import bookstore.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private UserRepository userRepository;

    public InformationModel save(InformationModel informationModel, String username) throws Exception{

//        InformationModel ifm=informationRepository.findByUsername(username);
//        if(ifm==null) ifm=informationModel;
//        else informationModel.setId(ifm.getId());
//        informationRepository.save(informationModel);
//
//        User user=userRepository.findByUsername(username);
//        user.setInformationModel(informationModel);
//        userRepository.save(user);

        return informationModel;
    }
}
