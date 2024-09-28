package rs.raf.domaci_3.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.raf.domaci_3.model.ErrorMassage;
import rs.raf.domaci_3.model.User;
import rs.raf.domaci_3.services.ErrorService;
import rs.raf.domaci_3.services.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/errors")
public class ErrorController {

    private final ErrorService errorService;
    private final UserService userService;

    public ErrorController(ErrorService errorService, UserService userService) {
        this.errorService = errorService;
        this.userService = userService;
    }

    @GetMapping(value = "/{email}")
    public List<ErrorMassage> getAllErrors(@PathVariable("email") String userEmail){
        User user = userService.findByEmail(userEmail);

        return errorService.findAllByUserId(user.getUserId());
    }

}
