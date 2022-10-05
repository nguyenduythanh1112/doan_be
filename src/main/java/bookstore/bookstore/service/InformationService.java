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

        User user=userRepository.findByUsername(username);
        if(user==null) throw new Exception("User not exist");

        InformationModel ifm=informationRepository.findByUsername(username);
        if(ifm==null) {
            ifm=new InformationModel();
            ifm.setUser(user);
            ifm.setCity(informationModel.getCity());
            ifm.setTown(informationModel.getTown());
            ifm.setWard(informationModel.getWard());
            ifm.setDetailAddress(informationModel.getDetailAddress());
            ifm.setName(informationModel.getName());
            ifm.setPhoneNumber(informationModel.getPhoneNumber());
        }
        else {
            ifm.setCity(informationModel.getCity());
            ifm.setTown(informationModel.getTown());
            ifm.setWard(informationModel.getWard());
            ifm.setDetailAddress(informationModel.getDetailAddress());
            ifm.setName(informationModel.getName());
            ifm.setPhoneNumber(informationModel.getPhoneNumber());
        }

        ifm.setUser(user);
        informationRepository.save(ifm);
//        user.setInformationModel(informationModel);
//        userRepository.save(user);

        return informationModel;
    }

    public InformationModel findByUsername(String username) throws Exception{
        InformationModel informationModel=informationRepository.findByUsername(username);
        if(informationModel==null) throw new Exception("Not found");
        return informationModel;
    }

}
