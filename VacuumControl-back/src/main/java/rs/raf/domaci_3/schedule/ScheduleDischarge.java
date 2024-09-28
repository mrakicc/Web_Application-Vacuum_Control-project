package rs.raf.domaci_3.schedule;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import rs.raf.domaci_3.model.ErrorMassage;
import rs.raf.domaci_3.model.Vacuum;
import rs.raf.domaci_3.repositories.ErrorRepository;
import rs.raf.domaci_3.repositories.VacuumRepository;
import rs.raf.domaci_3.services.AsyncComponent;

import java.util.Date;

public class ScheduleDischarge implements Runnable{

    private VacuumRepository vacuumRepository;
    private AsyncComponent asyncComponent;
    private ErrorRepository errorRepository;

    private Long vacId;
    private Long userId;

    public ScheduleDischarge(VacuumRepository vacuumRepository, AsyncComponent asyncComponent, Long vacId, Long userId, ErrorRepository errorRepository) {
        this.vacuumRepository = vacuumRepository;
        this.asyncComponent = asyncComponent;
        this.vacId = vacId;
        this.userId = userId;
        this.errorRepository = errorRepository;
    }

    @Override
    public void run() {
        Vacuum vacuum = vacuumRepository.findById(this.vacId).get();

        if(vacuum.getAddedBy().getUserId() != userId){
            ErrorMassage errorMassage = new ErrorMassage(userId, vacId, new Date(), "discharge", "You can not discharge a vacuum that isn't yours");
            errorRepository.save(errorMassage);
            return;
        }
        if(!vacuum.getStatus().equals("off")) {
            ErrorMassage errorMassage = new ErrorMassage(userId, vacId, new Date(), "discharge", "Vacuum is not in state stopped");
            errorRepository.save(errorMassage);
        }
        if(vacuum.isDisabled() == true) {
            ErrorMassage errorMassage = new ErrorMassage(userId, vacId, new Date(), "discharge", "Vacuum is in another process");
            errorRepository.save(errorMassage);
            return;
        }
        if(!vacuum.isActive()) {
            ErrorMassage errorMassage = new ErrorMassage(userId, vacId, new Date(), "discharge", "Vacuum is not active");
            errorRepository.save(errorMassage);
            return;
        }

        vacuum.setDisabled(true);
        vacuumRepository.save(vacuum);
        asyncComponent.dischargeVacuum(vacuum);

    }
}
