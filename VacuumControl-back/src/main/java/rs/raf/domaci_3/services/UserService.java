package rs.raf.domaci_3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.raf.domaci_3.model.User;
import rs.raf.domaci_3.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class
UserService implements UserDetailsService, IService<User, Long> {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User myUser = this.userRepository.findByEmail(email);
        if(myUser == null) {
            throw new UsernameNotFoundException("Email "+email+" not found");
        }

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), new ArrayList<>());
    }

    @Override
    public User save(User user) throws BadCredentialsException {
        List<User> allUsers = (List<User>)userRepository.findAll();
        for(User u : allUsers){
            if(u.getEmail().equals(user.getEmail()) && (!u.getUserId().equals(user.getUserId()))){
                System.out.println("Email " +user.getEmail()+ " has already been used");
                throw new BadCredentialsException("Email " +user.getEmail()+ " has already been used");
            }
        }
        return this.userRepository.save(user);
    }

    @Override
    public List<User> findAll(){
        return (List<User>)this.userRepository.findAll();
    }

    @Override
    public void deleteById(Long id){this.userRepository.deleteById(id);}

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email);
    }


}
