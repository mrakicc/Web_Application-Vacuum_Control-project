package rs.raf.domaci_3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import rs.raf.domaci_3.model.User;
import rs.raf.domaci_3.model.Vacuum;
import rs.raf.domaci_3.repositories.ErrorRepository;
import rs.raf.domaci_3.repositories.UserRepository;
import rs.raf.domaci_3.repositories.VacuumRepository;
import rs.raf.domaci_3.schedule.ScheduleDischarge;
import rs.raf.domaci_3.schedule.ScheduleStart;
import rs.raf.domaci_3.schedule.ScheduleStop;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class VacuumService {

    private VacuumRepository vacuumRepository;
    private UserRepository userRepository;
    private final AsyncComponent asyncComponent;
    private final TaskScheduler taskScheduler;
    private ErrorRepository errorRepository;

    @Autowired
    public VacuumService(VacuumRepository vacuumRepository, UserRepository userRepository, AsyncComponent asyncComponent, TaskScheduler taskScheduler, ErrorRepository errorRepository) {
        this.vacuumRepository = vacuumRepository;
        this.userRepository = userRepository;
        this.asyncComponent = asyncComponent;
        this.taskScheduler = taskScheduler;
        this.errorRepository = errorRepository;
    }

    public Vacuum save(String email, String name) {
        if(name.isEmpty()){
            throw  new BadCredentialsException("Name can not be null");
        }
        User user = userRepository.findByEmail(email);
        Vacuum vacuum = new Vacuum();
        List<Vacuum> allVacuums = (List<Vacuum>)vacuumRepository.findAll();
        for(Vacuum v : allVacuums){
            if(v.getName().equals(vacuum.getName())){
                System.out.println("Name " +vacuum.getName()+ " has already been used");
//                throw new BadCredentialsException("Email " +vacuum.getName()+ " has already been used");
            }
        }
        vacuum.setStatus("off");
        vacuum.setAddedBy(user);
        vacuum.setActive(true);
        Date date = new Date(System.currentTimeMillis());
        vacuum.setDateAdded(date);
        vacuum.setName(name);
        return this.vacuumRepository.save(vacuum);
    }

    public List<Vacuum> findAll() {
        return (List<Vacuum>)this.vacuumRepository.findAll();
    }

    public List<Vacuum> findAllByAddedBy(String email) {
        User user = userRepository.findByEmail(email);
        return this.vacuumRepository.findAllByAddedBy(user);
    }

    public ResponseEntity<?> start(String userEmail, Long id) throws InterruptedException {
        User user = this.userRepository.findByEmail(userEmail);
        Vacuum v = this.vacuumRepository.findById(id).get();
        if(!v.getAddedBy().equals(user)){
            System.out.println("You can not start a vacuum that isn't yours");
            throw  new AccessDeniedException("You can not start a vacuum that isn't yours");
        }else if(!v.getStatus().equals("off")) {
            throw  new IllegalStateException("Vacuum is not in state stopped");
        } else if (v.isDisabled() == true) {
            throw  new DisabledException("Vacuum is in another process");
        }

        v.setDisabled(true);
        this.vacuumRepository.save(v);
        asyncComponent.startVacuum(v);
        return  ResponseEntity.ok(v);
    }

    public ResponseEntity<?> stop(String userEmail, Long id) throws InterruptedException {
        User user = this.userRepository.findByEmail(userEmail);
        Vacuum v = this.vacuumRepository.findById(id).get();
        if(!v.getAddedBy().equals(user)){
            System.out.println("You can not stop a vacuum that isn't yours");
            throw  new AccessDeniedException("You can not stop a vacuum that isn't yours");
        }else if(!v.getStatus().equals("on")) {
            throw  new IllegalStateException("Vacuum is not in state stopped");
        } else if (v.isDisabled() == true) {
            throw  new DisabledException("Vacuum is in another process");
        }

        v.setDisabled(true);
        this.vacuumRepository.save(v);
        asyncComponent.stopVacuum(v);
        return  ResponseEntity.ok(v);
    }

    public ResponseEntity<?> discharge(String userEmail, Long id) throws InterruptedException {
        User user = this.userRepository.findByEmail(userEmail);
        Vacuum v = this.vacuumRepository.findById(id).get();
        if(!v.getAddedBy().equals(user)){
            System.out.println("You can not discharge a vacuum that isn't yours");
            throw  new AccessDeniedException("You can not discharge a vacuum that isn't yours");
        }else if(!v.getStatus().equals("off")) {
            throw  new IllegalStateException("Vacuum is not in state stopped");
        }else if(v.isDisabled() == true) {
            throw  new DisabledException("Vacuum is in another process");
        }

        v.setDisabled(true);
        this.vacuumRepository.save(v);
        asyncComponent.dischargeVacuum(v);
        return ResponseEntity.ok(v);
    }

    public void deleteById(String userEmail, Long id) {
        User user = this.userRepository.findByEmail(userEmail);
        Vacuum v = this.vacuumRepository.findById(id).get();
        if(!v.getAddedBy().equals(user)){
            System.out.println("You can not delete a vacuum that isn't yours");
            throw  new AccessDeniedException("You can not delete a vacuum that isn't yours");
        }else if(!v.getStatus().equals("off")) {
            throw  new IllegalStateException("Vacuum is not in state stopped");
        }else if(v.isDisabled() == true) {
            throw  new DisabledException("Vacuum is in another process");
        }
        v.setActive(false);
        this.vacuumRepository.save(v);
    }
    public List<Vacuum> search(String userEmail, String name, String status, Date dateFrom, Date dateTo) {
        User user = this.userRepository.findByEmail(userEmail);
        List<Vacuum> result = new ArrayList<>();

        List<Vacuum> vacuums = this.vacuumRepository.findByNameAndStatus(name,status,dateFrom,dateTo);
        for(Vacuum v : vacuums){
            if(v.getAddedBy().equals(user)){
                result.add(v);
            }
        }

        return result;
    }

    public Vacuum findByName(String name) {
        return this.vacuumRepository.findByName(name);
    }

    public List<Vacuum> findAllByStatus(List<String> statusList) {
        List<Vacuum> vacuumList = new ArrayList<>();
        List<Vacuum> tempList;
        for(String status : statusList) {
            tempList = this.vacuumRepository.findAllByStatus(status);
            for(Vacuum v : tempList){
                vacuumList.add(v);
            }
        }
        return vacuumList;
    }

    public List<Vacuum> findAllByDate(Date from, Date to) {
        List<Vacuum> all = (List<Vacuum>)this.vacuumRepository.findAll();
        List<Date> dates = new ArrayList<>();
        List<Vacuum> tempList;
        List<Vacuum> vacuumList = new ArrayList<>();
        for(Vacuum v : all){
            if(!dates.contains(v.getDateAdded()) && (v.getDateAdded().compareTo(from) >= 0) && (v.getDateAdded().compareTo(to) <= 0)){
                dates.add(v.getDateAdded());
            }
        }
        for(Date d : dates){
            tempList = this.vacuumRepository.findAllByDateAdded(d);
            for(Vacuum v : tempList){
                vacuumList.add(v);
            }
        }

        return vacuumList;
    }

    public ResponseEntity<?> scheduleStart(String userEmail, Long vacuumId, java.util.Date date){
        User user = this.userRepository.findByEmail(userEmail);
        taskScheduler.schedule(new ScheduleStart(vacuumRepository, asyncComponent, vacuumId, user.getUserId(), errorRepository), date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> scheduleStop(String userEmail, Long vacuumId, java.util.Date date){
        User user = this.userRepository.findByEmail(userEmail);
        taskScheduler.schedule(new ScheduleStop(vacuumRepository, asyncComponent, vacuumId, user.getUserId(), errorRepository), date);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> scheduleDischarge(String userEmail, Long vacuumId, java.util.Date date){
        User user = this.userRepository.findByEmail(userEmail);
        taskScheduler.schedule(new ScheduleDischarge(vacuumRepository, asyncComponent, vacuumId, user.getUserId(), errorRepository), date);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
