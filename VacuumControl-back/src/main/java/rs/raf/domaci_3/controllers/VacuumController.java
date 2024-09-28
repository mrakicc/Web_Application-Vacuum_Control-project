package rs.raf.domaci_3.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import rs.raf.domaci_3.model.Permission;
import rs.raf.domaci_3.model.User;
import rs.raf.domaci_3.model.Vacuum;
import rs.raf.domaci_3.services.UserService;
import rs.raf.domaci_3.services.VacuumService;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin
@RestController
@RequestMapping("/api/vacuums")
public class VacuumController {

    private final VacuumService vacuumService;
    private final UserService userService;
    private int counter = 0;
    private boolean disabled = false;
    //private List<Vacuum> disabled = new ArrayList<>();

    public VacuumController(VacuumService vacuumService, UserService userService) {
        this.vacuumService = vacuumService;
        this.userService =  userService;
    }

    @GetMapping(value = "/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vacuum> getAllVacuums(@PathVariable("email") String email){
//        if(disabled == true){
//            throw new DisabledException("In process another function");
//        }
        return vacuumService.findAllByAddedBy(email);
    };

    @GetMapping(value = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Vacuum addVacuum(@RequestParam("email") String emailUser, @RequestParam("name") String nameVacuum,  Authentication authentication){
//        if(disabled == true){
//            throw new DisabledException("In process another function");
//        }
        if(isAuthorise(authentication, "can_add_vacuum")){
            return vacuumService.save(emailUser, nameVacuum);
        }
        System.out.println("\n\nYou are not authorise to do create function\n\n");
        throw new AccessDeniedException("You are not authorise to do create function");
    }

    @GetMapping(value = "/start/{email}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startVacuum(@PathVariable("email") String userEmail, @PathVariable("id") Long id, Authentication authentication) throws InterruptedException {
        if(isAuthorise(authentication, "can_start_vacuum")){
            return new ResponseEntity<>(vacuumService.start(userEmail,id), HttpStatus.OK);
        }
        System.out.println("\n\nYou are not authorise to do read function\n\n");
        throw new AccessDeniedException("You are not authorise to do read function");
    };

    @GetMapping(value = "/stop/{email}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> stopVacuum(@PathVariable("email") String userEmail, @PathVariable("id") Long id, Authentication authentication) throws InterruptedException {

        counter+=1;
        if(isAuthorise(authentication, "can_stop_vacuum")){
            if(counter == 3){
                counter = 0;
                vacuumService.stop(userEmail, id);
                return new ResponseEntity<>(vacuumService.discharge(userEmail, id), HttpStatus.OK);
            }

            return new ResponseEntity<>(vacuumService.stop(userEmail, id), HttpStatus.OK);
        }
        System.out.println("\n\nYou are not authorise to do read function\n\n");
        throw new AccessDeniedException("You are not authorise to do read function");
    };

    @GetMapping(value = "/discharge/{email}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> dischargeVacuum(@PathVariable("email") String userEmail,@PathVariable("id") Long id, Authentication authentication) throws InterruptedException {

        if(isAuthorise(authentication, "can_discharge_vacuum")){
            return new ResponseEntity<>(vacuumService.discharge(userEmail,id), HttpStatus.OK);
        }
        System.out.println("\n\nYou are not authorise to do read function\n\n");
        throw new AccessDeniedException("You are not authorise to do read function");
    };

    @DeleteMapping(value = "/{email}/{id}")
    public ResponseEntity<?> deleteVacuum(@PathVariable("email") String userEmail, @PathVariable("id") Long id, Authentication authentication){

        if(isAuthorise(authentication, "can_remove_vacuum")){
            vacuumService.deleteById(userEmail, id);
        }
        System.out.println("\n\nYou are not authorise to do delete function\n\n");
        return ResponseEntity.status(403).build();
    }

    @GetMapping(value = "/search/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vacuum> getSearch(@PathVariable("email") String userEmail, @RequestParam String name, @RequestParam String status, @RequestParam String date1, @RequestParam String date2, Authentication authentication) {

        if(isAuthorise(authentication, "can_search_vacuum")){
            LocalDate dateTo;
            LocalDate dateFrom;
            Date praviDatum = Date.valueOf("3000-05-05");
            Date praviDatumOd = Date.valueOf("1000-05-05");
            if(name.compareToIgnoreCase("null") == 0)
            {
                name = null;
            }
            if(status.compareToIgnoreCase("null") == 0)
            {
                status = null;
            }
            if(date1.compareToIgnoreCase("null") == 0 || date2.compareToIgnoreCase("null") == 0)
            {
                dateTo = null;
                dateFrom = null;
            }
            else
            {
                dateTo = LocalDate.parse(date2);
                dateFrom = LocalDate.parse(date1);
                praviDatum = Date.valueOf(date2);
                praviDatumOd = Date.valueOf(date1);
            }
            System.out.println(praviDatumOd);
            System.out.println(praviDatum);
            return vacuumService.search(userEmail, name,status,praviDatumOd,praviDatum );
        }
        System.out.println("\n\nYou are not authorise to do read function\n\n");
        throw new AccessDeniedException("You are not authorise to do read function");
    };

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

    @GetMapping(value = "/scheduleStart",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleStart(@RequestParam("email") String userEmail, @RequestParam("id") Long id, @RequestParam("date") String date, Authentication authentication) throws InterruptedException {
        if(isAuthorise(authentication, "can_start_vacuum")){
            if(date.length() == 0)
            {
                throw new DateTimeException("Date not chosen");
            }
            Date scheduleDate = Date.valueOf(date);
            return new ResponseEntity<>(vacuumService.scheduleStart(userEmail, id, scheduleDate), HttpStatus.OK);
        }
        System.out.println("\n\nYou are not authorise to do read function\n\n");
        throw new AccessDeniedException("You are not authorise to do read function");
    };

    @GetMapping(value = "/scheduleStop",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleStop(@RequestParam("email") String userEmail, @RequestParam("id") Long id, @RequestParam("date") String date, Authentication authentication) throws InterruptedException {
        if(isAuthorise(authentication, "can_stop_vacuum")){
            Date scheduleDate = Date.valueOf(date);
            return new ResponseEntity<>(vacuumService.scheduleStop(userEmail, id, scheduleDate), HttpStatus.OK);
        }
        System.out.println("\n\nYou are not authorise to do read function\n\n");
        throw new AccessDeniedException("You are not authorise to do read function");
    };

    @GetMapping(value = "/scheduleDischarge",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleDischarge(@RequestParam("email") String userEmail, @RequestParam("id") Long id, @RequestParam("date") String date, Authentication authentication) throws InterruptedException {
        if(isAuthorise(authentication, "can_discharge_vacuum")){
            Date scheduleDate = Date.valueOf(date);
            return new ResponseEntity<>(vacuumService.scheduleDischarge(userEmail, id, scheduleDate), HttpStatus.OK);
        }
        System.out.println("\n\nYou are not authorise to do read function\n\n");
        throw new AccessDeniedException("You are not authorise to do read function");
    };

}
