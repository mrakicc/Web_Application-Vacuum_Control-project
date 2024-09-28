package rs.raf.domaci_3.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.raf.domaci_3.model.Permission;
import rs.raf.domaci_3.model.User;
import rs.raf.domaci_3.services.UserService;

import java.io.Console;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(Authentication authentication){
        if(isAuthorise(authentication, "can_read_users")){
            return userService.findAll();
        }
        System.out.println("\n\nYou are not authorise to do read function\n\n");
        throw new AccessDeniedException("You are not authorise to do read function");
    };

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user, Authentication authentication) throws BadCredentialsException {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        if(isAuthorise(authentication, "can_create_users")){
            return userService.save(user);
        }
        System.out.println("\n\nYou are not authorise to do create function\n\n");
        throw new AccessDeniedException("You are not authorise to do create function");
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody User user, Authentication authentication) throws BadCredentialsException {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        if(isAuthorise(authentication, "can_update_users")){
            return userService.save(user);
        }
        System.out.println("\n\nYou are not authorise to do update function\n\n");
        throw new AccessDeniedException("You are not authorise to do update function");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id, Authentication authentication){
        if(isAuthorise(authentication, "can_delete_users")){
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        System.out.println("\n\nYou are not authorise to do delete function\n\n");
        return ResponseEntity.status(403).build();
    }

    public boolean isAuthorise(Authentication authentication, String crudString){
        for(User u : userService.findAll()) {
            if(u.getEmail().equals(authentication.getName())) {
                for(Permission permission : u.getPermissions()){
                    if(permission.getName().equals(crudString)) {
                        return true;
                    }
                }
            }
        }
        return  false;
    }

    @CrossOrigin
    @GetMapping(value = "/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByEmail(@PathVariable("email") String email, Authentication authentication){
        return userService.findByEmail(email);
    };

}
