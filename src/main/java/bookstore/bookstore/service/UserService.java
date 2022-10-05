package bookstore.bookstore.service;

import bookstore.bookstore.model.CartModel;
import bookstore.bookstore.model.User;
import bookstore.bookstore.repository.CartRepository;
import bookstore.bookstore.repository.UserRepository;
import bookstore.bookstore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User save(User user) throws Exception{
        User userUsername=userRepository.findByUsername(user.getUsername());
        if(userUsername!=null) throw new Exception("User existed");
        User userEmail=userRepository.findByEmail(user.getEmail());
        if(userEmail!=null) throw new Exception("Email existed");
        user.setRole("user");
        CartModel cartModel=new CartModel();
        user.setCartModel(cartModel);
        return userRepository.save(user);
    }

    public String login(User user) throws Exception{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return jwtUtil.generateToken((User) loadUserByUsername(user.getUsername()));
    }
}
